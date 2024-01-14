//package com.idsargus.akpmsarservice.model.domain;
//
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.util.Date;
//import java.util.List;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "processmanual_subsection")
//public class ArProcessManualSubSection {
//
//    private static final long serialVersionUID = 1L;
//
//    private String title;
//
//    private Double position = 1D;
//    @ManyToOne
//    @JoinColumns({ @JoinColumn(name = "process_manual_id", referencedColumnName = "id") })
//    private ArProcessManual processManual;
//
//    @Column(columnDefinition = "LONGTEXT")
//    private String content;
//
//    @Column(name = "modification_summary", columnDefinition = "TEXT")
//    private String modificationSummary;
//
//    @Column(name = "created_by" ,insertable = false, updatable = false)
//    private Integer createdByName;
//
//    @Column(name = "modified_by", insertable = false, updatable = false)
//    private Integer modifiedByName;
//
//    @Column(name = "created_on", insertable = false, updatable = false)
//    private Date createdOnName;
//    @Column(name = "modified_on", insertable = false, updatable = false)
//    private Date modifiedOnDate;
//    @NotNull
//    @Column(name = "notification", columnDefinition = "TINYINT(1) DEFAULT '1'")
//    private boolean notification = true;
//
//    @NotNull
//    @Column(name = "status", columnDefinition = "TINYINT(1) DEFAULT '1'")
//    private boolean status = true;
//
//    @NotNull
//    @Column(name = "is_deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
//    private boolean deleted = false;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy= "processManual", targetEntity = ArFiles.class)
////	@JoinTable(name = "process_manual_files_rel", joinColumns = { @JoinColumn(name = "process_manual_id") }, inverseJoinColumns = { @JoinColumn(name = "files_id") })
//    private List<ArFiles> files;
//
//    // for joing the tables (many-to-many)
//    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
//    @JoinTable(name = "process_manual_department_rel", joinColumns = { @JoinColumn(name = "process_manual_id") }, inverseJoinColumns = { @JoinColumn(name = "department_id") })
//    private List<Department> departments;
//
//
//}
