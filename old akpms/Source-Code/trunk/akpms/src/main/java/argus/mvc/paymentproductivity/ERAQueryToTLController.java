/**
 *
 */
package argus.mvc.paymentproductivity;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

import argus.domain.paymentproductivity.PaymentProdQueryToTL;
import argus.domain.paymentproductivity.PaymentProductivity;
import argus.exception.ArgusException;
import argus.mvc.productivity.helper.ArProductivityHelper;
import argus.repo.paymentproductivity.PaymentProdQueryToTLDao;
import argus.repo.paymentproductivity.PaymentProductivityDao;
import argus.util.Constants;
import argus.validator.EraQueryToTLValidator;

/**
 * @author vishal.joshi
 *
 */
@Controller
@RequestMapping(value = "/paymentprodquerytotl")
@SessionAttributes({ Constants.PAYMENTPRODQUERYTOTL })
public class ERAQueryToTLController {

	private static final Logger LOGGER = Logger
			.getLogger(ERAQueryToTLController.class);

	@Autowired
	private EraQueryToTLValidator queryToTLValidator;

	@Autowired
	private PaymentProdQueryToTLDao prodQueryToTLDao;

	@Autowired
	private PaymentProductivityDao paymentProductivityDao;

	@Autowired
	private MessageSource messageSource;

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
	public String productivityQueryToTLLoad(Model model, WebRequest request,
			Map<String, Object> map, HttpSession session,
			@RequestParam(required = false) String successUpdate) {
		LOGGER.info("in [productivityQueryToTLLoad] method");
		PaymentProdQueryToTL prodQueryToTL = null;
		String success = (String) session
				.getAttribute(Constants.SUCCESS_UPDATE);
		if (success != null) {
			map.put("success", success);
		}
		session.removeAttribute(Constants.SUCCESS_UPDATE);
		try {
			if (request.getParameter(Constants.TICKET_NUMBER) != null) {
				PaymentProductivity paymentProductivity = paymentProductivityDao
						.findByTicketNo(Long.valueOf(request
								.getParameter(Constants.TICKET_NUMBER)));
				map.put("paymentProductivity", paymentProductivity);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		if (null != request & null != request.getParameter(Constants.ID)) {
			prodQueryToTL = null;
			try {

				prodQueryToTL = prodQueryToTLDao.findById(Long
						.parseLong(request.getParameter(Constants.ID)));
				map.put("paymentProductivity",
						prodQueryToTL.getPaymentProductivity());
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
			model.addAttribute(Constants.PAYMENTPRODQUERYTOTL, prodQueryToTL);
			model.addAttribute(Constants.MODE, Constants.EDIT);
			map.put(Constants.OPERATION_TYPE, Constants.EDIT);
		} else if (null != request & null != request.getParameter("prodId")) {

			try {
				PaymentProductivity paymentProductivity = paymentProductivityDao
						.findById(Long.valueOf(request.getParameter("prodId")));
				map.put("paymentProductivity", paymentProductivity);

				prodQueryToTL = prodQueryToTLDao.findQueryToTLByProdId(Long
						.parseLong(request.getParameter("prodId")));

			} catch (Exception e) {
				LOGGER.info(e.toString());
				prodQueryToTL = new PaymentProdQueryToTL();
				model.addAttribute(Constants.PAYMENTPRODQUERYTOTL,
						prodQueryToTL);
				model.addAttribute(Constants.MODE, Constants.EDIT);
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
				return "paymentProductivity/paymentProdQueryToTL";
			}
			model.addAttribute(Constants.PAYMENTPRODQUERYTOTL, prodQueryToTL);
			model.addAttribute(Constants.MODE, Constants.EDIT);
			map.put(Constants.OPERATION_TYPE, Constants.EDIT);
		} else {
			model.addAttribute(Constants.PAYMENTPRODQUERYTOTL,
					new PaymentProdQueryToTL());
			model.addAttribute(Constants.MODE, Constants.ADD);
			map.put(Constants.OPERATION_TYPE, Constants.ADD);
		}

		return "paymentProductivity/paymentProdQueryToTL";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveProductivityQueryToTL(
			@Valid @ModelAttribute(Constants.PAYMENTPRODQUERYTOTL) PaymentProdQueryToTL prodQueryToTL,
			BindingResult result, Model model, WebRequest request,
			Map<String, Object> map, HttpSession session) {
		LOGGER.info("in [saveProductivityQueryToTL] method : ");
		PaymentProductivity paymentProductivity = new PaymentProductivity();
		Object[] ticketNumber = new Object[1];
		if (prodQueryToTL.getPaymentProductivity() == null
				|| prodQueryToTL.getPaymentProductivity().getId() == null) {
			try {
				paymentProductivity = paymentProductivityDao
						.findByTicketNo(Long.valueOf(prodQueryToTL
								.getTicketNumber()));
				prodQueryToTL.setPaymentProductivity(paymentProductivity);
			} catch (NumberFormatException e) {
				LOGGER.info(Constants.EXCEPTION + ": NumberFormatException ");
			} catch (NoResultException e) {
				LOGGER.info(Constants.EXCEPTION + ": NoResultException ");
			} catch (ArgusException e) {
				LOGGER.info(Constants.EXCEPTION + ": ArgusException ");
			}
		} else {
			map.put("paymentProductivity",
					prodQueryToTL.getPaymentProductivity());
		}
		queryToTLValidator.validate(prodQueryToTL, result);
		if (!result.hasErrors()) {
			LOGGER.info(" :: NO ERROR :: ");
			if (prodQueryToTL.getId() != null) {
				try {
					prodQueryToTLDao.updatePaymentProdQueryToTL(prodQueryToTL);
					ticketNumber[0] = prodQueryToTL.getTicketNumber();
					session.setAttribute(
							Constants.SUCCESS_UPDATE,
							messageSource
									.getMessage(
									"record.updatedSuccessfully",
											ticketNumber, Locale.ENGLISH)
									.trim());
				} catch (ArgusException e) {
					LOGGER.error(Constants.EXCEPTION, e);
				} finally {
					LOGGER.info("FINALLY ");
				}
			} else {
				try {
					prodQueryToTLDao.addPaymentProdQueryToTL(prodQueryToTL);

					paymentProductivity = paymentProductivityDao
							.findById(prodQueryToTL.getPaymentProductivity()
									.getId());
					map.put("paymentProductivity", paymentProductivity);
//					paymentProductivity.setPaymentProdQueryToTL(prodQueryToTL);
//					paymentProductivityDao
//							.updatePaymentProductivity(paymentProductivity);
					ticketNumber[0] = prodQueryToTL.getTicketNumber();
					session.setAttribute(
							Constants.SUCCESS_UPDATE,
							messageSource
									.getMessage(
									"record.addedSuccessfully",
											ticketNumber, Locale.ENGLISH)
									.trim());
				} catch (ArgusException e) {
					LOGGER.info(Constants.EXCEPTION, e);
				}
			}

			return "redirect:/paymentproductivitynonera";

		} else {
			LOGGER.info(" :: ERROR :: ");
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError objectError : list) {
				LOGGER.info(" :: ERROR :: " + objectError.getDefaultMessage());
			}

			if (prodQueryToTL != null & prodQueryToTL.getId() != null) {
				LOGGER.info(" :: prodQueryToTL IS NOT NULL :: ");
				map.put(Constants.OPERATION_TYPE, Constants.EDIT);
			} else {
				LOGGER.info(" :: prodQueryToTL IS NULL :: ");
				map.put(Constants.OPERATION_TYPE, Constants.ADD);
			}

			return "paymentProductivity/paymentProdQueryToTL";
		}
	}
}
