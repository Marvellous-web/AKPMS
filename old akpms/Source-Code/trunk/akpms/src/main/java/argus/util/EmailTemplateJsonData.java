package argus.util;

public class EmailTemplateJsonData {
	
	private long id;
		
	private String name;
	
	private String subscriptionEmail;
	
	private boolean status = true;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getSubscriptionEmail() {
		return subscriptionEmail;
	}

	public void setSubscriptionEmail(String subscriptionEmail) {
		this.subscriptionEmail = subscriptionEmail;
	}	
}
