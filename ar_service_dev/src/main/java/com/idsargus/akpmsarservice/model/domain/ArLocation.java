package com.idsargus.akpmsarservice.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "location")
public class ArLocation  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @NotNull
    @Column(name = "enabled", columnDefinition = "TINYINT(1) DEFAULT '1'")
    private boolean enabled = true;

    @Column(name = "description", columnDefinition = "TEXT")
    private String desc;

    @NotNull
    @Column(name = "is_deleted", columnDefinition = "TINYINT(1) DEFAULT '0'")
    private boolean deleted = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String toString() {
        return "" + this.getId();
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @param deleted the deleted to set
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


}

