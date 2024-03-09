/**
 *
 */
package argus.util;

/**
 * @author sumit.v
 *
 */
public class TraineeEvaluationReportData {

	private Long id;

	private String firstName;

	private String lastName;

	private float processManualReadpercent;

	private float idsArgusPercent;

	private float argusPercent;

	private float totalTraineePercent;

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

	public float getProcessManualReadpercent() {
		return processManualReadpercent;
	}

	public void setProcessManualReadpercent(float processManualReadpercent) {
		this.processManualReadpercent = processManualReadpercent;
	}

	public float getIdsArgusPercent() {
		return idsArgusPercent;
	}

	public void setIdsArgusPercent(float idsArgusPercent) {
		this.idsArgusPercent = idsArgusPercent;
	}

	public float getArgusPercent() {
		return argusPercent;
	}

	public void setArgusPercent(float argusPercent) {
		this.argusPercent = argusPercent;
	}

	public float getTotalTraineePercent() {
		return totalTraineePercent;
	}

	public void setTotalTraineePercent(float totalTraineePercent) {
		this.totalTraineePercent = totalTraineePercent;
	}


}
