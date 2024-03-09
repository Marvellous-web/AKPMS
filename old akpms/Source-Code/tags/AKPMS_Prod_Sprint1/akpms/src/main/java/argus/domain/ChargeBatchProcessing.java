package argus.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import argus.util.XstreamDateConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias(value = "ChargeBatchProcessing")
@Entity
@Table(name = "charge_batch_processing")
@EntityListeners(value = EntityListener.class)
public class ChargeBatchProcessing extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String type = "CHARGE_BATCH_TYPE_FACILITY";

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "doctor_id", referencedColumnName = "id", unique = false, nullable = true)
	private Doctor doctor;

	@XStreamConverter(value = XstreamDateConverter.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dos_from")
	private Date dosFrom; // Date Of Service From

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dos_to")
	private Date dosTo; // Date Of Service To

	@Column(name = "number_of_superbills")
	private Integer numberOfSuperbills;

	@Column(name = "number_of_attachments")
	private Integer numberOfAttachments;

	@Column(name = "remark", columnDefinition = "TEXT")
	private String remarks;

	@XStreamConverter(value = XstreamDateConverter.class)
	@Column(name = "date_received")
	private Date dateReceived;

	@Column(name = "date_posted")
	private Date dateBatchPosted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recevied_by", referencedColumnName = "id", nullable = true, unique = false)
	private User receviedBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "posted_by", referencedColumnName = "id", nullable = true, unique = false)
	private User postedBy;

	@Column(name = "number_of_superbills_argus")
	private Integer numberOfSuperbillsArgus;

	@Column(name = "number_of_attachments_argus")
	private Integer numberOfAttachmentsArgus;

	@Column(name = "remarks_argus", columnDefinition = "TEXT")
	private String remarksArgus;

	@Transient
	private String dateFrom;

	@Transient
	private String dateTo;

	@Transient
	private Date dateReceivedFrom;

	@Transient
	private Date dateReceivedTo;

	@Transient
	private Date dateBatchPostedTo;

	@Transient
	private Date dateBatchPostedFrom;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "posted_on")
	private Date postedOn;

	@Transient
	private String typeValue;

	@Transient
	private boolean editDoctorSection = false;

	@Transient
	private String tempBatchType;

	@Transient
	private int superBillDifference = 0;

	@Transient
	private int attachmentDifference = 0;

	public Date getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Date getDosFrom() {
		return dosFrom;
	}

	public void setDosFrom(Date dosFrom) {
		this.dosFrom = dosFrom;
	}

	public Date getDosTo() {
		return dosTo;
	}

	public void setDosTo(Date dosTo) {
		this.dosTo = dosTo;
	}

	public Integer getNumberOfSuperbills() {
		return numberOfSuperbills;
	}

	public void setNumberOfSuperbills(Integer numberOfSuperbills) {
		this.numberOfSuperbills = numberOfSuperbills;
	}

	public Integer getNumberOfAttachments() {
		return numberOfAttachments;
	}

	public void setNumberOfAttachments(Integer numberOfAttachments) {
		this.numberOfAttachments = numberOfAttachments;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	public User getReceviedBy() {
		return receviedBy;
	}

	public void setReceviedBy(User receviedBy) {
		this.receviedBy = receviedBy;
	}

	public Integer getNumberOfSuperbillsArgus() {
		return numberOfSuperbillsArgus;
	}

	public void setNumberOfSuperbillsArgus(Integer numberOfSuperbillsArgus) {
		this.numberOfSuperbillsArgus = numberOfSuperbillsArgus;
	}

	public Integer getNumberOfAttachmentsArgus() {
		return numberOfAttachmentsArgus;
	}

	public void setNumberOfAttachmentsArgus(Integer numberOfAttachmentsArgus) {
		this.numberOfAttachmentsArgus = numberOfAttachmentsArgus;
	}

	public String getRemarksArgus() {
		return remarksArgus;
	}

	public void setRemarksArgus(String remarksArgus) {
		this.remarksArgus = remarksArgus;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public Date getDateReceivedFrom() {
		return dateReceivedFrom;
	}

	public void setDateReceivedFrom(Date dateReceivedFrom) {
		this.dateReceivedFrom = dateReceivedFrom;
	}

	public Date getDateReceivedTo() {
		return dateReceivedTo;
	}

	public void setDateReceivedTo(Date dateReceivedTo) {
		this.dateReceivedTo = dateReceivedTo;
	}

	public Date getDateBatchPosted() {
		return dateBatchPosted;
	}

	public void setDateBatchPosted(Date dateBatchPosted) {
		this.dateBatchPosted = dateBatchPosted;
	}

	public Date getDateBatchPostedTo() {
		return dateBatchPostedTo;
	}

	public void setDateBatchPostedTo(Date dateBatchPostedTo) {
		this.dateBatchPostedTo = dateBatchPostedTo;
	}

	public Date getDateBatchPostedFrom() {
		return dateBatchPostedFrom;
	}

	public void setDateBatchPostedFrom(Date dateBatchPostedFrom) {
		this.dateBatchPostedFrom = dateBatchPostedFrom;
	}

	public User getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public boolean isEditDoctorSection() {
		return editDoctorSection;
	}

	public void setEditDoctorSection(boolean editDoctorSection) {
		this.editDoctorSection = editDoctorSection;
	}

	public String getTempBatchType() {
		return tempBatchType;
	}

	public void setTempBatchType(String tempBatchType) {
		this.tempBatchType = tempBatchType;
	}

	public int getSuperBillDifference() {
		return superBillDifference;
	}

	public void setSuperBillDifference(int superBillDifference) {
		this.superBillDifference = superBillDifference;
	}

	public int getAttachmentDifference() {
		return attachmentDifference;
	}

	public void setAttachmentDifference(int attachmentDifference) {
		this.attachmentDifference = attachmentDifference;
	}

	public String toStrng() {
		return "" + this.getId();
	}

}
