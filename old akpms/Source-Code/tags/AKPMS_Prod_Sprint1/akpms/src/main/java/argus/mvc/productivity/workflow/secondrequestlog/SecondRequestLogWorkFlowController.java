package argus.mvc.productivity.workflow.secondrequestlog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import argus.domain.SecondRequestLogWorkFlow;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.productivity.ArProductivityDao;
import argus.repo.productivity.helper.ArProductivityDaoHelper;
import argus.repo.productivity.workflow.secondrequestlog.SecondRequestLogDao;
import argus.util.ArProductivityJsonData;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.validator.SecondRequestLogWorkFlowValidator;

@Controller
@RequestMapping(value = "/flows/secondrequestlogworkflow")
@SessionAttributes({ "secondRequestLogWorkFlow" })
public class SecondRequestLogWorkFlowController {

	private static final Log LOGGER = LogFactory
			.getLog(SecondRequestLogWorkFlowController.class);

	@Autowired
	private SecondRequestLogDao secondRequestLogDao;

	@Autowired
	private SecondRequestLogWorkFlowValidator secondRequestLogWorkFlowValidator;

	@Autowired
	private ArProductivityDao arProductivityDao;

	@RequestMapping(method = RequestMethod.GET)
	public String secondRequestLogWorkFlowList() {

		return "flows/arProductivity/secondRequestLogWorkFlowList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String secondRequestLogWorkFlowLoad(Map<String, Object> map,
			WebRequest request, HttpSession session) {

		SecondRequestLogWorkFlow secondRequestLogWorkFlow = null;

		if (request.getParameter("arProductivity.id") != null) {
			try {
				map.put(Constants.ARPRODUCTIVITY, arProductivityDao
						.findById(Long.valueOf(request
								.getParameter(Constants.ARPRODUCTIVITY_ID))));
				secondRequestLogWorkFlow = secondRequestLogDao
						.findByProductivityId(Long.valueOf(request
								.getParameter("arProductivity.id")));
				map.put(Constants.READ_ONLY, true);
				session.setAttribute("initialStatus",
						secondRequestLogWorkFlow.getStatus());
			} catch (NoResultException ex) {
				secondRequestLogWorkFlow = new SecondRequestLogWorkFlow();
				map.put(Constants.READ_ONLY, false);
				map.put("secondRequestLogWorkFlow", secondRequestLogWorkFlow);
				LOGGER.error(Constants.EXCEPTION, ex);

			} catch (Exception e) {
				LOGGER.info("Error occured while fetching SecondRequestLogWorkFlow Object");
				LOGGER.error(Constants.EXCEPTION, e);
			}

		} else if (request.getParameter(Constants.ID) != null) {
			try {
				secondRequestLogWorkFlow = secondRequestLogDao.findById(Long
						.valueOf(request.getParameter(Constants.ID)));
				map.put(Constants.READ_ONLY, true);
			} catch (Exception e) {
				LOGGER.info("Exception occured while fetching SecondRequest Log WorkFlow");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		} else {

			secondRequestLogWorkFlow = new SecondRequestLogWorkFlow();
			map.put(Constants.READ_ONLY, false);
		}

		map.put("secondRequestLogWorkFlow", secondRequestLogWorkFlow);
		return "flows/arproductivity/secondrequestlogworkflow";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String secondRequestLogWorkFlowProcess(
			@ModelAttribute("secondRequestLogWorkFlow") SecondRequestLogWorkFlow secondRequestLogWorkFlow,
			Map<String, Object> map, BindingResult result) {
		secondRequestLogWorkFlowValidator.validate(secondRequestLogWorkFlow,
				result);
		if (result.hasErrors()) {
			try {
				map.put(Constants.ARPRODUCTIVITY, arProductivityDao
						.findById(secondRequestLogWorkFlow.getArProductivity()
								.getId()));
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);

			}
			return "flows/arproductivity/secondrequestlogworkflow";

		}
		LOGGER.info("In secondRequestLogWorkFlow method");
		if (secondRequestLogWorkFlow.getId() != null) {
			try {
				secondRequestLogDao
						.updateSecondRequestLogWorkFlow(secondRequestLogWorkFlow);
			} catch (ArgusException e) {
				LOGGER.info("Exception occured while updating Coding Correction Log");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		} else {
			try {
				secondRequestLogDao
						.addSecondRequestLogWorkFlow(secondRequestLogWorkFlow);
			} catch (ArgusException e) {
				LOGGER.info("Exception occured while adding SecondRequest Log");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		}
		LOGGER.info("Out secondRequestLogWorkFlowProcess method");
		return "redirect:/flows/secondrequestlogworkflow/add?arProductivity.id="
				+ secondRequestLogWorkFlow.getArProductivity().getId();
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
			int totalRows = secondRequestLogDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<SecondRequestLogWorkFlow> rows = secondRequestLogDao
						.findAll(orderClauses, whereClauses, true);
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
			List<SecondRequestLogWorkFlow> rows) {
		List<ArProductivityJsonData> arProdJsonData = new ArrayList<ArProductivityJsonData>();

		if (rows != null && rows.size() > 0) {
			for (SecondRequestLogWorkFlow eTemp : rows) {
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
