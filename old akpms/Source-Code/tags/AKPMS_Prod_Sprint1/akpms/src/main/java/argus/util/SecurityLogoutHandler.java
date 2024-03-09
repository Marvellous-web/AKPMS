package argus.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class SecurityLogoutHandler implements LogoutHandler {

	private static final Log LOGGER = LogFactory.getLog(SecurityLogoutHandler.class);

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) {
		LOGGER.info("in logout Handler");
		try
		{
		if(auth.isAuthenticated())
		{
			auth.setAuthenticated(false);
			request.getSession(false).invalidate();
			LOGGER.info("logout seccessful");
		}
		}catch(Exception e)
		{
			LOGGER.error(Constants.EXCEPTION, e);
		}
	}

}
