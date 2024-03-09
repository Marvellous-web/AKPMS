package com.idsargus.akpmsauthservice.config;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.idsargus.akpmsauthservice.model.CustomUser;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {
	private static final String EMAIL = "email";
	private static final String ROLE = "role";
	private static final String DEPARTMENT = "department";
	private static final String PERMISSION = "permission";
	private static final String ID = "id";
	private static final String FIRSTNAME = "firstName";
	private static final String LASTNAME = "lastName";
	private static final String DASHBOARD = "dashboard";
	private static final String MENU = "menu";

	private static final String ADDRESS = "address";
	private static final String CONTACT = "contact";

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();

		Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());

		info.put(ID, customUser.getId());
		info.put(EMAIL, customUser.getEmail());
		info.put(ROLE, customUser.getRole());
		info.put(FIRSTNAME, customUser.getFirstName());
		info.put(LASTNAME, customUser.getLastName());
		info.put(ADDRESS, customUser.getAddress());
		info.put(CONTACT, customUser.getContact());
		info.put(DEPARTMENT, convert(customUser.getDepartments()));
		info.put(PERMISSION, convert(customUser.getPermissions()));
	//	if(!customUser.getRole().equals("role_admin")) {
		info.put(DASHBOARD, customUser.getDashboardList());
		info.put(MENU, customUser.getUserManu());
		//}
		DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
		customAccessToken.setAdditionalInformation(info);

		return super.enhance(customAccessToken, authentication);
	}

	public Set<String> convert(Set<?> elememts) {
		Set<String> s = new HashSet<>();

		for (Object object : elememts) {
			s.add(object.toString());
		}

		return s;
	}
}