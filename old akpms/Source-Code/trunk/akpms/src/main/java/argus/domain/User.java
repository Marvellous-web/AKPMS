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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import argus.util.Constants;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"), name = "user")
@EntityListeners(EntityListener.class)
public class User extends BaseEntity {
	/** Default value included to remove warning. Remove or modify at will. **/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name", length = Constants.HUNDRED)
	private String firstName;

	@Column(name = "last_name", length = Constants.HUNDRED)
	private String lastName;

	@Column(name = "password")
	private String password;

	@Column(name = "contact")
	private String contact;

	@Column(name = "address", columnDefinition="TEXT")
	private String address;

	@Size(min = Constants.ONE, max = Constants.HUNDRED)
	@Column(name = "token")
	private String token;

	@NotNull
	@Column(name = "status", columnDefinition = "tinyint(1) default '1'")
	private boolean status = false;

	@NotNull
	@Column(name = "is_deleted", columnDefinition = "tinyint(1) default '0'")
	private boolean deleted = false;

	private String email;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumns({ @JoinColumn(name = "role_id", referencedColumnName = "id", unique = false, nullable = true) })
	private Role role = null;

	/* for joing the tables (many-to-many) */
	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},fetch=FetchType.LAZY)
	@JoinTable(name = "user_dept_rel", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "dept_id") })
	private List<Department> departments = null;

	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH, CascadeType.REMOVE},fetch=FetchType.LAZY)
	@JoinTable(name = "user_permission_rel", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "permission_id") })
	private List<Permission> permissions = null;

	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH, CascadeType.REMOVE},fetch=FetchType.LAZY)
	@JoinTable(name = "user_email_subscription_rel", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "email_template_id") })
	private List<EmailTemplate> emailTemplate = null;

	@Transient
	private String lastLogin;

	@ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH, CascadeType.REMOVE},fetch=FetchType.LAZY)
	@JoinTable(name = "user_notification_rel", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "notification_id") })
	private List<Notifications> notificationsList = null;

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public List<EmailTemplate> getEmailTemplate() {
		return emailTemplate;
	}

	public void setEmailTemplate(List<EmailTemplate> emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

	public List<Notifications> getNotificationsList() {
		return notificationsList;
	}

	public void setNotificationsList(List<Notifications> notificationsList) {
		this.notificationsList = notificationsList;
	}

}
