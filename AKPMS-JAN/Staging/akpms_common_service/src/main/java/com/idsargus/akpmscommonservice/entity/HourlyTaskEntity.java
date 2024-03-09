package com.idsargus.akpmscommonservice.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "hourly_task")
public class HourlyTaskEntity extends BaseAuditableEntity {

	private static final long serialVersionUID = 1L;

	// private String name;
	@OneToOne
	@JoinColumn(name = "name")
    private HourlyTaskName name;


	@Column(name = "details") ///name = "description" changed by Tausif
	private String details;

	//added by Tausif time , date received , task completed
	@Column(name = "time")
	private String time;
	@Column(name = "hours")
	private String hours;

	@Column(name = "minutes")
	private String minutes;
	@Column(name = "date_received")
	private Date dateReceived;


	@Column(name = "task_completed")
	private Date taskCompleted;

//commented upto chargeable by Tausif
//	@OneToOne
//	@JoinColumn(name = "department_id")
//	private DepartmentEntity department;
//
//	@NotNull
//	@Column(name = "chargeable", columnDefinition = "BIT default 1", nullable = false)
//	private Boolean chargeable;

	//added on 11.4.23 by arnrl
//@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH,
//            CascadeType.REFRESH})
//    @JoinColumns(@JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false, unique = false))
//    private User CreatedByName;

	@Transient
	private User CreatedByName;

	@NotNull
	@Column(name = "enabled", columnDefinition = "BIT default 1", nullable = false)
	private Boolean enabled;

	@NotNull
	@Column(name = "deleted", columnDefinition = "BIT default 0", nullable = false)
	private Boolean deleted;

	@Transient
    private Integer nameId;

   public Integer getNameId() {
	  return (this.name == null) ? null : this.name.getId();
   }
	public String getCreatedByName(){
		return (this.getCreatedBy() == null) ? null : this.getCreatedBy().getFirstName()+ " "+ this.getCreatedBy().getLastName();

	}

	////@Table(name = "payment_prod_hourly")
	//	//public class AdminHourlyTaskEntity extends AdminBaseAuditableEntity {
	//	//
	//	//	private static final long serialVersionUID = 1L;
	//	//
	//	//	// private String name;
	//	////	@OneToOne
	//	////	@JoinColumn(name = "task_name")
	//	////	//@JoinColumn(name = "name")  ------previously
	//	////    private AdminHourlyTaskName name;
	//	//
	//	////@ManyToOne
	//	////	@JoinColumns({ @JoinColumn(name = "batch_id", referencedColumnName = "id", unique = false, nullable = true) })
	//	////	private PaymentBatch paymentBatch;
	//	//	@Column(name = "detail") ///name = "description" changed by Tausif
	//	//	private String details;
	//	//
	//	//	//added by Tausif time , date received , task completed
	//	//	@Column(name = "time")
	//	//	private String time;
	//	//	@Column(name = "hours")
	//	//	private String hours;
	//	//
	//	//	@Column(name = "minutes")
	//	//	private String minutes;
	//	//	@Column(name = "date_received")
	//	//	private Date dateReceived;
	//	//
	//	//	// Added hourlyTask on 27.4.23
	//	//	@ManyToOne
	//	//	@JoinColumns({ @JoinColumn(name = "task_name", referencedColumnName = "id", unique = false, nullable = true) })
	//	//	private AdminHourlyTaskName hourlyTask;
	//	//
	//	//	@Column(name = "date_task_completed")
	//	//	private Date taskCompleted;
	//	//
	//	////commented upto chargeable by Tausif
	//	////	@OneToOne
	//	////	@JoinColumn(name = "department_id")
	//	////	private AdminDepartmentEntity department;
	//	////
	//	////	@NotNull
	//	////	@Column(name = "chargeable", columnDefinition = "BIT default 1", nullable = false)
	//	////	private Boolean chargeable;
	//	//
	//	//	//added on 11.4.23 by arnrl
	//	////@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH,
	//	////            CascadeType.REFRESH})
	//	////    @JoinColumns(@JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false, unique = false))
	//	////    private User CreatedByName;
	//	//
	//	//
	//	//	@Transient
	//	//	private User CreatedByName;
	//	//
	//	////	@NotNull
	//	////	@Column(name = "enabled", columnDefinition = "BIT default 1", nullable = false)
	//	////	private Boolean enabled;
	//	//
	//	////	@NotNull
	//	////	@Column(name = "deleted", columnDefinition = "BIT default 0", nullable = false)
	//	////	private Boolean deleted;
	//	//
	//	//	@Transient
	//	//	private String taskName ;
	//	//	public String getTaskName() {
	//	//   return (this.hourlyTask == null) ? null : this.hourlyTask.getName();
	//	//   }
	//	// //   private Integer nameId;
	//	//
	//	//   public Integer getNameId() {
	//	//	  return (this.hourlyTask == null) ? null : this.hourlyTask.getId();
	//	//   }
	//	//// public Integer getNameId() {
	//	////	  return (this.name == null) ? null : this.name.getId();
	//	////   }
	//	//	public String getCreatedByName(){
	//	//		return (this.getCreatedBy() == null) ? null : this.getCreatedBy().getFirstName()+ " "+ this.getCreatedBy().getLastName();
	//	//
	//	//	}
	//	//
	//	//	//
	//	//}
}
