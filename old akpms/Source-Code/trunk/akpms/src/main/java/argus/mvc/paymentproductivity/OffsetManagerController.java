/**
 *
 */
package argus.mvc.paymentproductivity;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import argus.domain.paymentproductivity.OffsetPostingRecord;
import argus.domain.paymentproductivity.PaymentPostingByOffSetManager;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.paymentbatch.PaymentBatchDao;
import argus.repo.paymentproductivity.offset.PaymentOffsetManagerDao;
import argus.repo.processManual.ProcessManualDao;
import argus.repo.user.UserDao;
import argus.util.AkpmsUtil;
import argus.util.Constants;
import argus.util.FopXmlToPdfGenrator;
import argus.util.JsonDataWrapper;
import argus.util.OffsetPostingJsonData;
import argus.util.OffsetPostingRecordJsonData;
import argus.util.PaymentBatchJsonData;
import argus.util.UserXstreamConverter;
import argus.util.XstreamDateConverter;
import argus.validator.OffsetManagerValidator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author vishal.joshi
 *
 */
@Controller
@RequestMapping(value = "/paymentoffsetmanager")
@SessionAttributes({ Constants.PAYMENT_OFFSET_MANAGER })
public class OffsetManagerController {

	private static final Logger LOGGER = Logger
			.getLogger(OffsetManagerController.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PaymentOffsetManagerDao managerDao;

	@Autowired
	private PaymentBatchDao batchDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private OffsetManagerValidator offsetManagerValidator;

	@Autowired
	private ProcessManualDao processManualDao;

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

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String productivityOffsetLoad(Model model, WebRequest request,
			Map<String, Object> map,
			@RequestParam(required = false) String successUpdate,
			HttpSession session) {
		LOGGER.info("in [productivityOffsetLoad] method");

		List<OffsetPostingRecord> postingRecordList = new ArrayList<OffsetPostingRecord>();
		postingRecordList.add(new OffsetPostingRecord());

		map.put("offsetPostingRecord", new OffsetPostingRecord());
		map.put("postingRecord", postingRecordList);

		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success", success);
		}
		session.removeAttribute(Constants.SUCCESS_UPDATE);

		PaymentBatch paymentBatch = null;
		if (null != request & null != request.getParameter(Constants.ID)) {
			PaymentPostingByOffSetManager byOffSetManager = null;

			try {
				byOffSetManager = managerDao.findById(
						Long.parseLong(request.getParameter(Constants.ID)),
						true);
				postingRecordList = byOffSetManager.getPostingRecords();
				paymentBatch = byOffSetManager.getPaymentBatch();
				model.addAttribute("postingRecord", postingRecordList);
			} catch (Exception e) {
				LOGGER.info("in OffsetManagerController.java-productivityOffsetLoad");
				LOGGER.error(Constants.EXCEPTION, e);
			}
			model.addAttribute(Constants.PAYMENT_OFFSET_MANAGER,
					byOffSetManager);
			model.addAttribute(Constants.MODE, Constants.EDIT);
			map.put(Constants.OPERATION_TYPE, Constants.EDIT);
			map.put(Constants.BUTTON_NAME, Constants.UPDATE);
			map.put(Constants.OFFSET, byOffSetManager.getId());
			map.put("paymentBatch", paymentBatch);
			if (paymentBatch != null) {
				map.put(Constants.BATCH_ID, paymentBatch.getId());
			} else if (request.getParameter(Constants.BATCH_ID) != null) {
				map.put(Constants.BATCH_ID,
						request.getParameter(Constants.BATCH_ID));
			}
		} else {
			if (request.getParameter(Constants.BATCH_ID) != null) {
				try {
					paymentBatch = batchDao.findById(Long.valueOf(request
							.getParameter(Constants.BATCH_ID)), true);
					map.put(Constants.PAYMENT_BATCH, paymentBatch);
				} catch (Exception e) {
					LOGGER.error(Constants.EXCEPTION, e);
				}
				map.put(Constants.BATCH_ID,
						request.getParameter(Constants.BATCH_ID));
			}

			model.addAttribute(Constants.PAYMENT_OFFSET_MANAGER,
					new PaymentPostingByOffSetManager());
			model.addAttribute(Constants.MODE, Constants.ADD);
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
			map.put(Constants.BUTTON_NAME, Constants.ADD);
		}

		return "paymentProductivity/paymentOffsetManager";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveOffsetPosting(
			@Valid @ModelAttribute(Constants.PAYMENT_OFFSET_MANAGER) PaymentPostingByOffSetManager offSetManager,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in [saveOffsetPosting] method : ");

		List<OffsetPostingRecord> offsetPostingRecordList = new ArrayList<OffsetPostingRecord>();
		List<Long> toRemoveOffsetPostingRecordList = new ArrayList<Long>();

		PaymentBatch paymentBatch = null;
		StringBuilder toRemoveIds = new StringBuilder();
		int counter = 0;
		for (OffsetPostingRecord offsetPostingRecord : offSetManager
				.getPostingRecords()) {
			LOGGER.info(":: VALUES CPT ::" + offsetPostingRecord.getCpt());
			LOGGER.info(":: VALUES FROM ::" + offsetPostingRecord.getDosFrom());
			LOGGER.info(":: VALUES TO ::" + offsetPostingRecord.getDosTo());
			LOGGER.info(":: VALUES AMT ::" + offsetPostingRecord.getAmount());
			if ((offsetPostingRecord.getAmount() == null || offsetPostingRecord
					.getAmount().longValue() == 0)
					&& (offsetPostingRecord.getCpt() == null || offsetPostingRecord
							.getCpt().trim().length() == 0)
					&& offsetPostingRecord.getDosFrom() == null
					&& offsetPostingRecord.getDosTo() == null) {
				if (offsetPostingRecord.getId() != null) {
					toRemoveOffsetPostingRecordList.add(offsetPostingRecord
							.getId());
					if (counter == 0) {
						toRemoveIds.append(offsetPostingRecord.getId());
					} else {
						toRemoveIds.append("," + offsetPostingRecord.getId());
					}
					counter++;

				}

			} else {
				offsetPostingRecordList.add(offsetPostingRecord);
			}
		}
		if (request.getParameter("toRemoveIds") != null
				&& request.getParameter("toRemoveIds").trim().length() > 0) {
			toRemoveIds = new StringBuilder(request.getParameter("toRemoveIds"));
			String[] splittArray = null;
			splittArray = request.getParameter("toRemoveIds").split(",");
			for (String value : splittArray) {
				toRemoveOffsetPostingRecordList.add(Long.valueOf(value));
			}

		}
		offSetManager.setPostingRecords(offsetPostingRecordList);
		model.addAttribute("postingRecord", offSetManager.getPostingRecords());

		offsetManagerValidator.validate(offSetManager, result);
		try {
			paymentBatch = batchDao.findById(offSetManager.getPaymentBatch()
					.getId(), true);
			map.put(Constants.PAYMENT_BATCH, paymentBatch);
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		if (!result.hasErrors()) {
			LOGGER.info(" :: NO ERROR :: ");
			if (offSetManager.getId() != null) {

				try {
					Double amount = 0.0;
					for (OffsetPostingRecord offsetRecord : offSetManager
							.getPostingRecords()) {
						amount = amount + offsetRecord.getAmount();
					}
					offSetManager.setTotalPosted(amount);
					if (offSetManager.getAttachment().getId() != null) {
						if (!offSetManager.getAttachment().getAttachedFile()
								.isEmpty()) {
							fileUpload(offSetManager);
						}
					} else if (offSetManager.getAttachment().getId() == null) {
						if (!offSetManager.getAttachment().getAttachedFile()
								.isEmpty()) {
							fileUpload(offSetManager);
						} else {
							offSetManager.setAttachment(null);
						}
					}
					managerDao
							.updatePaymentPostingByOffSetManager(offSetManager);
					if (!toRemoveOffsetPostingRecordList.isEmpty()) {
						managerDao
								.deleteOffsetPostingRecords(toRemoveOffsetPostingRecordList);
					}
					session.setAttribute(
							Constants.SUCCESS_UPDATE,
							messageSource.getMessage(
									"arProductivity.updatedSuccessfully", null,
									Locale.ENGLISH).trim());
					if (offSetManager.isOffset()) {
						map.put("batchId", offSetManager.getPaymentBatch()
								.getId());

						return "redirect:/paymentoffsetmanager/add";
					}
				} catch (ArgusException e) {
					LOGGER.error(Constants.EXCEPTION, e);
				} finally {
					LOGGER.info("FINALLY ");

				}

			} else {
				try {

					Double amount = 0.0;
					for (OffsetPostingRecord offsetRecord : offSetManager
							.getPostingRecords()) {
						amount = amount + offsetRecord.getAmount();
					}
					offSetManager.setTotalPosted(amount);
					if (!offSetManager.getAttachment().getAttachedFile()
							.isEmpty()) {
						fileUpload(offSetManager);
					} else {
						offSetManager.setAttachment(null);
					}
					paymentBatch = batchDao.findById(offSetManager
							.getPaymentBatch().getId(), true);
					map.put("paymentBatch", paymentBatch);
					managerDao.addPaymentPostingByOffSetManager(offSetManager);
					session.setAttribute(
							Constants.SUCCESS_UPDATE,
							messageSource.getMessage(
									"arProductivity.addedSuccessfully", null,
									Locale.ENGLISH).trim());
					if (offSetManager.isOffset()) {
						map.put("batchId", offSetManager.getPaymentBatch()
								.getId());

						return "redirect:/paymentoffsetmanager/add";
					}

				} catch (ArgusException e) {
					LOGGER.info(Constants.EXCEPTION, e);
				}
			}

			return "redirect:/paymentoffsetmanager";

		} else {
			LOGGER.info(" :: ERROR :: ");

			map.put(Constants.PAYMENT_BATCH, paymentBatch);
			if (null != request & null != request.getParameter(Constants.ID)) {

				try {
					map.put("postingRecord", offSetManager.getPostingRecords());
					paymentBatch = batchDao.findById(offSetManager
							.getPaymentBatch().getId(), true);
					map.put("paymentBatch", paymentBatch);
				} catch (Exception e) {
					LOGGER.error(e.toString());
				}
				if (paymentBatch != null) {
					map.put(Constants.BATCH_ID, paymentBatch.getId());
				} else if (request.getParameter(Constants.BATCH_ID) != null) {
					map.put(Constants.BATCH_ID,
							request.getParameter(Constants.BATCH_ID));
				}
				model.addAttribute(Constants.MODE, Constants.EDIT);
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				map.put(Constants.BUTTON_NAME, Constants.UPDATE);
				map.put(Constants.OFFSET, offSetManager.getId());
				if (offSetManager.getPostingRecords() == null
						|| offSetManager.getPostingRecords().isEmpty()) {
					OffsetPostingRecord offsetPostingRecord = new OffsetPostingRecord();
					offsetPostingRecord.setOffSetManager(offSetManager);
					offSetManager.getPostingRecords().add(offsetPostingRecord);
				}
			} else {
				if (offSetManager.getPostingRecords() == null
						|| offSetManager.getPostingRecords().isEmpty()) {
					OffsetPostingRecord offsetPostingRecord = new OffsetPostingRecord();
					offSetManager.getPostingRecords().add(offsetPostingRecord);
				}
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
				map.put(Constants.BUTTON_NAME, Constants.ADD);
				map.put(Constants.BATCH_ID, offSetManager.getPaymentBatch()
						.getId());
			}
			map.put("toRemoveIds", toRemoveIds);
			return "paymentProductivity/paymentOffsetManager";
		}
	}

	/**
	 * function to use for flexigrid list
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/json", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public JsonDataWrapper<OffsetPostingJsonData> listAllProductivity(
			WebRequest request) {
		LOGGER.info("listAllProductivity ::in Report json method");

		int page = 1; // default 1 for page number in json wrapper
		int rp = 0;

		Map<String, String> orderClauses = new HashMap<String, String>();
		Map<String, String> whereClauses = new HashMap<String, String>();

		if (request != null) {

			if (request.getParameter(Constants.RECORD_PRE_PAGE) != null) {
				rp = Integer.parseInt(request
						.getParameter(Constants.RECORD_PRE_PAGE));
				try {
					orderClauses.put(Constants.LIMIT, "" + rp);
				} catch (Exception e) {
					LOGGER.debug("rp[Record pre Page] not coming or not an integer in request ");
				}
			}

			if (request.getParameter(Constants.PAGE) != null) {
				try {
					page = Integer.parseInt(request
							.getParameter(Constants.PAGE));
					orderClauses.put(Constants.OFFSET, "" + ((rp * page) - rp));
				} catch (Exception e) {
					LOGGER.debug("Exception during parsing");
				}
			}

			if (request.getParameter(Constants.SORT_ORDER) != null) {
				orderClauses.put(Constants.SORT_BY,
						request.getParameter(Constants.SORT_ORDER));
			}

			if (request.getParameter(Constants.SORT_NAME) != null) {
				orderClauses.put(Constants.ORDER_BY,
						request.getParameter(Constants.SORT_NAME));
			}

			if (request.getParameter(Constants.QTYPE) != null
					&& request.getParameter(Constants.QUERY) != null
					&& !request.getParameter(Constants.QUERY).isEmpty()) {
				whereClauses.put(request.getParameter(Constants.QTYPE),
						request.getParameter(Constants.QUERY));
			}
			if (request.getParameter(Constants.CHECK_NUMBER) != null
					& request.getParameter(Constants.CHECK_NUMBER).trim()
							.length() > 0) {
				String checkNumber = request
						.getParameter(Constants.CHECK_NUMBER);
				whereClauses.put(Constants.CHECK_NUMBER, checkNumber);
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

		} else {
			LOGGER.info("request object is coming null");
		}
		printReportCriteria = whereClauses;
		int totalRows = 0;
		try {
			totalRows = managerDao.totalRecordForList(whereClauses);
			if (totalRows > 0) {
				List<PaymentPostingByOffSetManager> rows = managerDao.findAll(
						orderClauses, whereClauses, false);
				List<OffsetPostingJsonData> djd = getJsonData(rows);
				JsonDataWrapper<OffsetPostingJsonData> jdw = new JsonDataWrapper<OffsetPostingJsonData>(
						page, totalRows, djd);
				return jdw;
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}

		return null;
	}

	/**
	 * this method set value in OffsetPostingJsonData list
	 *
	 * @param rows
	 * @return
	 */
	private List<OffsetPostingJsonData> getJsonData(
			List<PaymentPostingByOffSetManager> rows) {
		List<OffsetPostingJsonData> deptJsonData = new ArrayList<OffsetPostingJsonData>();
		if (rows != null && rows.size() > Constants.ZERO) {

			for (PaymentPostingByOffSetManager offsetPosting : rows) {
				OffsetPostingJsonData djd = new OffsetPostingJsonData();
				djd.setId(offsetPosting.getId());
				if (offsetPosting.getDateOfCheck() != null) {
					djd.setDateOfCheck(AkpmsUtil.akpmsDateFormat(
							offsetPosting.getDateOfCheck(),
							Constants.DATE_FORMAT));
				}
				if (offsetPosting.getCheckNumber() != null) {
					djd.setCheckNumber(offsetPosting.getCheckNumber());

				}
				if (offsetPosting.getFcnOrAR() != null) {
					djd.setFcnOrAR(offsetPosting.getFcnOrAR());

				}
				djd.setDoctor(offsetPosting.getPaymentBatch().getDoctor()
						.getName());
				djd.setBatchId(offsetPosting.getPaymentBatch().getId());
				djd.setCreatedBy(offsetPosting.getCreatedBy().getFirstName()
						+ " " + offsetPosting.getCreatedBy().getLastName());
				djd.setInsuranceName(offsetPosting.getPaymentBatch()
						.getInsurance().getName());
				djd.setPatientName(offsetPosting.getPatientName());
				djd.setAccountNumber(offsetPosting.getAccountNumber());
				djd.setCreatedOn(AkpmsUtil.akpmsDateFormat(
						offsetPosting.getCreatedOn(), Constants.DATE_FORMAT));

				djd.setTotalPosted(offsetPosting.getTotalPosted());

				if (offsetPosting.getTime() != null) {
					djd.setTime(offsetPosting.getTime());
				}
				djd.setPostingRecords(managerDao.getPostedRecord(offsetPosting
						.getId()));

				deptJsonData.add(djd);
			}

		}

		return deptJsonData;
	}

	/**
	 * This function just load jsp "paymentProductivity/offsetPostedList", the
	 * list will load using flexigrid list
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String offsetPostedList(Map<String, Object> map, WebRequest request,
			HttpSession session, Model model) {
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success", success);
		}
		session.removeAttribute(Constants.SUCCESS_UPDATE);

		try {

			map.put("postedBy", userDao.getOffsetUser());

		} catch (ArgusException ar) {
			LOGGER.error(Constants.EXCEPTION, ar);
		}
		return "paymentProductivity/offsetPostedList";
	}

	@RequestMapping(value = "/getRecord", method = RequestMethod.GET)
	@ResponseBody
	public List<OffsetPostingRecordJsonData> getRecord(@RequestParam long id) {
		LOGGER.info("in getRecord method");
		LOGGER.info("id = " + id);
		List<OffsetPostingRecordJsonData> recordPosted = new ArrayList<OffsetPostingRecordJsonData>();

		try {

			recordPosted = managerDao.getPostedRecord(id);
			if (recordPosted.size() > Constants.ZERO) {
				return recordPosted;
			}

		} catch (Exception e) {
			LOGGER.info(Constants.EXCEPTION + " :: " + e.getMessage());
		}

		return recordPosted;
	}

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String offsetPostedReportLoad(WebRequest request, Model model,
			Map<String, Object> map) {
		try {

			map.put("postedBy", userDao.getOffsetUser());

		} catch (ArgusException ar) {
			LOGGER.error(Constants.EXCEPTION, ar);
		}
		return "offsetPostedWorkflowReport";
	}

	public void fileUpload(PaymentPostingByOffSetManager offsetManager) {

		try {
			Files newFile = offsetManager.getAttachment();
			if (newFile.getAttachedFile().getSize() != Constants.ZERO) {

				MultipartFile file = newFile.getAttachedFile();
				if (null != file && file.getSize() > Constants.ZERO) {

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
							"attachments.storage.space.offsetPostingByManager",
							null, Locale.ENGLISH).trim();
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
					if (offsetManager.getId() != null) {
						managerDao.updateAttachement(newFile);
					} else {
						managerDao.saveAttachement(newFile);
					}
					/* set message here */
				} else {
					LOGGER.info("there is no attachment coming in file object");
				}
			} else {

				LOGGER.info("Attached file Object is coming null");
			}

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
	}

	@RequestMapping(value = "/fileDownload", method = RequestMethod.GET)
	public void downloadAttachment(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("id") Long id,
			HttpSession session) {
		LOGGER.info("In download method. Id = " + id);
		try {
			if (id != null) {
				Files file = processManualDao.getAttachedFile(id);

				String realPath = messageSource.getMessage(
						"attachments.storage.space.offsetPostingByManager",
						null, Locale.ENGLISH).trim();

				File systemFile = new File(realPath, file.getSystemName());
				InputStream is = new FileInputStream(systemFile);

				response.setHeader("Content-Disposition",
						"attachment; filename=" + file.getName());
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
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

			if (paymentBatch != null && paymentBatch.getId() != null) {
				if (!(paymentBatch.getPaymentType().getId().longValue() == Constants.PAYMENT_TYPE_OFFSET
						.longValue())) {
					LOGGER.info("Offline Payment Batch");
					obj = new Object[Constants.TWO];
					obj[Constants.ZERO] = Constants.ERR;
					obj[Constants.ONE] = messageSource.getMessage(
							"batch.online", null, Locale.ENGLISH).trim();
					return obj;
				} else {
					PaymentBatchJsonData paymentBatchJsonData = new PaymentBatchJsonData();
					paymentBatchJsonData.setId(paymentBatch.getId());
					paymentBatchJsonData.setDoctor(paymentBatch.getDoctor()
							.getName());
					paymentBatchJsonData.setInsurance(paymentBatch
							.getInsurance().getName());
					if (paymentBatch.getDepositAmount() != null
							&& paymentBatch.getDepositAmount() != Constants.ZERO) {
						paymentBatchJsonData.setDepositAmount(paymentBatch
								.getDepositAmount());
					} else {
						paymentBatchJsonData.setDepositAmount(paymentBatch
								.getNdba());
					}
					obj = new Object[Constants.TWO];
					obj[Constants.ZERO] = paymentBatchJsonData;
					return obj;
				}
			} else {
				obj = new Object[Constants.TWO];
				obj[Constants.ZERO] = Constants.ERR;
				obj[Constants.ONE] = messageSource.getMessage(
						"batch.notAvailable", null, Locale.ENGLISH).trim();
				return obj;
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
	}

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public void printReport(HttpServletResponse response, HttpSession session) {

		try {
			String xmlString = null;
			List<PaymentPostingByOffSetManager> rows = managerDao.findAll(null,
					printReportCriteria, true);
			if (rows != null && !rows.isEmpty()) {
				xmlString = getXmlData(rows);
				// adding XML header
				xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
						+ xmlString;
			} else {
				String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
				xmlString = xml + "<empty>No Record found.</empty>";
			}
			// LOGGER.info(xmlString);
			// Generate PDF
			FopXmlToPdfGenrator
					.generatePDFReport(
							response,
							session,
							"PaymentProductivityOffsetPostingStylesheet_ListView_FO.xsl",
							xmlString,messageSource
							.getMessage("report.paymentProductivity.name",null, Locale.ENGLISH));

		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
	}

	public String getXmlData(
			List<PaymentPostingByOffSetManager> paymentPostingByOffSetManagerList) {
		XStream xstream = new XStream(new DomDriver());
		xstream.setMode(XStream.NO_REFERENCES);// XPATH_ABSOLUTE_REFERENCES);
		xstream.processAnnotations(PaymentPostingByOffSetManager.class);
		xstream.processAnnotations(OffsetPostingRecord.class);
		xstream.processAnnotations(PaymentBatch.class);
		xstream.processAnnotations(User.class);
		xstream.processAnnotations(Doctor.class);
		xstream.registerConverter(new UserXstreamConverter());
		xstream.registerConverter(new XstreamDateConverter());
		return xstream.toXML(paymentPostingByOffSetManagerList);
		// return xml;
	}

}
