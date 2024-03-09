package argus.util;


public class UserJsonData {

	private Long id;

	private String firstName;

	private String lastName;

	private String contact;

	private String address;

	private boolean status = false;

	private String email;

	private String roleName;

	private String departmentNames;

	private String lastEvaluatedOn;

	private String lastEvaluatedBy;

	private String createdOn;

	private float traineePercent;

	public float getTraineePercent() {
		return traineePercent;
	}

	public void setTraineePercent(float traineePercent) {
		this.traineePercent = traineePercent;
	}


	public String getLastEvaluatedBy() {
		return lastEvaluatedBy;
	}

	public void setLastEvaluatedBy(String lastEvaluatedBy) {
		this.lastEvaluatedBy = lastEvaluatedBy;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDepartmentNames() {
		return departmentNames;
	}

	public void setDepartmentNames(String departmentNames) {
		this.departmentNames = departmentNames;
	}

	public String getLastEvaluatedOn() {
		return lastEvaluatedOn;
	}

	public void setLastEvaluatedOn(String lastEvaluatedOn) {
		this.lastEvaluatedOn = lastEvaluatedOn;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}


}
