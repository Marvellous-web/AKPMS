package argus.util;

import java.util.Date;

public class ArProductivityJsonData {

	private Long id;

	private String patientAccNo;

	private String patientName;

	private Date dos;

	private String cpt;

	private String insurance;

	private String doctor;

	private String dataBas;

	private Float balanceAmt;

	private int source = 0;

	private int status = 0;

	private int workFlow = 0;

	private String tlRemark;

	private String remark;

	private String workFlowName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPatientAccNo() {
		return patientAccNo;
	}

	public void setPatientAccNo(String patientAccNo) {
		this.patientAccNo = patientAccNo;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public Date getDos() {
		return dos;
	}

	public void setDos(Date dos) {
		this.dos = dos;
	}

	public String getCpt() {
		return cpt;
	}

	public void setCpt(String cpt) {
		this.cpt = cpt;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getDataBas() {
		return dataBas;
	}

	public void setDataBas(String dataBas) {
		this.dataBas = dataBas;
	}

	public Float getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(Float balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(int workFlow) {
		this.workFlow = workFlow;
	}

	public String getTlRemark() {
		return tlRemark;
	}

	public void setTlRemark(String tlRemark) {
		this.tlRemark = tlRemark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWorkFlowName() {
		return workFlowName;
	}

	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	private String sourceName;
}
