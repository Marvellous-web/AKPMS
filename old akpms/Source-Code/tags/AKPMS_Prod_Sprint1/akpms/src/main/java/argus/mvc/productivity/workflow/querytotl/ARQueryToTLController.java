package argus.mvc.productivity.workflow.querytotl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import argus.domain.ArProductivity;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.productivity.ArProductivityDao;
import argus.util.ArProductivityJsonData;
import argus.util.Constants;
import argus.util.JsonDataWrapper;

@Controller
@RequestMapping(value = "/flows/arquerytotl")
@SessionAttributes({ Constants.ARPRODUCTIVITY })
public class ARQueryToTLController {

	private static final Log LOGGER = LogFactory
			.getLog(ARQueryToTLController.class);


	@Autowired
	private ArProductivityDao arProductivityDao;

	@RequestMapping(method = RequestMethod.GET)
	public String queryToTLList() {
		return "flows/arProductivity/queryToTLList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String queryToTLLoad(Map<String, Object> map, WebRequest request) {
		ArProductivity arProductivity = null;
		if (request.getParameter(Constants.ARPRODUCTIVITY_ID) != null
				&& !request.getParameter(Constants.ARPRODUCTIVITY_ID)
						.equals("")) {
			try {
				arProductivity = arProductivityDao.findById(Long
						.parseLong(request
								.getParameter(Constants.ARPRODUCTIVITY_ID)));
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}
			map.put(Constants.ARPRODUCTIVITY, arProductivity);
		}
		return "flows/arproductivity/querytotl";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String queryToTLProcess(@ModelAttribute(Constants.ARPRODUCTIVITY) ArProductivity arProductivity,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map){
		if (arProductivity.getId() != null) {
			try {
			arProductivityDao.updateProductivity(arProductivity);
			} catch (Exception e) {
				LOGGER.error(Constants.EXCEPTION, e);
			}
		}
		return "flows/arProductivity/queryToTLList";
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
			whereClauses.put(Constants.WORKFLOW_ID,
					String.valueOf(Constants.QUERY_TO_TL_WORKFLOW));
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
