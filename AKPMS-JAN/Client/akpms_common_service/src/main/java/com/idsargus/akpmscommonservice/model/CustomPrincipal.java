package com.idsargus.akpmscommonservice.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomPrincipal implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private Integer id;
	private String role;
	private List<String> permission;
	private List<Integer> department;
	private String scope;
}