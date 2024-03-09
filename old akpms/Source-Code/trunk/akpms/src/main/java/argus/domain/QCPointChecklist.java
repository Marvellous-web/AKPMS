package argus.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "qc_point_checklist")
@EntityListeners(EntityListener.class)
public class QCPointChecklist extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @Column(name = "check_point")
	// private Short checkPoint;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinColumn(name = "qc_point_id", nullable = false, unique = false)
	private QcPoint qcPoint;

	// @ManyToOne(fetch = FetchType.LAZY, targetEntity = QCPointChecklist.class,
	// cascade = {CascadeType.REMOVE, CascadeType.REFRESH })
	@ManyToOne(cascade = { CascadeType.REMOVE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "qa_worksheet_patient_info_id")
	private QAWorksheetPatientInfo qaWorksheetPatientInfo;

	@ManyToOne(cascade = {CascadeType.REFRESH }, targetEntity = QAProductivitySampling.class)
	@JoinColumn(name = "qa_productivity_sampling_id", referencedColumnName = "id",  nullable = true)
	private QAProductivitySampling qaProductivitySampling;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.REMOVE })
	@JoinColumns({ @JoinColumn(name = "user_id", referencedColumnName = "id", unique = false, nullable = true) })
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// public Short getCheckPoint() {
	// return checkPoint;
	// }
	//
	// public void setCheckPoint(Short checkPoint) {
	// this.checkPoint = checkPoint;
	// }

	public QcPoint getQcPoint() {
		return qcPoint;
	}

	public void setQcPoint(QcPoint qcPoint) {
		this.qcPoint = qcPoint;
	}

	public QAWorksheetPatientInfo getQaWorksheetPatientInfo() {
		return qaWorksheetPatientInfo;
	}

	public void setQaWorksheetPatientInfo(
			QAWorksheetPatientInfo qaWorksheetPatientInfo) {
		this.qaWorksheetPatientInfo = qaWorksheetPatientInfo;
	}

	public QAProductivitySampling getQaProductivitySampling() {
		return qaProductivitySampling;
	}

	public void setQaProductivitySampling(
			QAProductivitySampling qaProductivitySampling) {
		this.qaProductivitySampling = qaProductivitySampling;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
