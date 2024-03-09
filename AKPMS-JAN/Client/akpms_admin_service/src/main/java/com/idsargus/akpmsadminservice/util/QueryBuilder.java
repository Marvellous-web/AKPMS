package com.idsargus.akpmsadminservice.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface QueryBuilder {

	/*
	 * static StringBuilder prepareWhereClause(List<SearchCriteria> searchCriterias)
	 * { StringBuilder sb = new StringBuilder();
	 * 
	 * sb.append(" WHERE 1 = 1 ");
	 * 
	 * if (searchCriterias != null) {
	 * 
	 * for (SearchCriteria sc : searchCriterias) { if
	 * (sc.getF().equalsIgnoreCase(KEYWORD)) { sb.append(" AND (")
	 * .append(createClause("u.patientAccountNumber", sc.getC(), sc.getV(),
	 * SCDataType.LIKE)); sb.append(" OR ").append(createClause("u.patientName",
	 * sc.getC(), sc.getV(), SCDataType.LIKE)); sb.append(")"); } else if
	 * (sc.getF().equalsIgnoreCase(WORKFLOWID)) {
	 * sb.append(" AND ").append(createClause("u.arWorkflowIds", sc.getC(),
	 * sc.getV(), SCDataType.MEMBER)); } else if
	 * (sc.getF().equalsIgnoreCase(TEAMID)) {
	 * sb.append(" AND ").append(createClause("u.team.id", sc.getC(), sc.getV(),
	 * SCDataType.NUMERIC)); } else if (sc.getF().equalsIgnoreCase(SUBSTATUS)) {
	 * sb.append(" AND ").append(createClause("u.subStatus", sc.getC(), sc.getV(),
	 * SCDataType.NUMERIC)); } else if (sc.getF().equalsIgnoreCase(INSURANCEID)) {
	 * sb.append(" AND ").append(createClause("u.insurance.id", sc.getC(),
	 * sc.getV(), SCDataType.NUMERIC)); } else if
	 * (sc.getF().equalsIgnoreCase(DOCTORID)) {
	 * sb.append(" AND ").append(createClause("u.doctor.id", sc.getC(), sc.getV(),
	 * SCDataType.NUMERIC)); } else if (sc.getF().equalsIgnoreCase(STATUSCODE)) {
	 * sb.append(" AND ").append(createClause("u.statusCode", sc.getC(), sc.getV(),
	 * SCDataType.STRING)); } else if (sc.getF().equalsIgnoreCase(SOURCE)) {
	 * sb.append(" AND ").append(createClause("u.source", sc.getC(), sc.getV(),
	 * SCDataType.STRING)); } else if (sc.getF().equalsIgnoreCase(CREATEDDATEFROM))
	 * { sb.append(" AND ").append(createClause("u.createdOn", sc.getC(), sc.getV(),
	 * SCDataType.DATE)); } else if (sc.getF().equalsIgnoreCase(CREATEDDATETO)) {
	 * sb.append(" AND ").append(createClause("u.createdOn", sc.getC(), sc.getV(),
	 * SCDataType.DATE)); } else if (sc.getF().equalsIgnoreCase(FOLLOWUPDATEFROM)) {
	 * sb.append(" AND ").append(createClause("u.followUpDate", sc.getC(),
	 * sc.getV(), SCDataType.DATE)); } else if
	 * (sc.getF().equalsIgnoreCase(FOLLOWUPDATETO)) {
	 * sb.append(" AND ").append(createClause("u.followUpDate", sc.getC(),
	 * sc.getV(), SCDataType.DATE)); } else if
	 * (sc.getF().equalsIgnoreCase(CREATEDBY)) {
	 * sb.append(" AND ").append(createClause("u.createdBy", sc.getC(), sc.getV(),
	 * SCDataType.STRING)); } } }
	 * 
	 * return sb; }
	 * 
	 * static StringBuilder createClause(String fieldName, String condition, String
	 * value, SCDataType type) { StringBuilder sb = new StringBuilder();
	 * 
	 * if (type == SCDataType.STRING) {
	 * sb.append("LOWER(").append(fieldName).append(") ").append(condition).
	 * append(" "); } else if (type == SCDataType.LIKE) {
	 * sb.append("LOWER(").append(fieldName).append(") ").append("LIKE").append(" "
	 * ); } else { sb.append(fieldName).append(" ").append(condition).append(" "); }
	 * 
	 * if (type == SCDataType.STRING) {
	 * sb.append("'").append(value.toLowerCase()).append("'"); } else if (type ==
	 * SCDataType.LIKE) { sb.append("'%").append(value.toLowerCase()).append("%'");
	 * } else if (type == SCDataType.NUMERIC) { sb.append(value); } else if (type ==
	 * SCDataType.IN) { sb.append(" (").append(value).append(") ");
	 * 
	 * // String[] ids = value.split(","); // if (ids.length > 0) { //
	 * sb.append(" AND ("); // int i = 0; // for (String id : ids) { // sb.append(id
	 * + " MEMBER OF pmtbatch.phDoctor "); // i++; // if (i < ids.length) { //
	 * sb.append(" OR "); // } // } // sb.append(" ) "); // } }
	 * 
	 * return sb; }
	 * 
	 * static StringBuilder prepareOrderByClause(StringBuilder sb, String orderBy,
	 * String direction) {
	 * 
	 * sb.append(" ORDER BY ").append(orderBy).append(" ").append(direction);
	 * 
	 * return sb; }
	 * 
	 * static List<SearchCriteria> prepareSearchCriteria(String filter) throws
	 * JsonParseException, JsonMappingException, IOException {
	 * 
	 * List<SearchCriteria> searchCriterias = new ArrayList<>();
	 * 
	 * if (filter != null) { Map<String, Object> filterMap = new
	 * ObjectMapper().readValue(filter, Map.class);
	 * 
	 * if (filterMap != null && !filterMap.isEmpty()) { for (Map.Entry<String,
	 * Object> entry : filterMap.entrySet()) {
	 * 
	 * String value = null;
	 * 
	 * if (entry.getValue() instanceof String) { if
	 * (!entry.getValue().toString().isEmpty()) { value =
	 * entry.getValue().toString(); } } else if (entry.getValue() instanceof
	 * Integer) { if (Integer.parseInt(entry.getValue().toString()) > 0) { value =
	 * entry.getValue().toString(); } }
	 * 
	 * if (entry.getKey() != null && value != null) { SearchCriteria searchCriteria
	 * = new SearchCriteria();
	 * 
	 * searchCriteria.setF(entry.getKey());
	 * 
	 * if (entry.getKey().endsWith("From")) { searchCriteria.setC(">="); } else if
	 * (entry.getKey().endsWith("To")) { searchCriteria.setC("<="); } else if
	 * (entry.getKey().endsWith("Not")) { searchCriteria.setC("!="); } else {
	 * searchCriteria.setC("="); }
	 * 
	 * searchCriteria.setV(entry.getValue().toString());
	 * 
	 * searchCriterias.add(searchCriteria); } } } }
	 * 
	 * return searchCriterias; }
	 */
}
