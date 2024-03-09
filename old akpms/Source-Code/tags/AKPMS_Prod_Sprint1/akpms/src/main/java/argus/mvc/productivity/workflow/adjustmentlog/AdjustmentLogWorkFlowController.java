/**
 *
 */
package argus.mvc.productivity.workflow.adjustmentlog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import argus.domain.AdjustmentLogWorkFlow;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.productivity.ArProductivityDao;
import argus.repo.productivity.helper.ArProductivityDaoHelper;
import argus.repo.productivity.workflow.adjustmentlogs.AdjustmentLogsDao;
import argus.util.ArProductivityJsonData;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.validator.AdjustmentLogWorkFlowValidator;

/**
 * @author vishal.joshi
 *
 */
@Controller
@RequestMapping(value = "/flows/adjustmentlogs")
@SessionAttributes({ Constants.ADJUSTMENTLOG })
public class AdjustmentLogWorkFlowController {

	@Autowired
	private AdjustmentLogWorkFlowValidator flowValidator;

	@Autowired
	private AdjustmentLogsDao adjLogsDao;

	@Autowired
	private ArProductivityDao arProductivityDao;

	private static final Log LOGGER = LogFactory
			.getLog(AdjustmentLogWorkFlowController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String adjustmentLogList() {

		return "flows/arProductivity/adjustmentLogsList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String adjustmentLogLoad(Model model, WebRequest request,
			Map<String, Object> map,
			@RequestParam(required = false) String successUpdate,
			HttpSession session) {
		LOGGER.info("in [adjustmentLogLoad] method");
		int arProductivityId = 0;
		if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null
				&& !request.getParameter(Constants.ARPRODUCTIVITY_ID)
						.equals("")) {
			arProductivityId = Integer.valueOf(request
					.getParameter(Constants.ARPRODUCTIVITY_ID));
			AdjustmentLogWorkFlow adjLogWorkFlow = null;
			try {
				map.put(Constants.ARPRODUCTIVITY, arProductivityDao
						.findById(Long.valueOf(request
								.getParameter(Constants.ARPRODUCTIVITY_ID))));
				adjLogWorkFlow = adjLogsDao
						.getAdjLogByArProductivityId(arProductivityId);
				model.addAttribute(Constants.ADJUSTMENTLOG, adjLogWorkFlow);
				model.addAttribute(Constants.MODE, Constants.EDIT);
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				session.setAttribute("initialStatus",
						adjLogWorkFlow.getWorkFlowStatus());
				map.put(Constants.READ_ONLY, true);
				if (adjLogWorkFlow.getWorkFlowStatus() == Constants.WORKFLOW_ESCALATE) {
					map.put(Constants.CHECK_WORK_FLOW,
							Constants.WORKFLOW_ESCALATE);
				}
			} catch (ArgusException e) {
				LOGGER.error(e.getMessage(), e);
			} catch (NoResultException ex) {
				LOGGER.error(ex.getMessage(), ex);
				map.put(Constants.ADJUSTMENTLOG, new AdjustmentLogWorkFlow());
				model.addAttribute(Constants.MODE, Constants.ADD);
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
				map.put(Constants.READ_ONLY, false);
			}
		} else {
			map.put(Constants.ADJUSTMENTLOG, new AdjustmentLogWorkFlow());
			model.addAttribute(Constants.MODE, Constants.ADD);
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.SAVE);
			map.put(Constants.READ_ONLY, false);
		}

		return "flows/arProductivity/adjustmentLogs";
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveAdjustmentLog(
			@Valid @ModelAttribute(Constants.ADJUSTMENTLOG) AdjustmentLogWorkFlow adjLogWorkFlow,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in [saveAdjustmentLog] method");
		flowValidator.validate(adjLogWorkFlow, result);
		if (!result.hasErrors()) {

			if (adjLogWorkFlow.getId() != null) {
				LOGGER.info("in [saveAdjustmentLog]adjLogWorkFlow is not null");
				try {
					adjLogsDao.updateAdjLogWorkFlow(adjLogWorkFlow);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"adjustmentlogs.updatedSuccessfully");
					map.put("showFinalRemark", 1);
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				} finally {
					return "redirect:/flows/arProductivity";
				}
			} else {
				try {
					adjLogsDao.addAdjLogWorkFlow(adjLogWorkFlow);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"adjustmentlogs.addedSuccessfully");

				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
					return "flows/arProductivity/adjustmentLogs";
				}
			}
			return "redirect:/flows/arProductivity";
		} else {
			if (adjLogWorkFlow != null && adjLogWorkFlow.getId() != null) {
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				map.put(Constants.CHECK_WORK_FLOW, Constants.WORKFLOW_ESCALATE);
				map.put(Constants.READ_ONLY, true);
				try {
					map.put(Constants.ARPRODUCTIVITY, arProductivityDao
							.findById(adjLogWorkFlow.getArProductivity()
									.getId()));
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);

				}

			} else {
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
			}
			return "flows/arProductivity/adjustmentLogs";
		}
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<ArProductivityJsonData> listAllArProductivity(
			WebRequest request) {
		LOGGER.info("in [listAllArProductivity]json method");
		int page = 1;
		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();
		if (request != null) {
			page = Integer.parseInt(request.getParameter(Constants.PAGE));
			orderClauses = ArProductivityHelper.getOrderClause(request);
			whereClauses = ArProductivityHelper.getWhereClause(request);
		} else {
			LOGGER.info("request object is coming null");
		}
		try {
			int totalRows = adjLogsDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<AdjustmentLogWorkFlow> rows = adjLogsDao.findAll(
						orderClauses, whereClauses, true);
				List<ArProductivityJsonData> djd = getJsonData(rows);
				JsonDataWrapper<ArProductivityJsonData> jdw = new JsonDataWrapper<ArProductivityJsonData>(
						page, totalRows, djd);
				return jdw;
			}
		} catch (ArgusException e) {
			LOGGER.info(Constants.EXCEPTION, e);
		}
		return null;
	}

	private List<ArProductivityJsonData> getJsonData(
			List<AdjustmentLogWorkFlow> rows) {
		List<ArProductivityJsonData> arProdJsonData = new ArrayList<ArProductivityJsonData>();

		if (rows != null && rows.size() > 0) {
			for (AdjustmentLogWorkFlow eTemp : rows) {
				ArProductivityJsonData djd = new ArProductivityJsonData();

				djd.setId(eTemp.getArProductivity().getId());
				djd.setInsurance(eTemp.getArProductivity().getInsurance()
						.getName());
				djd.setBalanceAmt(eTemp.getArProductivity().getBalanceAmt());
				djd.setDoctor(eTemp.getArProductivity().getDoctor().getName());
				djd.setCpt(eTemp.getArProductivity().getCpt());
				djd.setDos(eTemp.getArProductivity().getDos());
				djd.setPatientAccNo(eTemp.getArProductivity().getPatientAccNo());
				djd.setPatientName(eTemp.getArProductivity().getPatientName());
				djd.setSourceName(ArProductivityDaoHelper.getSourceName(eTemp
						.getArProductivity().getSource()));
				djd.setWorkFlowName(ArProductivityDaoHelper
						.getWorkFlowName(eTemp.getArProductivity()
								.getWorkFlow()));
				djd.setDataBas(eTemp.getArProductivity().getDataBas());
				arProdJsonData.add(djd);
			}
		}
		return arProdJsonData;
	}
}
