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

import argus.domain.Department;
import argus.repo.department.DepartmentDao;

/**
 * @author sumit.v
 *
 */
@Service
public class DepartmentVaildator extends LocalValidatorFactoryBean implements
		Validator {
	private static final Logger LOGGER = Logger
			.getLogger(DepartmentVaildator.class);
	@Autowired
	private DepartmentDao departmentDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return Department.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		Department department = (Department) target;
		try {
			Department isDepartment = departmentDao.findByName(department
					.getName());

			if (isDepartment != null) {
				if (department.getId() == null) {
					LOGGER.info("duplicate dept name validation in case of add department");
					errors.rejectValue("name", "departmentName.exist");
				} else if (department.getId().longValue() != isDepartment
						.getId().longValue()) {
					LOGGER.info("duplicate dept name validation in case of edit department");
					errors.rejectValue("name", "departmentName.exist");
				}
			}

		} catch (Exception e) {
			LOGGER.info(
					"No Department Exists with name = " + department.getName(),
					e);
		}
		if (department.getName() == null
				|| department.getName().trim().equals("")) {
			errors.rejectValue("name", "departmentName.required");
		}
	}
}
