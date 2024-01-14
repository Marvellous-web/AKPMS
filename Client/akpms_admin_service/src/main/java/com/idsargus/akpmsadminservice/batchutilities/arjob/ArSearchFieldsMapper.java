package com.idsargus.akpmsadminservice.batchutilities.arjob;

import java.sql.Date;

import org.apache.commons.lang3.StringEscapeUtils;

import lombok.Setter;
import lombok.ToString;

@ToString
public class ArSearchFieldsMapper {

	private int arid;

	private String patientname;

	private String remark;

	private String source;

	private String statuscode;

	private float balance;

	private String cpt;

	private String dos;

	private Date followupdate;

	private String patientaccountnumber;

	public void setArid(int arid) {
		this.arid = arid;
	}

	public void setPatientname(String patientname) {
		this.patientname = StringEscapeUtils.escapeCsv(patientname);
	}

	public void setRemark(String remark) {
		this.remark = StringEscapeUtils.escapeCsv(remark);
	}

	public void setSource(String source) {
		this.source = StringEscapeUtils.escapeCsv(source);
	}

	public void setStatuscode(String statuscode) {
		this.statuscode = StringEscapeUtils.escapeCsv(statuscode);
	}

	public int getArid() {
		return arid;
	}

	public String getPatientname() {
		return StringEscapeUtils.escapeCsv(patientname);
	}

	public String getRemark() {
		return StringEscapeUtils.escapeCsv(remark);
	}

	public String getSource() {
		return StringEscapeUtils.escapeCsv(source);
	}

	public String getStatuscode() {
		return StringEscapeUtils.escapeCsv(statuscode);
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public String getCpt() {
		return StringEscapeUtils.escapeCsv(cpt);
	}

	public void setCpt(String cpt) {
		this.cpt = cpt;
	}

	public String getDos() {
		return StringEscapeUtils.escapeCsv(dos);
	}

	public void setDos(String dos) {
		this.dos = dos;
	}

	public Date getFollowupdate() {
		return followupdate;
	}

	public void setFollowupdate(Date followupdate) {
		this.followupdate = followupdate;
	}

	public String getPatientaccountnumber() {
		return StringEscapeUtils.escapeCsv(patientaccountnumber);
	}

	public void setPatientaccountnumber(String patientaccountnumber) {
		this.patientaccountnumber = patientaccountnumber;
	}

}
