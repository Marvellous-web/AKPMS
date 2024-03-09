package com.idsargus.akpmsadminservice.entity;

        import javax.persistence.Column;
        import javax.persistence.Entity;
        import javax.persistence.Table;
        import javax.validation.constraints.NotNull;

        import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
        import com.idsargus.akpmscommonservice.entity.BaseAuditableEntity;

        import lombok.Getter;
        import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "charge_batch_type")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ChargeBatchType extends AdminBaseAuditableEntity{


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String name;

    private String description;

    @NotNull
    @Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
    private Boolean deleted = false;

    @NotNull
    @Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
    private Boolean enabled = true;

}
