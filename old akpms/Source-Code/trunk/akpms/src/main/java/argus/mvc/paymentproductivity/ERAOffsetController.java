/**
 *
 */
package argus.mvc.paymentproductivity;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
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
import org.springframework.web.multipart.MultipartFile;

import argus.domain.Doctor;
import argus.domain.Files;
import argus.domain.PaymentBatch;
import argus.domain.User;
import argus.domain.paymentproductivity.OffsetRecord;
import argus.domain.paymentproductivity.PaymentProductivity;
import argus.domain.paymentproductivity.PaymentProductivityOffset;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.doctor.DoctorDao;
import argus.repo.file.FileDao;
import argus.repo.insurance.InsuranceDao;
import argus.repo.paymentbatch.PaymentBatchDao;
import argus.repo.paymentproductivity.PaymentProductivityDao;
import argus.repo.paymentproductivity.offset.PaymentOffsetManagerDao;
import argus.repo.paymentproductivity.offset.PaymentProductivityOffsetDao;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.ERAOffsetJsonData;
import argus.util.FopXmlToPdfGenrator;
import argus.util.JsonDataWrapper;
import argus.util.OffsetRecordJsonData;
import argus.util.PaymentBatchJsonData;
import argus.util.UserXstreamConverter;
import argus.util.XstreamDateConverter;
import argus.validator.EraOffsetValidator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author rajiv.k
 *
 */
@Controller
@RequestMapping(value = "/paymentproderaoffset")
@SessionAttributes({ Constants.PAYMENTPRODOFFSET, "arStep", "offsetResolve" })
@Scope("session")
public class ERAOffsetController {

	private static final Logger LOGGER = Logger
			.getLogger(ERAOffsetController.class);

	private static final String INSURANCE_LIST = "insuranceList";
	private static final String DOCTOR_LIST = "doctorList";

	@Autowired
	private PaymentProductivityOffsetDao paymentProductivityOffsetDao;

	@Autowired
	private EraOffsetValidator eraOffsetValidator;
	@Autowired
	private PaymentProductivityDao paymentProductivityDao;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserDao userDao;

	@Autowired
	private PaymentOffsetManagerDao managerDao;
	@Autowired
	private PaymentBatchDao batchDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private InsuranceDao insuranceDao;

	@Autowired
	private DoctorDao doctorDao;

	private Map<String, String> printReportCriteria;

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ArgusException {
		LOGGER.info("in [initBinder] method : ");
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String value) {
				try {
					setValue(ArProductivityHelper.initBinder(value));
				} catch (ArgusException e) {
					LOGGER.error(Constants.EXCEPTION, e);
					setValue(null);
				}
			}

