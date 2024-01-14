package com.idsargus.akpmsarservice.config;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.idsargus.akpmscommonservice.model.CustomPrincipal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtConverter extends DefaultAccessTokenConverter implements JwtAccessTokenConverterConfigurer {

	private static final String ID = "id";
	private static final String EMAIL = "email";
	private static final String ROLE = "role";
	private static final String NAME = "name";
	private static final String DEPARTMENT = "department";
	private static final String PERMISSION = "permission";

	@Override
	public void configure(JwtAccessTokenConverter converter) {
		converter.setAccessTokenConverter(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
		OAuth2Authentication auth = super.extractAuthentication(map);
		CustomPrincipal cPrincipal = new CustomPrincipal();
		log.debug(cPrincipal.toString());

		if (map.get(ID) != null)
			cPrincipal.setId((Integer) map.get(ID));

		if (map.get(EMAIL) != null)
			cPrincipal.setEmail((String) map.get(EMAIL));

		if (map.get(NAME) != null)
			cPrincipal.setName((String) map.get(NAME));

		if (map.get(ROLE) != null)
			cPrincipal.setName((String) map.get(ROLE));

		if (map.containsKey(DEPARTMENT)) {
			cPrincipal.setDepartment((ArrayList<Integer>) map.get(DEPARTMENT));
		}

		if (map.containsKey(PERMISSION)) {
			cPrincipal.setPermission((ArrayList<String>) map.get(PERMISSION));
		}

		auth.setDetails(cPrincipal);
		return auth;
	}
}
