package argus.domain;

import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@EntityListeners(EntityListener.class)
@Table(name = "qc_point")
public class QcPoint extends BaseEntity {
	/** Default value included to remove warning. Remove or modify at will. **/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @Pattern(regexp = "[A-Za-z ]*", message =
	// "must contain only letters and spaces")
	@Column(name = "name", columnDefinition = "TEXT")
	private String name;

	@OneToMany(targetEntity = QcPoint.class, mappedBy = "parent", fetch = FetchType.LAZY)
	private List<QcPoint> qcPoints = null;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "parent_id", referencedColumnName = "id", unique = false, nullable = true) })
	private QcPoint parent = null;

	// @ManyToOne(cascade={CascadeType.REFRESH, CascadeType.DETACH},
	// fetch=FetchType.LAZY, targetEntity = Department.class)
	// @JoinColumn(name = "department_id", referencedColumnName = "id", unique =
	// false, nullable = false)
	// private Department department;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinColumns(@JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false, unique = false))
	private Department department;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinColumns(@JoinColumn(name = "sub_department_id", referencedColumnName = "id", nullable = true, unique = false))
	private Department subDepartment;

	@NotNull
	@Column(name = "status", columnDefinition = "TINYINT(1) DEFAULT '1'")
	private boolean status = true;

	@NotNull
	@Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

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

	public List<QcPoint> getQcPoints() {
		return qcPoints;
	}

	public void setQcPoints(List<QcPoint> qcPoints) {
		this.qcPoints = qcPoints;
	}

	public QcPoint getParent() {
		return parent;
	}

	public void setParent(QcPoint parent) {
		this.parent = parent;
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

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return "" + this.getId();
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * @return the subDepartment
	 */
	public Department getSubDepartment() {
		return subDepartment;
	}

	/**
	 * @param subDepartment
	 *            the subDepartment to set
	 */
	public void setSubDepartment(Department subDepartment) {
		this.subDepartment = subDepartment;
	}

}

