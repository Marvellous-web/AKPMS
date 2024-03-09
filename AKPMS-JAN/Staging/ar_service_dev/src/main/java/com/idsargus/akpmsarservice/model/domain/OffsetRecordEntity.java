package com.idsargus.akpmsarservice.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
        name = "offset_record"
)

@Getter
@Setter
@Data
public class OffsetRecordEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name= "amount")
    private double amount;
    @Column(name= "cpt")
    private String cpt;
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name= "dos")
    private Date dos;
    @Column(name= "offset_id")
    private Long offsetId;



}