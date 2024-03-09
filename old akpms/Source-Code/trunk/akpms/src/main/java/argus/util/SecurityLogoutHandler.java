package argus.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class SecurityLogoutHandler implements LogoutHandler {

	private static final Logger LOGGER = Logger
			.getLogger(SecurityLogoutHandler.class);

	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication auth) {
		LOGGER.info("in logout Handler");																																																																																																																																																																																																																																					
		try {
			Cookie ck = new Cookie("lt", "true");
			ck.setMaxAge(0);
			response.addCookie(ck);
			if (auth.isAuthenticated()) {
				auth.setAuthenticated(false);
				request.getSession(false).invalidate();
				LOGGER.info("logout seccessful");
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}

}
