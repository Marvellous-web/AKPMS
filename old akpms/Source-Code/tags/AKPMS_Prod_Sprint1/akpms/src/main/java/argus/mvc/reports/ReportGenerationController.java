/**
 *
 */
package argus.mvc.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import argus.domain.AdminSettings;
import argus.domain.Department;
import argus.domain.ProcessManual;
import argus.domain.User;
import argus.repo.adminSettings.AdminSettingsDao;
import argus.repo.processManual.ProcessManualDao;
import argus.repo.traineeEvaluation.TraineeEvaluateDao;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.TraineeCalcaulation;
import argus.util.TraineeEvaluationReportData;

/**
 * @author sumit.v
 * 
 */
@Controller
@RequestMapping(value = "/generatereport")
public class ReportGenerationController {

	private static final Log LOGGER = LogFactory
			.getLog(ReportGenerationController.class);

	@Autowired
	private ProcessManualDao processManualDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private TraineeEvaluateDao traineeEvaluateDao;

	@Autowired
	private AdminSettingsDao adminSettingsDao;

	/**
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/processmanualreport", method = RequestMethod.GET)
	public String genarateProcessManualReport(Map<String, Object> map) {
		User user = AkpmsUtil.getLoggedInUser();
		if (user.getRole().getId() == Constants.DOCUMENT_MANAGER_ROLE_ID) {
			try {
				List<ProcessManual> processManualList = processManualDao
						.getAllProcessManuals(true, true);
				Map<String, String> whereClauses = new HashMap<String, String>();
				whereClauses.put(Constants.SELECTED_ROLES_IDS, Constants.TRAINEE_ROLE_ID.toString());
				whereClauses.put(Constants.STATUS, Constants.STRING_ONE);
				List<User> traineeList = userDao.findAll(null, whereClauses);
				map.put("processManualList", processManualList);
				map.put("traineeList", traineeList);
			} catch (Exception e) {
				LOGGER.info("ERROR:" + e.getMessage());
			}
		}else if (user.getRole().getId() == Constants.TRAINEE_ROLE_ID) {
			try {
				List<ProcessManual> processManualList = processManualDao
						.getAllProcessManuals(true, true);
				User trainee = userDao.findById(user.getId(), true);
				List<Long> userDeptList = new ArrayList<Long>();
				for (Department dept : trainee.getDepartments()) {
					userDeptList.add(dept.getId());
				}
				for (ProcessManual pm : processManualList) {
					for (Department dept : pm.getDepartments()) {
						if (userDeptList.contains(dept.getId())) {
							pm.setShowReadAndUnderstood(true);
						}
					}
				}
				map.put("processManualList", processManualList);
				map.put("trainee", trainee);
			} catch (Exception e) {
				LOGGER.error("ERROR: " + e.getMessage());
			}
		}

		return "processManualReadStatusReport";
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/traineeevaluationreport", method = RequestMethod.GET)
	public String generateEvaluationReport(Map<String, Object> map) {
		LOGGER.info("in trainee evaluation report");
		try {
			List<Object[]> evaluatedTraineeData = traineeEvaluateDao
					.findEvaluationReportTrainees(new Long(Constants.ZERO));
			LOGGER.info("Trainee List Size:= " + evaluatedTraineeData.size());
			AdminSettings adminSettings = adminSettingsDao.getAdminSettings();
			Long totalProcessManuals = processManualDao
					.getTotalProcessManuals();
			if (evaluatedTraineeData.size() > Constants.ZERO) {
				List<TraineeEvaluationReportData> traineeEvaluationReportDataList = processGeneratedData(
						evaluatedTraineeData, totalProcessManuals,
						adminSettings);
				LOGGER.info("Trainee Report Data Size:= "
						+ traineeEvaluationReportDataList.size());
				map.put("traineeEvaluationReportDataList",
						traineeEvaluationReportDataList);
			}
		} catch (Exception e) {
			LOGGER.info(Constants.EXCEPTION, e);
		}

		return "traineeEvaluationReport";
	}

	/**
	 * 
	 * @param evaluatedTraineeData
	 * @param totalProcessManuals
	 * @param adminSettings
	 * @return
	 */
	private List<TraineeEvaluationReportData> processGeneratedData(
			List<Object[]> evaluatedTraineeData, Long totalProcessManuals,
			AdminSettings adminSettings) {
		List<TraineeEvaluationReportData> traineeEvaluationReportDataList = new ArrayList<TraineeEvaluationReportData>();

		for (Object[] trainee : evaluatedTraineeData) {
			TraineeEvaluationReportData traineeEvaluationReportData = new TraineeEvaluationReportData();
			traineeEvaluationReportData.setFirstName(trainee[Constants.ZERO]
					.toString());
			traineeEvaluationReportData.setLastName(trainee[Constants.ONE]
					.toString());
			traineeEvaluationReportData.setId(Long
					.parseLong(trainee[Constants.TWO]
					.toString()));
			float idsArgusPercent = TraineeCalcaulation
					.calculateIdsArgusPercen(adminSettings,
 Float
							.parseFloat(trainee[Constants.EIGHT].toString()));
			traineeEvaluationReportData.setIdsArgusPercent(idsArgusPercent);
			float argusPercent = TraineeCalcaulation.calculateArgusPercent(
					adminSettings,
					Float.parseFloat(trainee[Constants.FIVE].toString()));
			traineeEvaluationReportData.setArgusPercent(argusPercent);
			float processManualReadPercent = TraineeCalcaulation
					.calculateProcessManualReadStatusPercent(adminSettings,
							Float.parseFloat(trainee[Constants.NINE].toString()),
							totalProcessManuals);
			traineeEvaluationReportData
					.setProcessManualReadpercent(processManualReadPercent);
			float traineePercent = TraineeCalcaulation.calculateTraineePercent(
					Float.parseFloat(trainee[Constants.EIGHT].toString()),
					Float.parseFloat(trainee[Constants.FIVE].toString()),
					Float.parseFloat(trainee[Constants.NINE].toString()),
					totalProcessManuals, adminSettings);

			traineeEvaluationReportData.setTotalTraineePercent(traineePercent);
			traineeEvaluationReportDataList.add(traineeEvaluationReportData);
		}

		return traineeEvaluationReportDataList;
	}
}
