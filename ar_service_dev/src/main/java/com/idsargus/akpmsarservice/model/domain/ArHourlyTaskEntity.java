package com.idsargus.akpmsarservice.model.domain;

import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
//@Table(name = "hourly_task")  -- previously
@Table(name = "payment_prod_hourly")
public class ArHourlyTaskEntity extends BaseAuditableEntity {

    private static final long serialVersionUID = 1L;

    // private String name;
//	@OneToOne
//	@JoinColumn(name = "task_name")
//	//@JoinColumn(name = "name")  ------previously
//    private ArHourlyTaskName name;

    //@ManyToOne
//	@JoinColumns({ @JoinColumn(name = "batch_id", referencedColumnName = "id", unique = false, nullable = true) })
//	private PaymentBatch paymentBatch;
    @Column(name = "detail") ///name = "description" changed by Tausif
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

    @ManyToOne
    @JoinColumns({ @JoinColumn(name = "task_name", referencedColumnName = "id", unique = false, nullable = true) })
    private ArHourlyTaskName hourlyTask;

    @Column(name = "date_task_completed")
    private Date taskCompleted;


    @Transient
    private User CreatedByName;

//	@NotNull
//	@Column(name = "enabled", columnDefinition = "BIT default 1", nullable = false)
//	private Boolean enabled;

//	@NotNull
//	@Column(name = "deleted", columnDefinition = "BIT default 0", nullable = false)
//	private Boolean deleted;

    @Transient
    private String taskName ;
    public String getTaskName() {
        return (this.hourlyTask == null) ? null : this.hourlyTask.getName();
    }
    //   private Integer nameId;

    public Integer getNameId() {
        return (this.hourlyTask == null) ? null : this.hourlyTask.getId();
    }
    // public Integer getNameId() {
//	  return (this.name == null) ? null : this.name.getId();
//   }
    public String getCreatedByName(){
        return (this.getCreatedBy() == null) ? null : this.getCreatedBy().getFirstName()+ " "+ this.getCreatedBy().getLastName();

    }

    //
}

