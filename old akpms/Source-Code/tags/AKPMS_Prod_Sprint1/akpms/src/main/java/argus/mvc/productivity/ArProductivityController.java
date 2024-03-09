/**
 *
 */
package argus.mvc.productivity;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import argus.domain.ArProductivity;
import argus.domain.Insurance;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.doctor.DoctorDao;
import argus.repo.insurance.InsuranceDao;
import argus.repo.productivity.ArProductivityDao;
import argus.util.ArProductivityJsonData;
import argus.util.Constants;
import argus.util.JsonDataWrapper;
import argus.validator.ArProductivityValidator;

/**
 * @author vishal.joshi
 *
 */
@Controller
@RequestMapping(value = "/flows/arProductivity")
@SessionAttributes({ Constants.ARPRODUCTIVITY, Constants.ADJUSTMENTLOG,
		Constants.CHECKTRACER })
public class ArProductivityController {

	private static final Log LOGGER = LogFactory
			.getLog(ArProductivityController.class);

	private static final String INSURANCE_LIST = "insuranceList";

	private static final String DOCTOR_LIST = "doctorList";

	@Autowired
	private ArProductivityDao arProductivityDao;

	@Autowired
	private InsuranceDao insuranceDao;

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private ArProductivityValidator arProductivityValidator;

	@RequestMapping(method = RequestMethod.GET)
	public String productivityList() {
		return "flows/arProductivity/arProductivityList";
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ArgusException {

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

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String productivityLoad(Model model, WebRequest request,
			HttpSession session,
			Map<String, Object> map,
			@RequestParam(required = false) String successUpdate) {
		LOGGER.info("in [productivityLoad] method");
		try {
			Map<String, String> whereClause = new HashMap<String, String>();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			List<Insurance> insuranceList = insuranceDao.findAll(null,
					whereClause);
			model.addAttribute(INSURANCE_LIST, insuranceList);

			model.addAttribute(DOCTOR_LIST,
					doctorDao.findAll(null, whereClause, false));
		} catch (Exception e) {
			LOGGER.info("EXception while getting insurance list:");
			model.addAttribute(INSURANCE_LIST, null);
			model.addAttribute(DOCTOR_LIST, null);
		}

		if (null != request & null != request.getParameter(Constants.ID)) {
			ArProductivity arProductivity = null;
			try {
				arProductivity = arProductivityDao.findById(Long
						.parseLong(request.getParameter(Constants.ID)));
				if (arProductivity == null) {
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"record.notFound");
					return "redirect:/flows/arProductivity";
				}

			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
			model.addAttribute(Constants.ARPRODUCTIVITY, arProductivity);
			model.addAttribute(Constants.MODE, Constants.EDIT);
			map.put(Constants.OPERATION_TYPE, Constants.EDIT);
			map.put(Constants.BUTTON_NAME, Constants.UPDATE);
			map.put(Constants.READ_ONLY, true);
		} else {
			model.addAttribute(Constants.ARPRODUCTIVITY, new ArProductivity());
			model.addAttribute(Constants.MODE, Constants.ADD);
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.SAVE);
			map.put(Constants.READ_ONLY, false);
		}

		return "flows/arProductivity/arProductivity";
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveProductivity(
			@Valid @ModelAttribute(Constants.ARPRODUCTIVITY) ArProductivity arProductivity,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in [saveProductivity] method");

		String workFlow = null;

		try {
			model.addAttribute(INSURANCE_LIST, insuranceDao.findAll(null, null));
			model.addAttribute(DOCTOR_LIST,
					doctorDao.findAll(null, null, false));
		} catch (Exception e) {
			LOGGER.info("EXception while getting insurance list:");
			model.addAttribute(INSURANCE_LIST, null);
			model.addAttribute(DOCTOR_LIST, null);
		}
		arProductivityValidator.validate(arProductivity, result);
		if (!result.hasErrors()) {

			if (arProductivity.getId() != null) {
				try {
					arProductivityDao.updateProductivity(arProductivity);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"arProductivity.updatedSuccessfully");
					workFlow = ArProductivityHelper.getFlow(arProductivity
							.getWorkFlow())
							+ "?arProductivity.id="
							+ arProductivity.getId();
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				} finally {
					LOGGER.info("FINALLY ");
					return workFlow;
				}

			} else {
				try {
					arProductivityDao.addProductivity(arProductivity);
					session.setAttribute(Constants.SUCCESS_UPDATE,
							"arProductivity.addedSuccessfully");
					workFlow = ArProductivityHelper.getFlow(arProductivity
							.getWorkFlow())
							+ "?arProductivity.id="
							+ arProductivity.getId();
				} catch (ArgusException e) {
					LOGGER.info(Constants.EXCEPTION, e);
				}
			}
			return workFlow;

		} else {
			if (arProductivity != null & arProductivity.getId() != null) {
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
			} else {
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.SAVE);
			}

			return "flows/arProductivity/arProductivity";
		}

	}

	/**
	 * function to use for flexigrid list
	 *
	 * @param request
	 * @return
	 */
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
			int totalRows = arProductivityDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<ArProductivity> rows = arProductivityDao.findAll(
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

	private List<ArProductivityJsonData> getJsonData(List<ArProductivity> rows) {
		List<ArProductivityJsonData> arProdJsonData = new ArrayList<ArProductivityJsonData>();

		if (rows != null && rows.size() > 0) {
			for (ArProductivity eTemp : rows) {
				ArProductivityJsonData djd = new ArProductivityJsonData();

				djd.setId(eTemp.getId());
				djd.setInsurance(eTemp.getInsurance().getName());
				djd.setBalanceAmt(eTemp.getBalanceAmt());
				djd.setDoctor(eTemp.getDoctor().getName());
				djd.setCpt(eTemp.getCpt());
				djd.setDos(eTemp.getDos());
				djd.setRemark(eTemp.getRemark());
				djd.setTlRemark(eTemp.getTlRemark());
				djd.setPatientAccNo(eTemp.getPatientAccNo());
				djd.setPatientName(eTemp.getPatientName());
				djd.setSourceName(eTemp.getSourceName());
				djd.setWorkFlowName(eTemp.getWorkFlowName());
				djd.setDataBas(eTemp.getDataBas());
				arProdJsonData.add(djd);
			}
		}
		return arProdJsonData;
	}
}