			@Override
			public String getAsText() {
				if (getValue() != null) {
					return new SimpleDateFormat(Constants.DATE_FORMAT)
							.format((Date) getValue());
				}
				return "";
			}
		});
	}

	/**
	 * This function just load jsp "Payment Productivity Offset List", the list
	 * will load using flexigrid list
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String productivityList(Map<String, Object> map, WebRequest request,
			HttpSession session) {
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success", success);
		}

		session.removeAttribute(Constants.SUCCESS_UPDATE);
		populateDropDownLists(map);

		if (request.getParameter(Constants.STATUS) != null) {
			map.put(Constants.STATUS, request.getParameter(Constants.STATUS));
		}
		return "eraOffsetList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String productivityOffsetLoad(Model model, WebRequest request,
			Map<String, Object> map, HttpSession session,
			@RequestParam(required = false) String successUpdate) {
		LOGGER.info("in [productivityOffsetLoad] method");

		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success", success);
		}
		session.removeAttribute(Constants.SUCCESS_UPDATE);
		List<OffsetRecord> offsetRecordList = new ArrayList<OffsetRecord>();

		if (null != request & null != request.getParameter(Constants.ID)) {
			PaymentProductivityOffset paymentProductivityOffset = null;
			try {

				paymentProductivityOffset = paymentProductivityOffsetDao
						.findById(Long.parseLong(request
								.getParameter(Constants.ID)), true);

				model.addAttribute("offsetRecords",
						paymentProductivityOffset.getOffsetRecords());

			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
			offsetRecordList = paymentProductivityOffset.getOffsetRecords();
			model.addAttribute(Constants.PAYMENTPRODOFFSET,
					paymentProductivityOffset);
			model.addAttribute("arStep", paymentProductivityOffset);
			model.addAttribute("offsetResolve", paymentProductivityOffset);
			model.addAttribute("offsetRecords", offsetRecordList);
			model.addAttribute(Constants.MODE, Constants.EDIT);
			map.put(Constants.OPERATION_TYPE, Constants.EDIT);
			map.put(Constants.BUTTON_NAME, Constants.UPDATE);
			map.put(Constants.PAYMENT_BATCH,
					paymentProductivityOffset.getPaymentBatch());
			model.addAttribute(Constants.TICKET_NUMBER,
					paymentProductivityOffset.getPaymentBatch().getId());
			model.addAttribute(Constants.INSURANCE_NAME,
					paymentProductivityOffset.getPaymentBatch().getInsurance()
							.getName());
		} else {
			PaymentBatch paymentBatch = null;
			offsetRecordList.add(new OffsetRecord());
			if (request.getParameter(Constants.TICKET_NUMBER) != null) {
				try {
					paymentBatch = batchDao.findById(Long.valueOf(request
							.getParameter(Constants.TICKET_NUMBER)), true);
					map.put(Constants.PAYMENT_BATCH, paymentBatch);
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
					model.addAttribute(Constants.PAYMENTPRODOFFSET,
							new PaymentProductivityOffset());
					model.addAttribute("arStep",
							new PaymentProductivityOffset());
					model.addAttribute("offsetResolve",
							new PaymentProductivityOffset());
					model.addAttribute(Constants.MODE, Constants.ADD);
					map.put(Constants.OPERATION_TYPE, Constants.ADD);
					map.put(Constants.BUTTON_NAME, Constants.ADD);
					map.put(Constants.TICKET_NUMBER,
							request.getParameter(Constants.TICKET_NUMBER));
					model.addAttribute("offsetRecords", offsetRecordList);
				}
			}
			model.addAttribute(Constants.PAYMENTPRODOFFSET,
					new PaymentProductivityOffset());
			model.addAttribute(Constants.MODE, Constants.ADD);
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.ADD);
			if (paymentBatch != null && paymentBatch.getId() != null) {
				map.put(Constants.TICKET_NUMBER, paymentBatch.getId());
			}
			model.addAttribute("offsetRecords", offsetRecordList);
		}

		return "paymentProductivity/paymentProdERAOffset";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveProductivityOffset(
			@Valid @ModelAttribute(Constants.PAYMENTPRODOFFSET) PaymentProductivityOffset paymentProductivityOffset,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in [saveProductivityQueryToTL] method : ");
		List<OffsetRecord> offsetRecordsList = new ArrayList<OffsetRecord>();
		List<Long> toRemoveOffsetRecordList = new ArrayList<Long>();
		PaymentBatch paymentBatch = new PaymentBatch();
		StringBuilder toRemoveIds = new StringBuilder();
		int counter = 0;
		for (OffsetRecord offsetRecord : paymentProductivityOffset
				.getOffsetRecords()) {
			LOGGER.info(":: VALUES CPT ::" + offsetRecord.getCpt());
			LOGGER.info(":: VALUES DOS ::" + offsetRecord.getDos());
			LOGGER.info(":: VALUES AMT ::" + offsetRecord.getAmount());
			if ((offsetRecord.getAmount() == null || offsetRecord.getAmount()
					.longValue() == 0)
					&& (offsetRecord.getCpt() == null || offsetRecord.getCpt()
							.trim().length() == 0)
					&& offsetRecord.getDos() == null) {
				if (offsetRecord.getId() != null) {
					toRemoveOffsetRecordList.add(offsetRecord.getId());
					if (counter == 0) {
						toRemoveIds.append(offsetRecord.getId());
					} else {
						toRemoveIds.append("," + offsetRecord.getId());
					}
					counter++;
				}

			} else {
				offsetRecordsList.add(offsetRecord);
			}
			paymentProductivityOffset.setOffsetRecords(offsetRecordsList);
			model.addAttribute("offsetRecords",
					paymentProductivityOffset.getOffsetRecords());
		}

		if (request.getParameter("toRemoveIds") != null
				&& request.getParameter("toRemoveIds").trim().length() > 0) {
			toRemoveIds = new StringBuilder(request.getParameter("toRemoveIds"));
			String[] splittArray = null;
			splittArray = request.getParameter("toRemoveIds").split(",");
			for (String value : splittArray) {
				toRemoveOffsetRecordList.add(Long.valueOf(value));
			}

		}

		eraOffsetValidator.validate(paymentProductivityOffset, result);
		if (!result.hasErrors()) {
			LOGGER.info(" :: NO ERROR :: ");

			if (paymentProductivityOffset.getId() != null) {

				if (paymentProductivityOffset.getArAttachment() == null
						|| paymentProductivityOffset.getArAttachment().getId() == null) {
					paymentProductivityOffset.setArAttachment(null);
				}

				try {
					paymentProductivityOffsetDao
							.updatePaymentProductivityOffset(paymentProductivityOffset);

					paymentBatch = batchDao.findById(paymentProductivityOffset
							.getPaymentBatch().getId(), false);

					// batchDao.updatePaymentBatch(paymentBatch);
					session.setAttribute(
							Constants.SUCCESS_UPDATE,
							messageSource
									.getMessage(
											"paymentProductivityEraOffset.updatedSuccessfully",
											null, Locale.ENGLISH).trim());

					if (paymentProductivityOffset.isOffset()) {
						map.put("batchId", paymentBatch.getId());
						map.put(Constants.TICKET_NUMBER, paymentBatch.getId());
						return "redirect:/paymentproderaoffset/add";
					}
					if (!toRemoveOffsetRecordList.isEmpty()) {
						paymentProductivityOffsetDao
								.deleteOffsetRecords(toRemoveOffsetRecordList);
					}
				} catch (ArgusException e) {
					LOGGER.error(Constants.EXCEPTION, e);
				} finally {
					LOGGER.info("FINALLY ");

				}

			} else {

				paymentProductivityOffset.setArAttachment(null);
				paymentProductivityOffset.setStatus("Pending");
				try {
					paymentProductivityOffsetDao
							.addPaymentProductivityOffset(paymentProductivityOffset);
					paymentBatch = batchDao.findById(paymentProductivityOffset
							.getPaymentBatch().getId(), false);
					// batchDao.updatePaymentBatch(paymentBatch);
					session.setAttribute(
							Constants.SUCCESS_UPDATE,
							messageSource
									.getMessage(
											"paymentProductivityEraOffset.addedSuccessfully",
											null, Locale.ENGLISH).trim());
					if (paymentProductivityOffset.isOffset()) {
						map.put(Constants.TICKET_NUMBER, paymentBatch.getId());
						return "redirect:/paymentproderaoffset/add";
					}

				} catch (ArgusException e) {
					LOGGER.info(Constants.EXCEPTION, e);
				}
			}

			return "redirect:/paymentproderaoffset";

		} else {

			map.put(Constants.TICKET_NUMBER, paymentProductivityOffset
					.getPaymentBatch().getId());
			map.put(Constants.INSURANCE_NAME, paymentProductivityOffset
					.getPaymentBatch().getInsurance().getName());
			map.put(Constants.PAYMENT_BATCH,
					paymentProductivityOffset.getPaymentBatch());

			LOGGER.info(" :: ERROR :: ");

			if (paymentProductivityOffset != null
					& paymentProductivityOffset.getId() != null) {
				LOGGER.info(" :: paymentProductivityOffset IS NOT NULL :: ");
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				map.put(Constants.GET_BUTTON, Constants.GET_DETAILS);
				if (paymentProductivityOffset.getOffsetRecords() == null
						|| paymentProductivityOffset.getOffsetRecords()
								.isEmpty()) {
					OffsetRecord offsetRecord = new OffsetRecord();
					offsetRecord
							.setProductivityOffset(paymentProductivityOffset);
					paymentProductivityOffset.getOffsetRecords().add(
							offsetRecord);
				}
				model.addAttribute("offsetRecords",
						paymentProductivityOffset.getOffsetRecords());

			} else {
				if (paymentProductivityOffset.getOffsetRecords() == null
						|| paymentProductivityOffset.getOffsetRecords()
								.isEmpty()) {
					OffsetRecord offsetRecord = new OffsetRecord();
					paymentProductivityOffset.getOffsetRecords().add(
							offsetRecord);
				}
				LOGGER.info(" :: paymentProductivityOffset Id IS NULL :: ");
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.ADD);
				map.put(Constants.GET_BUTTON, Constants.GET_DETAILS);
			}

			map.put("toRemoveIds", toRemoveIds);
			return "paymentProductivity/paymentProdERAOffset";
		}
	}

	@RequestMapping(value = "/arUpdate", method = RequestMethod.POST)
	public String saveARStep(
			@Valid @ModelAttribute("arStep") PaymentProductivityOffset paymentProductivityOffset,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in [saveProductivityQueryToTL] method : ");
		eraOffsetValidator.validate(paymentProductivityOffset, result);
		try {
			if (!result.hasErrors()) {
				LOGGER.info(" :: NO ERROR :: ");

				if (paymentProductivityOffset.getId() != null) {

					String isFileDeleted = request.getParameter("fileDeleted");
					if (paymentProductivityOffset.getArAttachment() != null
							&& paymentProductivityOffset.getArAttachment()
									.getAttachedFile().getSize() != 0) {
						AkpmsUtil.fileUpload(
								paymentProductivityOffset.getArAttachment(),
								fileDao, messageSource);
					} else if (isFileDeleted != null
							&& !isFileDeleted.trim().isEmpty()
							&& isFileDeleted.equals("true")) {

						fileDao.deleteAttachedFile(paymentProductivityOffset
								.getArAttachment().getId());
						paymentProductivityOffset.setArAttachment(null);

					} else if (paymentProductivityOffset.getArAttachment() == null
							|| paymentProductivityOffset.getArAttachment()
									.getId() == null) {
						paymentProductivityOffset.setArAttachment(null);
					}

					paymentProductivityOffset.setStatus("AR Step Completed");

					paymentProductivityOffsetDao
							.updatePaymentProductivityOffset(paymentProductivityOffset);

					session.setAttribute(
							Constants.SUCCESS_UPDATE,
							messageSource
									.getMessage(
											"paymentProductivityEraOffset.updatedSuccessfully",
											null, Locale.ENGLISH).trim());

					return "redirect:/paymentproderaoffset";

					// batchDao.updatePaymentBatch(paymentBatch);
				}
			}
		} catch (ArgusException e) {
			LOGGER.error(Constants.EXCEPTION, e);
		} finally {
			LOGGER.info("FINALLY ");

		}

		return "redirect:/paymentproderaoffset";
	}

	@RequestMapping(value = "/offsetResolve", method = RequestMethod.POST)
	public String saveOffsetResolve(
			@ModelAttribute("offsetResolve") PaymentProductivityOffset paymentProductivityOffset,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in [saveProductivityQueryToTL] method : ");
		eraOffsetValidator.validate(paymentProductivityOffset, result);
		try {
			if (!result.hasErrors()) {
				LOGGER.info(" :: NO ERROR :: ");

				if (paymentProductivityOffset.getId() != null) {

					if (paymentProductivityOffset.getArAttachment() == null
							|| paymentProductivityOffset.getArAttachment()
									.getId() == null) {
						paymentProductivityOffset.setArAttachment(null);
					}

					paymentProductivityOffset.setStatus("Offset Resolve");

					paymentProductivityOffsetDao
							.updatePaymentProductivityOffset(paymentProductivityOffset);

					session.setAttribute(
							Constants.SUCCESS_UPDATE,
							messageSource
									.getMessage(
											"paymentProductivityEraOffset.updatedSuccessfully",
											null, Locale.ENGLISH).trim());

					return "redirect:/paymentproderaoffset";

					// batchDao.updatePaymentBatch(paymentBatch);
				}
			}
		} catch (ArgusException e) {
			LOGGER.error(Constants.EXCEPTION, e);
		} finally {
			LOGGER.info("FINALLY ");

		}

		return "redirect:/paymentproderaoffset";
	}

	/**
	 * function to use for flexigrid list
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/json", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JsonDataWrapper<ERAOffsetJsonData> listAllOffset(WebRequest request) {
		LOGGER.info("payment productivity::in json method");

		int page = 1; // default 1 for page number in json wrapper
		int rp = 0;

		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request != null) {
			page = Integer.parseInt(request.getParameter("page"));
			if (request.getParameter("rp") != null) {
				rp = Integer.parseInt(request.getParameter("rp"));
				try {
					orderClauses.put("limit", "" + rp);
				} catch (Exception e) {
					LOGGER.debug("rp[Record pre Page] not coming or not an integer in request ");
				}
			}

			if (request.getParameter("page") != null) {
				try {
					page = Integer.parseInt(request.getParameter("page"));
					orderClauses.put("offset", "" + ((rp * page) - rp));
				} catch (Exception e) {
					LOGGER.debug("Exception during parsing");
				}
			}

			if (request.getParameter("sortorder") != null) {
				orderClauses.put("sortBy", request.getParameter("sortorder"));
			}

			if (request.getParameter("sortname") != null) {
				orderClauses.put("orderBy", request.getParameter("sortname"));
			}

			if (request.getParameter("qtype") != null
					&& request.getParameter("query") != null
					&& !request.getParameter("query").isEmpty()) {
				whereClauses.put(request.getParameter("qtype"),
						request.getParameter("query"));
			}

			if (request.getParameter(Constants.POSTED_BY_ID) != null
					& request.getParameter(Constants.POSTED_BY_ID).trim()
							.length() > 0) {
				String postedBy = request.getParameter(Constants.POSTED_BY_ID);
				whereClauses.put(Constants.POSTED_BY_ID, postedBy);
			}

			// set posted from date
			if (request.getParameter(Constants.DATE_POSTED_FROM) != null
					&& request.getParameter(Constants.DATE_POSTED_FROM).trim()
							.length() > 0) {
				LOGGER.info("DATE_POSTED_FROM= "
						+ request.getParameter(Constants.DATE_POSTED_FROM));
				whereClauses.put(Constants.DATE_POSTED_FROM,
						request.getParameter(Constants.DATE_POSTED_FROM));
			}

			// set posted to date
			if (request.getParameter(Constants.DATE_POSTED_TO) != null
					&& request.getParameter(Constants.DATE_POSTED_TO).trim()
							.length() > 0) {
				LOGGER.info("DATE_POSTED_TO= "
						+ request.getParameter(Constants.DATE_POSTED_TO));
				whereClauses.put(Constants.DATE_POSTED_TO,
						request.getParameter(Constants.DATE_POSTED_TO));
			}

			if (request.getParameter(Constants.TICKET_NUMBER) != null
					&& request.getParameter(Constants.TICKET_NUMBER).trim()
							.length() > 0) {
				String ticketNumber = request
						.getParameter(Constants.TICKET_NUMBER);

				whereClauses.put(Constants.TICKET_NUMBER, ticketNumber);
			}
			if (request.getParameter(Constants.ACCOUNT_NUMBER) != null
					&& request.getParameter(Constants.ACCOUNT_NUMBER).trim()
							.length() > 0) {
				String accountNo = request
						.getParameter(Constants.ACCOUNT_NUMBER);

				whereClauses.put(Constants.ACCOUNT_NUMBER, accountNo);
			}

			if (request.getParameter(Constants.PATIENT_NAME) != null
					&& request.getParameter(Constants.PATIENT_NAME).trim()
							.length() > 0) {
				String patientName = request
						.getParameter(Constants.PATIENT_NAME);
				Pattern percentPattern = Pattern.compile("^[%]*");
				Pattern underScorePattern = Pattern.compile("^[_]*");
				Matcher pecentMatcher = percentPattern.matcher(patientName);
				Matcher underScoreMatcher = underScorePattern
						.matcher(patientName);
				if (pecentMatcher.matches()) {
					patientName = Constants.QUERY_WILDCARD_PERCENTAGE;
				}
				if (underScoreMatcher.matches()) {
					patientName = Constants.QUERY_WILDCARD_UNDERSCORE;
				}
				whereClauses.put(Constants.PATIENT_NAME, patientName);
			}
			if (request.getParameter(Constants.CHECK_NUMBER) != null
					&& request.getParameter(Constants.CHECK_NUMBER).trim()
							.length() > 0) {
				String checkNo = request.getParameter(Constants.CHECK_NUMBER);

				whereClauses.put(Constants.CHECK_NUMBER, checkNo);
			}

			if (request.getParameter(Constants.OFFSET_TICKET_NUMBER) != null
					&& request.getParameter(Constants.OFFSET_TICKET_NUMBER)
							.trim().length() > 0) {
				whereClauses.put(Constants.OFFSET_TICKET_NUMBER,
						request.getParameter(Constants.OFFSET_TICKET_NUMBER));
			}

			if (request.getParameter(Constants.DOCTOR_ID) != null
					&& request.getParameter(Constants.DOCTOR_ID).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("billing doctor id = "
						+ request.getParameter(Constants.DOCTOR_ID));
				whereClauses.put(Constants.DOCTOR_ID,
						request.getParameter(Constants.DOCTOR_ID));
			}

			if (request.getParameter(Constants.INSURANCE_ID) != null
					&& request.getParameter(Constants.INSURANCE_ID).trim()
							.length() > Constants.ZERO) {
				LOGGER.info("insurance id = "
						+ request.getParameter(Constants.INSURANCE_ID));
				whereClauses.put(Constants.INSURANCE_ID,
						request.getParameter(Constants.INSURANCE_ID));
			}

			if (request.getParameter(Constants.STATUS) != null
					&& request.getParameter(Constants.STATUS).trim().length() > Constants.ZERO) {
				LOGGER.info("status = "
						+ request.getParameter(Constants.STATUS));
				whereClauses.put(Constants.STATUS,
						request.getParameter(Constants.STATUS));
			}

		} else {
			LOGGER.info("request object is coming null");
		}
		printReportCriteria = whereClauses;
		int totalRows = 0;
		try {
			totalRows = paymentProductivityOffsetDao.totalRecord(whereClauses);
			if (totalRows > 0) {
				List<PaymentProductivityOffset> rows = paymentProductivityOffsetDao
						.findAll(orderClauses, whereClauses, true);
				List<ERAOffsetJsonData> djd = getJsonData(rows);
				JsonDataWrapper<ERAOffsetJsonData> jdw = new JsonDataWrapper<ERAOffsetJsonData>(
						page, totalRows, djd);
				return jdw;
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.toString());
		}

		return null;
	}

	/**
	 * this method set value in PaymentProductivityJsonData list
	 *
	 * @param rows
	 * @return
	 */
	private List<ERAOffsetJsonData> getJsonData(
			List<PaymentProductivityOffset> rows) {
		List<ERAOffsetJsonData> deptJsonData = new ArrayList<ERAOffsetJsonData>();

		if (rows != null && rows.size() > 0) {

			for (PaymentProductivityOffset paymentProductivityOffset : rows) {
				ERAOffsetJsonData djd = new ERAOffsetJsonData();
				djd.setId(paymentProductivityOffset.getId());
				djd.setBatchId(paymentProductivityOffset.getPaymentBatch()
						.getId());

				djd.setPatientName(paymentProductivityOffset.getPatientName());
				djd.setAccountNumber(paymentProductivityOffset
						.getAccountNumber());
				djd.setRemark(paymentProductivityOffset.getRemark());
				djd.setPostedBy(paymentProductivityOffset.getCreatedBy()
						.getFirstName()
						+ " "
						+ paymentProductivityOffset.getCreatedBy()
								.getLastName());
				djd.setChkNumber(paymentProductivityOffset.getChkNumber());

				if (paymentProductivityOffset.getCreatedOn() != null) {
					djd.setCreatedOn(AkpmsUtil.akpmsDateFormat(
							paymentProductivityOffset.getCreatedOn(),
							Constants.DATE_FORMAT));
				}
				djd.setStatus(paymentProductivityOffset.getStatus());
				djd.setOffsetTicketNumber(paymentProductivityOffset
						.getOffsetTicketNumber());
				djd.setDoctorName(paymentProductivityOffset.getPaymentBatch()
						.getDoctor().getName());
				djd.setInsuranceName(paymentProductivityOffset
						.getPaymentBatch().getInsurance().getName());
				djd.setChkDate(AkpmsUtil.akpmsDateFormat(
						paymentProductivityOffset.getChkDate(),
						Constants.DATE_FORMAT));

				double totalAmount = 0.0;
				for (OffsetRecord offsetRecord : paymentProductivityOffset
						.getOffsetRecords()) {
					totalAmount = totalAmount + offsetRecord.getAmount();
				}

				if (totalAmount > 0)
					totalAmount = Math.round(totalAmount * 100.0) / 100.0;

				long postedId = 0;

				try {
					postedId = managerDao.hasPosted(paymentProductivityOffset
							.getId());
					if (postedId != 0) {
						djd.setOperation("EDIT");
						djd.setOffsetPostId(postedId);
					} else {
						djd.setOperation("ADD");
					}
				} catch (Exception e) {
					djd.setOperation("ADD");
				}
				djd.setRecordList(paymentProductivityOffsetDao
						.getPostedRecord(paymentProductivityOffset.getId()));
				djd.setTotalAmount(totalAmount);
				deptJsonData.add(djd);
			}

		}

		return deptJsonData;
	}

	@RequestMapping(value = "/getRecord", method = RequestMethod.GET)
	@ResponseBody
	public List<OffsetRecordJsonData> getRecord(@RequestParam long id) {
		LOGGER.info("in getRecord method");
		LOGGER.info("id = " + id);
		List<OffsetRecordJsonData> recordPosted = new ArrayList<OffsetRecordJsonData>();

		try {

			recordPosted = paymentProductivityOffsetDao.getPostedRecord(id);
			if (recordPosted.size() > 0) {
				return recordPosted;
			}

		} catch (Exception e) {
			LOGGER.info(Constants.EXCEPTION + " :: " + e.getMessage());
		}

		return recordPosted;
	}

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String offsetReportLoad(WebRequest request, Model model,
			Map<String, Object> map) {
		map.put("typeList", this.getProductivityTypes());
		try {

			map.put("postedBy", userDao.getUserByDept());

		} catch (ArgusException ar) {
			LOGGER.error(Constants.EXCEPTION, ar);
		}
		return "offsetWorkflowReport";
	}

	private Map<Integer, String> getProductivityTypes() {
		Map<Integer, String> typeList = new HashMap<Integer, String>();
		typeList.put(1, messageSource.getMessage("1", null, Locale.ENGLISH));
		typeList.put(2, messageSource.getMessage("2", null, Locale.ENGLISH));
		typeList.put(3, messageSource.getMessage("3", null, Locale.ENGLISH));
		return typeList;
	}

	@RequestMapping(value = "getticketdetail/json", method = RequestMethod.POST)
	@ResponseBody
	public Object[] getDetail(
			@RequestParam(value = "batchId", required = false) String batchId,
			WebRequest request) {
		Object[] obj = null;
		PaymentBatch paymentBatch = new PaymentBatch();
		obj = getPaymentBatch(obj, batchId, paymentBatch);
		return obj;

	}

	public Object[] getPaymentBatch(Object[] obj, String batchId,
			PaymentBatch paymentBatch) {

		try {
			if (batchId != null && !batchId.trim().isEmpty()) {
				paymentBatch = batchDao.findById(Long.valueOf(batchId), true);
			}
		} catch (Exception e) {

			LOGGER.error(Constants.EXCEPTION, e);
			LOGGER.info("going to send error message on exception");
			obj = new Object[2];
			obj[0] = "err";
			obj[1] = messageSource.getMessage("batch.notAvailable", null,
					Locale.ENGLISH).trim();
			return obj;
		}

		if (paymentBatch != null && paymentBatch.getId() != null) {
			PaymentBatchJsonData paymentBatchJsonData = new PaymentBatchJsonData();
			// if (paymentBatch.getOffsetType().equals("offline")) {
			// LOGGER.info("Offline Payment Batch");
			// obj = new Object[2];
			// obj[0] = Constants.ERR;
			// obj[1] = messageSource.getMessage("batch.offline", null,
			// Locale.ENGLISH).trim();
			// return obj;
			// } else
			if (paymentBatch.getPostedBy() == null) {
				LOGGER.info("Batch not posted yet");
				obj = new Object[2];
				obj[0] = Constants.ERR;
				obj[1] = messageSource.getMessage("batch.notPosted", null,
						Locale.ENGLISH).trim();
				return obj;
			} else {
				try {
					PaymentProductivity paymentProductivity = paymentProductivityDao
							.findByTicketNo(paymentBatch.getId());
					if (paymentProductivity != null
							&& (paymentProductivity.getWorkFlowId() == Constants.QUERY_TO_TL_WORK_FLOW)) {
						obj = new Object[2];
						obj[0] = Constants.ERR;
						obj[1] = messageSource.getMessage("batch.queryToTL",
								null, Locale.ENGLISH).trim();
						return obj;
					} else {
						paymentBatchJsonData.setId(paymentBatch.getId());

						paymentBatchJsonData.setInsurance(paymentBatch
								.getInsurance().getName());
						paymentBatchJsonData.setDoctor(paymentBatch.getDoctor()
								.getName());
						if (paymentBatch.getDepositAmount() != null
								&& paymentBatch.getDepositAmount() != Constants.ZERO) {
							paymentBatchJsonData.setDepositAmount(paymentBatch
									.getDepositAmount());
						} else {
							paymentBatchJsonData.setDepositAmount(paymentBatch
									.getNdba());
						}

						obj = new Object[2];
						obj[0] = paymentBatchJsonData;
						return obj;

					}

				} catch (Exception e) {
					paymentBatchJsonData.setId(paymentBatch.getId());

					paymentBatchJsonData.setInsurance(paymentBatch
							.getInsurance().getName());
					paymentBatchJsonData.setDoctor(paymentBatch.getDoctor()
							.getName());
					if (paymentBatch.getDepositAmount() != null
							&& paymentBatch.getDepositAmount() != Constants.ZERO) {
						paymentBatchJsonData.setDepositAmount(paymentBatch
								.getDepositAmount());
					} else {
						paymentBatchJsonData.setDepositAmount(paymentBatch
								.getNdba());
					}

					obj = new Object[2];
					obj[0] = paymentBatchJsonData;
					return obj;
				}
			}
		} else {
			obj = new Object[2];
			obj[0] = Constants.ERR;
			obj[1] = messageSource.getMessage("batch.notAvailable", null,
					Locale.ENGLISH).trim();
			return obj;
		}

	}

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public void printReport(HttpServletResponse response, HttpSession session) {

		try {
			String xmlString = null;
			List<PaymentProductivityOffset> rows = paymentProductivityOffsetDao
					.findAll(null, printReportCriteria, true);
			if (rows != null && !rows.isEmpty()) {
				for (PaymentProductivityOffset paymentProductivityOffset : rows) {
					double totalAmount = 0.0;
					for (OffsetRecord offsetRecord : paymentProductivityOffset
							.getOffsetRecords()) {
						totalAmount += offsetRecord.getAmount();
					}
					paymentProductivityOffset.setTotalAmount(totalAmount);
				}
				xmlString = getXmlData(rows);
				// adding XML header
				xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
						+ xmlString;
			} else {
				String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
				xmlString = xml + "<empty>No Record found.</empty>";
			}
			LOGGER.info(xmlString);
			// Generate PDF
			FopXmlToPdfGenrator.generatePDFReport(response, session,
					"PaymentProductivityOffsetStylesheet_ListView_FO.xsl",
					xmlString, messageSource.getMessage(
							"report.paymentProductivity.name", null,
							Locale.ENGLISH));

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
	}

	@RequestMapping(value = "/printhtmlreport", method = RequestMethod.GET)
	public String printHtmlReport(Map<String, Object> map) {

		List<PaymentProductivityOffset> rows = null;
		try {
			rows = paymentProductivityOffsetDao.findAll(null,
					printReportCriteria, true);
			if (rows != null && !rows.isEmpty()) {
				for (PaymentProductivityOffset paymentProductivityOffset : rows) {
					double totalAmount = 0.0;
					for (OffsetRecord offsetRecord : paymentProductivityOffset
							.getOffsetRecords()) {
						totalAmount += offsetRecord.getAmount();
					}
					paymentProductivityOffset.setTotalAmount(totalAmount);
				}

			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}

		map.put("PRINT_REPORT_CRITERIA", printReportCriteria);
		map.put("SHOW_FOOTER", false);
		map.put("eraOffsetList", rows);
		return "eraOffsetListToPrint";
	}

	public String getXmlData(
			List<PaymentProductivityOffset> paymentProductivityOffsetList) {
		XStream xstream = new XStream(new DomDriver());
		xstream.setMode(XStream.NO_REFERENCES);// XPATH_ABSOLUTE_REFERENCES);
		xstream.processAnnotations(PaymentProductivityOffset.class);
		xstream.processAnnotations(OffsetRecord.class);
		xstream.processAnnotations(PaymentBatch.class);
		xstream.processAnnotations(User.class);
		xstream.processAnnotations(Doctor.class);
		xstream.registerConverter(new UserXstreamConverter());
		xstream.registerConverter(new XstreamDateConverter());
		return xstream.toXML(paymentProductivityOffsetList);
		// return xml;
	}

	public void fileUpload(PaymentProductivityOffset paymentProductivityOffset) {

		try {
			Files newFile = paymentProductivityOffset.getArAttachment();
			if (newFile.getAttachedFile().getSize() != 0) {

				MultipartFile file = newFile.getAttachedFile();
				if (null != file && file.getSize() > 0) {

					byte[] fileData = file.getBytes();
					String originalFileName = file.getOriginalFilename();
					Long timeMili = System.currentTimeMillis();

					StringBuilder systemName = new StringBuilder();
					if (newFile.getProcessManual() != null) {

						systemName.append(newFile.getProcessManual().getId());
						systemName.append("_");
						if (newFile.getSubProcessManualId() != null) {
							systemName.append(newFile.getSubProcessManualId());
						} else {
							systemName.append("0");
						}
						systemName.append("_");
					} else {
						LOGGER.info(" Process Manual not found in files object");
					}

					systemName.append(timeMili);
					systemName.append("_");
					systemName.append(originalFileName);

					String realPath = messageSource.getMessage(
							"attachments.storage.space.arProductivity", null,
							Locale.ENGLISH).trim();

					LOGGER.info("real Path = " + realPath);

					File dir = new File(realPath);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					File fileNamePath = new File(dir, systemName.toString());
					OutputStream outputStream = new FileOutputStream(
							fileNamePath);
					outputStream.write(fileData);
					outputStream.close();

					newFile.setName(originalFileName);
					newFile.setSystemName(systemName.toString());
					/*
					 * if (paymentProductivityOffset.getId() != null) {
					 * fileDao.updateAttachement(newFile); } else {
					 * fileDao.saveAttachement(newFile); }
					 */
					if (paymentProductivityOffset.getId() == null) {
						fileDao.saveAttachement(newFile);
					}
					// set message here
				} else {
					LOGGER.info("there is no attachment coming in file object");
				}
			} else {
				LOGGER.info("Attached file Object is coming null");
			}

		} catch (Exception e) {

		}
	}

	@RequestMapping(value = "/changeStatus", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public void changeStatus(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "ids", required = false) String ids,
			@RequestParam(value = "ticketNumber", required = false) String ticketNumber,
			@RequestParam(value = "status", required = false) String status,
			HttpSession session) {
		LOGGER.info("In change status method ");
		String[] splittArray = null;
		try {

			if (ids != null && !ids.equalsIgnoreCase("")) {
				splittArray = ids.split(",");
				LOGGER.info("Split Array" + ids + " " + splittArray.length);
				paymentProductivityOffsetDao.updateStatus(ids, ticketNumber,
						status);
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
	}

	private void populateDropDownLists(Map<String, Object> map) {
		LOGGER.info("in populateDropDownLists");
		Map<String, String> whereClause = new HashMap<String, String>();
		try {
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");

			map.put(INSURANCE_LIST, insuranceDao.findAll(null, whereClause));
		} catch (Exception e) {
			LOGGER.info("EXception while getting insurance list");
			LOGGER.info(e.getMessage());
			map.put(INSURANCE_LIST, null);
		}
		try {
			whereClause.clear();
			whereClause.put(Constants.STATUS, Constants.ACTIVATE + "");
			whereClause.put(Constants.PARENT_ONLY, "1");
			map.put(DOCTOR_LIST, doctorDao.findAll(null, whereClause, false));
		} catch (Exception e) {
			LOGGER.info("EXception while getting doctor list");
			LOGGER.info(e.getMessage());
			map.put(DOCTOR_LIST, null);
		}

		try {
			map.put("postedBy", userDao.getUserByDept());
		} catch (ArgusException e) {
			LOGGER.info("Exception while getting user list");
			LOGGER.info(e.getMessage());
		}
		LOGGER.info("out populateDropDownLists");
	}
}
