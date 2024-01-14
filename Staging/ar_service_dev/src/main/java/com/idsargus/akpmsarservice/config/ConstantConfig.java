package com.idsargus.akpmsarservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "app.constant")
@PropertySource("classpath:constants.properties")
@Getter
@Setter
public class ConstantConfig {

	Integer page;

	Integer size;

	String orderBy;

	String direction;

}
