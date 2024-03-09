package argus.util;

public class HourlyTaskJsonData {

	private Long id;

	private String name;

	private String desc;

	private String Chargeable;
	
	private String department;

	// private String type ;

	private boolean status = false;

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/*
	 * public String getType() { return type; }
	 *
	 * public void setType(String type) { this.type = type; }
	 */

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the chargeable
	 */
	public String getChargeable() {
		return Chargeable;
	}

	/**
	 * @param chargeable
	 *            the chargeable to set
	 */
	public void setChargeable(String chargeable) {
		Chargeable = chargeable;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
}
