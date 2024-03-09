package argus.interceptor.qamanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LayoutExecAndWaitInterceptor extends HandlerInterceptorAdapter {

	private static Logger LOGGER = Logger
			.getLogger(LayoutExecAndWaitInterceptor.class);
	private boolean flag = true;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		LOGGER.info("Request URI::" + request.getRequestURI().toString());
		// if returned false, we need to make sure 'response' is sent

		String orderBy = "";
		if (request.getParameter("orderby") != null) {
			orderBy = request.getParameter("orderby");
		}

		String viewName = "lodingView";
		ModelAndView mav = new ModelAndView(viewName);
		ModelMap model = (ModelMap) mav.getModel();

		String currentURI = request.getRequestURI().toString();

		if (flag
				&& currentURI.equalsIgnoreCase(request.getContextPath()
						+ "/qamanager/continueintercept/")) {
			model.addAttribute("continueUrl", "/qamanager/continue?id="
					+ request.getParameter("id") + "&orderby=" + orderBy);
			flag = false;
			throw new ModelAndViewDefiningException(mav);
		} else if (flag
				&& currentURI.equalsIgnoreCase(request.getContextPath()
						+ "/qaworksheetlayout/payment/")) {
			model.addAttribute("continueUrl", "/qamanager/continue?id="
					+ request.getParameter("qaworksheetid") + "&orderby="
					+ orderBy);
			flag = false;
			throw new ModelAndViewDefiningException(mav);
		} else if (flag
				&& currentURI.equalsIgnoreCase(request.getContextPath()
						+ "/qaworksheetlayout/coding/")) {
			model.addAttribute("continueUrl", "/qamanager/continue?id="
					+ request.getParameter("qaworksheetid") + "&orderby="
					+ orderBy);
			flag = false;
			throw new ModelAndViewDefiningException(mav);
		} else if (flag
				&& currentURI.equalsIgnoreCase(request.getContextPath()
						+ "/qaworksheetlayout/ar/")) {
			model.addAttribute("continueUrl", "/qamanager/continue?id="
					+ request.getParameter("qaworksheetid") + "&orderby="
					+ orderBy);
			flag = false;
			throw new ModelAndViewDefiningException(mav);
		} else if (flag
				&& currentURI.equalsIgnoreCase(request.getContextPath()
						+ "/qaworksheetlayout/account/")) {
			model.addAttribute("continueUrl", "/qamanager/continue?id="
					+ request.getParameter("qaworksheetid") + "&orderby="
					+ orderBy);
			flag = false;
			throw new ModelAndViewDefiningException(mav);
		}

		return true;
	}

	/**
	 * This implementation is empty.
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		flag = true;
	}
}
