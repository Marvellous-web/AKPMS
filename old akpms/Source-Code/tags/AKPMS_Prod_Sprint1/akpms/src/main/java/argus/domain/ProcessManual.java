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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "process_manual")
@EntityListeners(EntityListener.class)
public class ProcessManual extends BaseEntity{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private Double position = 1D;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "parent_id", referencedColumnName = "id", unique = false, nullable = true) })
	private ProcessManual parent;

	@OneToMany(cascade=CascadeType.ALL,targetEntity = ProcessManual.class, mappedBy = "parent", fetch = FetchType.LAZY)
	private List<ProcessManual> processManualList;

	@Column(columnDefinition = "LONGTEXT")
	private String content;

	@Column(name = "modification_summary", columnDefinition = "TEXT")
	private String modificationSummary;

	@NotNull
	@Column(name = "notification", columnDefinition = "TINYINT(1) DEFAULT '1'")
	private boolean notification = true;

	@NotNull
	@Column(name = "status", columnDefinition = "TINYINT(1) DEFAULT '1'")
	private boolean status = true;

	@NotNull
	@Column(name = "is_deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

	@OneToMany(cascade = CascadeType.ALL, mappedBy= "processManual", targetEntity = Files.class)
//	@JoinTable(name = "process_manual_files_rel", joinColumns = { @JoinColumn(name = "process_manual_id") }, inverseJoinColumns = { @JoinColumn(name = "files_id") })
	private List<Files> files;

	// for joing the tables (many-to-many)
	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	@JoinTable(name = "process_manual_department_rel", joinColumns = { @JoinColumn(name = "process_manual_id") }, inverseJoinColumns = { @JoinColumn(name = "department_id") })
	private List<Department> departments;

	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE})
	@JoinTable(name="process_manual_user_rel", joinColumns={@JoinColumn(name="process_manual_id")}, inverseJoinColumns={@JoinColumn(name="user_id")})
	private List<User> userList;

	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="id",referencedColumnName = "section_id",insertable = false, updatable = false)
	private ProcessManualPosition processManualPosition;

	@Transient
	private boolean readAndUnderstood;

	@Transient
	private boolean showReadAndUnderstood;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPosition() {
		return position;
	}

	public void setPosition(Double position) {
		this.position = position;
	}

	public ProcessManual getParent() {
		return parent;
	}

	public void setParent(ProcessManual parent) {
		this.parent = parent;
	}

	public List<ProcessManual> getProcessManualList() {
		return processManualList;
	}

	public void setProcessManualList(List<ProcessManual> processManualList) {
		this.processManualList = processManualList;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getModificationSummary() {
		return modificationSummary;
	}

	public void setModificationSummary(String modificationSummary) {
		this.modificationSummary = modificationSummary;
	}

	public boolean isNotification() {
		return notification;
	}

	public void setNotification(boolean notification) {
		this.notification = notification;
	}

	public List<Files> getFiles() {
		return files;
	}

	public void setFiles(List<Files> files) {
		this.files = files;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public boolean isReadAndUnderstood() {
		return readAndUnderstood;
	}

	public void setReadAndUnderstood(boolean readAndUnderstood) {
		this.readAndUnderstood = readAndUnderstood;
	}

	public ProcessManualPosition getProcessManualPosition() {
		return processManualPosition;
	}

	public void setProcessManualPosition(ProcessManualPosition processManualPosition) {
		this.processManualPosition = processManualPosition;
	}

	public boolean isShowReadAndUnderstood() {
		return showReadAndUnderstood;
	}

	public void setShowReadAndUnderstood(boolean showReadAndUnderstood) {
		this.showReadAndUnderstood = showReadAndUnderstood;
	}

}
