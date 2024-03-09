/**
 *
 */
package argus.mvc.productivity.workflow.refundrequest;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import argus.domain.RefundRequestWorkFlow;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.productivity.ArProductivityDao;
import argus.repo.productivity.workflow.refundRequest.RefundRequestDao;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.util.RefundRequestWorkFlowJsonData;
import argus.validator.RefundRequestWorkFlowValidator;

/**
 * @author rajiv.k
 *
 */
@Controller
@RequestMapping(value = "/flows/refundrequest")
@SessionAttributes({ Constants.REFUNDREQUEST })
public class RefundRequestWorkFlowController {

	private static final Log LOGGER = LogFactory
			.getLog(RefundRequestWorkFlowController.class);

	@Autowired
	private RefundRequestDao refundRequestDao;

	@Autowired
	private ArProductivityDao arProductivityDao;

	@Autowired
	private RefundRequestWorkFlowValidator refundRequestWorkFlowValidator;
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ArgusException {
		LOGGER.info("in [initBinder] method ");
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			public void setAsText(String value) {
				try {
					setValue(ArProductivityHelper.initBinder(value));
				} catch (ArgusException e) {
					LOGGER.error(Constants.EXCEPTION, e);
					setValue(null);
				}
			}

			public String getAsText() {
				if (getValue() != null) {
					return new SimpleDateFormat(Constants.DATE_FORMAT)
							.format((Date) getValue());
				}
				return "";
			}
		});
	}

	@RequestMapping(method = RequestMethod.GET)
	public String refundRequestWorkFlowList() {
		return "flows/arProductivity/refundRequestWorkFlowList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String refundRequestLoad(Model model, WebRequest request,
			Map<String, Object> map,
			@RequestParam(required = false) String successUpdate) {
		LOGGER.info("in [refundRequestLoad] method");
		RefundRequestWorkFlow refundRequestWorkFlow = null;

		if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null
				&& !request.getParameter(Constants.ARPRODUCTIVITY_ID)
						.equals("")) {
			try {
				refundRequestWorkFlow = refundRequestDao
						.getRefundRequestByArProductivityId((Integer.parseInt(request
								.getParameter(Constants.ARPRODUCTIVITY_ID))));
				map.put("refundRequest", refundRequestWorkFlow);
				map.put("readOnly", true);
			} catch (Exception e) {

				refundRequestWorkFlow = new RefundRequestWorkFlow();
				if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null) {
					try {
						refundRequestWorkFlow
								.setArProductivity(arProductivityDao.findById(Long.valueOf(request
										.getParameter(Constants.ARPRODUCTIVITY_ID))));
					} catch (Exception ex) {
						LOGGER.info(Constants.EXCEPTION, e);
					}
				}

				map.put("readOnly", false);

				LOGGER.info("Exception occured while fetching Refund Request");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		}
		map.put("refundRequest", refundRequestWorkFlow);
		return "flows/arProductivity/refundRequest";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String refundRequestAdd(
			@ModelAttribute(Constants.REFUNDREQUEST) RefundRequestWorkFlow refundRequestWorkFlow,
			Map<String, Object> map, BindingResult result) {

		refundRequestWorkFlowValidator.validate(refundRequestWorkFlow, result);
		if (result.hasErrors()) {
			return "flows/arProductivity/refundRequest";
		}

		LOGGER.info("In refundRequestAdd method");
		if (refundRequestWorkFlow.getId() != null) {

			try {
				refundRequestDao
						.updateRefundRequestWorkFlow(refundRequestWorkFlow);

			} catch (ArgusException e) {
				LOGGER.info("Exception occured while adding Refund Request");
				LOGGER.info(Constants.EXCEPTION, e);
			}

		} else {
			try {
				refundRequestDao
						.addRefundRequestWorkFlow(refundRequestWorkFlow);
			} catch (ArgusException e) {
				LOGGER.info("Exception occured while adding Refund Request");
				LOGGER.info(Constants.EXCEPTION, e);
			}
		}
		LOGGER.info("Out refundRequestAdd method");
		return "redirect:/flows/refundrequest";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<RefundRequestWorkFlowJsonData> listAllRefundRequest(
			WebRequest request) {
		LOGGER.info("in [listAllArProductivity]json method");
		int page = 1;
		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();
		if (request != null) {
			orderClauses = ArProductivityHelper.getOrderClause(request);
			whereClauses = ArProductivityHelper.getWhereClause(request);
		} else {
			LOGGER.info("request object is coming null");
		}
		if (request.getParameter(Constants.AR_PROD_PATIENT_NAME) != null
				& request.getParameter(Constants.AR_PROD_PATIENT_NAME).trim()
						.length() > 0) {
			String patientName = request
					.getParameter(Constants.AR_PROD_PATIENT_NAME);
			whereClauses.put(Constants.AR_PROD_PATIENT_NAME, patientName);
		}
		if (request.getParameter("arProductivity.patientAccNo") != null
				& request.getParameter("arProductivity.patientAccNo").trim()
						.length() > 0) {
			String patientAccNo = request
					.getParameter("arProductivity.patientAccNo");
			whereClauses.put("arProductivity.patientAccNo", patientAccNo);
		}

		if (request.getParameter("status") != null) {
			String status = request.getParameter("status");
			whereClauses.put("status", status);
		}

		try {
			int totalRows = refundRequestDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<RefundRequestWorkFlow> rows = refundRequestDao.findAll(
						orderClauses, whereClauses, true);
				List<RefundRequestWorkFlowJsonData> ccjd = getJsonData(rows);
				JsonDataWrapper<RefundRequestWorkFlowJsonData> jdw = new JsonDataWrapper<RefundRequestWorkFlowJsonData>(
						page, totalRows, ccjd);
				return jdw;
			}
		} catch (ArgusException e) {
			LOGGER.info(Constants.EXCEPTION, e);
		}
		return null;
	}

	private List<RefundRequestWorkFlowJsonData> getJsonData(
			List<RefundRequestWorkFlow> rows) {
		List<RefundRequestWorkFlowJsonData> refundRequestWorkFlowJsonDataList = new ArrayList<RefundRequestWorkFlowJsonData>();

		if (rows != null && rows.size() > 0) {
			for (RefundRequestWorkFlow eTemp : rows) {
				RefundRequestWorkFlowJsonData rrjd = new RefundRequestWorkFlowJsonData();
				rrjd.setPatientName(eTemp.getArProductivity().getPatientName());
				rrjd.setPatientAccNo(eTemp.getArProductivity()
						.getPatientAccNo());
				rrjd.setResponsibleParty(eTemp.getResponsibleParty());
				rrjd.setDos(eTemp.getDos());
				rrjd.setId(eTemp.getId());
				if (eTemp.getStatus() == 0) {
					rrjd.setStatus("Pending");
				} else if (eTemp.getStatus() == 1) {
					rrjd.setStatus("Done");
				} else {
					rrjd.setStatus("Reject");
				}
				rrjd.setArProdId(eTemp.getArProductivity().getId());
				rrjd.setTotalAmount(eTemp.getTotalAmount());
				refundRequestWorkFlowJsonDataList.add(rrjd);
			}
		}
		return refundRequestWorkFlowJsonDataList;
	}

}
