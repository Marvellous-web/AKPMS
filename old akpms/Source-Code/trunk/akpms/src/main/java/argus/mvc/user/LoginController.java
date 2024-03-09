package argus.mvc.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import argus.domain.Permission;
import argus.domain.Role;
import argus.domain.User;
import argus.repo.user.UserDao;
import argus.util.Constants;

//@Service
public class LoginController implements AuthenticationProvider {

	private static final Logger LOGGER = Logger.getLogger(LoginController.class);

	@Autowired
	private UserDao userDao;

	@SuppressWarnings("deprecation")
	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		LOGGER.info("in authentication method");
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

		String userName = auth.getName();
		String password = auth.getCredentials().toString();
		PasswordEncoder encoder = new Md5PasswordEncoder();
		User user = null;

		try {
			user = userDao.findByEmail(userName, true);
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
		if (user == null) {
			LOGGER.info("Username is wrong");
			throw new UsernameNotFoundException(
					"Either the username or the password is incorrect. Please try again.");
		} else {
			if (!encoder.isPasswordValid(user.getPassword(), password,
					Constants.SALT)) {
				LOGGER.info("Password is wrong");
				throw new BadCredentialsException(
						"Either the username or the password is incorrect. Please try again.");
			} else if (!user.isStatus()) {
				throw new BadCredentialsException(
						"Your account is no longer active, please contact the administrator.");
			} else if (user.isDeleted()) {
				throw new BadCredentialsException(
						"Your account has been deleted, please contact the administrator.");
			} else {
				Role role = user.getRole();
				if (null != role) {
					grantedAuthorities.add(new GrantedAuthorityImpl(role
							.getName()));
				}
				List<Permission> pemissionList = user.getPermissions();
				for (Permission permission : pemissionList) {
					grantedAuthorities.add(new GrantedAuthorityImpl(permission
							.getId()));
				}
				LOGGER.info("Login sucessfull");
				String lastLogin = null;
				try {
					lastLogin = userDao.getLastLoginDetails(user.getId());
				} catch (Exception e) {
					LOGGER.error(e.toString());
				}
				LOGGER.info("Last Login = " + lastLogin);
				if (lastLogin != null) {
					user.setLastLogin(lastLogin);
				} else {
					user.setLastLogin("This is your first login");
				}

				try {
					userDao.insertLoginDetails(user.getId());
				} catch (Exception e) {
					// TODO: handle exception
					LOGGER.error(e.getMessage(), e);
				}

				return new UsernamePasswordAuthenticationToken(user, password,
						grantedAuthorities);
			}
		}

	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
