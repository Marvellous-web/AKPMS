package argus.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "qa_worksheet_doctor")
public class QAWorksheetDoctor extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 2236128987870371938L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinColumns(@JoinColumn(name = "qaworksheet_id", referencedColumnName = "id", unique = false, nullable = false))
	private QAWorksheet qaWorksheet;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinColumns(@JoinColumn(name = "doctor_id", referencedColumnName = "id", unique = false, nullable = false))
	private Doctor doctor;

	@Column(name = "percentage_value")
	private Float percentageValue;

	@Column(length = 600, columnDefinition = "Text")
	private String remarks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public QAWorksheet getQaWorksheet() {
		return qaWorksheet;
	}

	public void setQaWorksheet(QAWorksheet qaWorksheet) {
		this.qaWorksheet = qaWorksheet;
	}

	public Float getPercentageValue() {
		return percentageValue;
	}

	public void setPercentageValue(Float percentageValue) {
		this.percentageValue = percentageValue;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the doctor
	 */
	public Doctor getDoctor() {
		return doctor;
	}

	/**
	 * @param doctor
	 *            the doctor to set
	 */
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

}
