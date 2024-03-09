package argus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin_settings")
@EntityListeners(EntityListener.class)
public class AdminSettings extends BaseEntity {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "procees_manaul_read_status_ratings")
	private String proceesManaulReadStatusRatings;

	@Column(name = "ids_argus_ratings")
	private String idsArgusRatings;

	@Column(name = "argus_ratings")
	private String argusRatings;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProceesManaulReadStatusRatings() {
		return proceesManaulReadStatusRatings;
	}

	public void setProceesManaulReadStatusRatings(
			String proceesManaulReadStatusRatings) {
		this.proceesManaulReadStatusRatings = proceesManaulReadStatusRatings;
	}

	public String getIdsArgusRatings() {
		return idsArgusRatings;
	}

	public void setIdsArgusRatings(String idsArgusRatings) {
		this.idsArgusRatings = idsArgusRatings;
	}

	public String getArgusRatings() {
		return argusRatings;
	}

	public void setArgusRatings(String argusRatings) {
		this.argusRatings = argusRatings;
	}
}