package com.idsargus.akpmsadminservice.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCriteria {

	// Field Name
	private String f;

	// Condition
	// "=", "!=", "<=", ">=", "in", "not in", "<", ">"
	private String c;

	// Value
	private String v;
}
