/**
 *
 */
package argus.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * @author vishal.joshi
 *
 */
@Entity
@Table(name = "doctor")
@EntityListeners(EntityListener.class)
public class Doctor extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @Pattern(regexp = "[A-Za-z ]*", message =
	// "must contain only letters and spaces")
	@NotNull
	private String name;

	private String doctorCode = null;

	@NotNull
	@Column(name = "non_deposit", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean nonDeposit = false;

	private float payments = 0.01F;

	private float accounting = 0;

	private float operations = 0;

	@XStreamOmitField
	@OneToMany(targetEntity = Doctor.class, mappedBy = "parent", fetch = FetchType.LAZY)
	private List<Doctor> doctors = null;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "parent_id", referencedColumnName = "id", unique = false, nullable = true) })
	private Doctor parent = null;

	@NotNull
	@Column(name = "status", columnDefinition = "TINYINT(1) DEFAULT '1'")
	private boolean status = true;

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	@Transient
	private long parentId;

	@Transient
	private long childCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Doctor getParent() {
		return parent;
	}

	public void setParent(Doctor parent) {
		this.parent = parent;
	}

	public String toString() {
		return "" + this.getId();
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public long getChildCount() {
		return childCount;
	}

	public void setChildCount(long childCount) {
		this.childCount = childCount;
	}

	public float getPayments() {
		return payments;
	}

	public void setPayments(float payments) {
		this.payments = payments;
	}

	public float getAccounting() {
		return accounting;
	}

	public void setAccounting(float accounting) {
		this.accounting = accounting;
	}

	public float getOperations() {
		return operations;
	}

	public void setOperations(float operations) {
		this.operations = operations;
	}

	public boolean isNonDeposit() {
		return nonDeposit;
	}

	public void setNonDeposit(boolean nonDeposit) {
		this.nonDeposit = nonDeposit;
	}
}
