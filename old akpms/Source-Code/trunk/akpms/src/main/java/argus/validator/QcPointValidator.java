/**
 *
 */
package argus.validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import argus.domain.QcPoint;
import argus.repo.department.DepartmentDao;
import argus.repo.qcpoint.QcPointDao;

/**
 * @author ashish.g , saurav.sofat
 *
 */
@Service
public class QcPointValidator extends LocalValidatorFactoryBean implements
		Validator {
	private static final Logger LOGGER = Logger
			.getLogger(QcPointValidator.class);
	@Autowired
	private QcPointDao qcPointDao;

	@Autowired
	private DepartmentDao departmentDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return QcPoint.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		QcPoint qcPoint = (QcPoint) target;
		LOGGER.info("in validator");

		if (qcPoint.getName() == null || qcPoint.getName().trim().equals("")) {
			errors.rejectValue("name", "qcPointName.required");
		} else {
			try {
				// to check duplicate name in same department
				QcPoint isQcPoint;

				try {
					isQcPoint = qcPointDao.findByNameAndDepartmentId(
							qcPoint.getName(), qcPoint.getDepartment().getId());
				} catch (Exception e) {
					LOGGER.info(
							"No result found for same name and department of QC Point",
							e);
					isQcPoint = null;
				}

				QcPoint parent = qcPointDao.findById(qcPoint.getParent()
						.getId(), false);

				LOGGER.info("in try validator " + parent.getName());

				if (isQcPoint != null) {
					if (qcPoint.getId() == null) {
						System.out.println("QcPoint check id.");
						LOGGER.info("duplicate dept name validation in case of add qcPoint");
						errors.rejectValue("name", "qcPointName.exist");
					} else if (qcPoint.getId().longValue() != isQcPoint.getId()
							.longValue()) {
						System.out.println("check new and old.");
						LOGGER.info("duplicate dept name validation in case of edit qcPoint");
						errors.rejectValue("name", "qcPointName.exist");
					}
				}

				if (qcPoint.getDepartment().getId().longValue() != parent
						.getDepartment().getId().longValue()) {
					// if (qcPoint.getId() == null) {
					System.out.println("in diff department");
					LOGGER.info("different department than parent validation in case of add qcPoint");
					errors.rejectValue(
							"name",
							"qcPointParentDepartment.match",
							new Object[] { departmentDao.findById(
									parent.getDepartment().getId(), true).getName() },
							"choose same department as parent");

					// } else if (qcPoint.getId().longValue() !=
					// isQcPoint.getId()
					// .longValue()) {
					// LOGGER.info("different department than parent validation in case of edit qcPoint");
					// errors.rejectValue(
					// "name",
					// "please choose "
					// + departmentDao.findById(
					// parent.getDepartmentId(), true)
					// .getName());
					// }
				}

			} catch (Exception e) {
				LOGGER.info(
						"No QcPoint Exists with name = " + qcPoint.getName(), e);
			}
		}

		if (qcPoint.getDepartment().getId() == null
				|| qcPoint.getDepartment().getId().equals("")) {
			errors.rejectValue("departmentId", "departmentName.required");
		}
	}
}
