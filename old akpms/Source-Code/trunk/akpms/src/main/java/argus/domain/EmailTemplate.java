package argus.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@EntityListeners(EntityListener.class)
@Table(name = "email_template")
public class EmailTemplate extends BaseEntity implements Serializable {
	/** Default value included to remove warning. Remove or modify at will. **/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	// @Pattern(regexp = "[A-Za-z ]*", message =
	// "must contain only letters and spaces")

	private String name;

	@Column(name = "content", columnDefinition = "TEXT")
	private String content;

	@NotNull
	@Column(name = "subscription_email", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean subscriptionEmail = false;

	@NotNull
	@Column(name = "status", columnDefinition = "TINYINT(1) DEFAULT '1'")
	private boolean status = true;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_email_subscription_rel", joinColumns = { @JoinColumn(name = "email_template_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private List<User> users;

	@NotNull
	@Column(name = "is_deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
	private boolean deleted = false;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isSubscriptionEmail() {
		return subscriptionEmail;
	}

	public void setSubscriptionEmail(boolean subscriptionEmail) {
		this.subscriptionEmail = subscriptionEmail;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String toString() {
		return "" + this.getId();
	}
}