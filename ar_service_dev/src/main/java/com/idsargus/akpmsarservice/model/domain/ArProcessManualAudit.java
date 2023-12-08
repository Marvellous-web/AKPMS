package com.idsargus.akpmsarservice.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.idsargus.akpmsarservice.model.BaseEntity;
import com.idsargus.akpmsarservice.repository.UserDataRestRepository;
import com.idsargus.akpmscommonservice.entity.ArProductivityWorkFlowEntity;
import com.idsargus.akpmscommonservice.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "process_manual_audit")
public class ArProcessManualAudit{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "process_manual_id")
    private Long processManualId;
    @Column(name="action")
    private String action;

//    @ManyToOne(cascade= {CascadeType.ALL}, fetch = FetchType.EAGER)
//    @JoinColumn(name="modified_by", referencedColumnName="id", unique = false)
//    private UserEntity modifiedBy;

    @Column(columnDefinition = "TEXT")
    private String content;

    @JsonIgnoreProperties
    @Column(name = "modified_by")
    private String userId;
    @Column(name = "modification_summary", columnDefinition = "TEXT")
    private String modificationSummary;
    @Column(name="modified_on")
    private Date modifiedOn;

    @Transient
    private String modifiedBy;





}
