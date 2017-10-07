package com.comdata.factory.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AdditionalEquipment.
 */
@Entity
@Table(name = "additional_equipment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdditionalEquipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "has_abs")
    private Boolean hasAbs;

    @Column(name = "has_esp")
    private Boolean hasEsp;

    @Column(name = "has_glass_roof")
    private Boolean hasGlassRoof;

    @Column(name = "has_alu_wheels")
    private Boolean hasAluWheels;

    @OneToOne(mappedBy = "addEq")
    @JsonIgnore
    private Car car;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isHasAbs() {
        return hasAbs;
    }

    public AdditionalEquipment hasAbs(Boolean hasAbs) {
        this.hasAbs = hasAbs;
        return this;
    }

    public void setHasAbs(Boolean hasAbs) {
        this.hasAbs = hasAbs;
    }

    public Boolean isHasEsp() {
        return hasEsp;
    }

    public AdditionalEquipment hasEsp(Boolean hasEsp) {
        this.hasEsp = hasEsp;
        return this;
    }

    public void setHasEsp(Boolean hasEsp) {
        this.hasEsp = hasEsp;
    }

    public Boolean isHasGlassRoof() {
        return hasGlassRoof;
    }

    public AdditionalEquipment hasGlassRoof(Boolean hasGlassRoof) {
        this.hasGlassRoof = hasGlassRoof;
        return this;
    }

    public void setHasGlassRoof(Boolean hasGlassRoof) {
        this.hasGlassRoof = hasGlassRoof;
    }

    public Boolean isHasAluWheels() {
        return hasAluWheels;
    }

    public AdditionalEquipment hasAluWheels(Boolean hasAluWheels) {
        this.hasAluWheels = hasAluWheels;
        return this;
    }

    public void setHasAluWheels(Boolean hasAluWheels) {
        this.hasAluWheels = hasAluWheels;
    }

    public Car getCar() {
        return car;
    }

    public AdditionalEquipment car(Car car) {
        this.car = car;
        return this;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdditionalEquipment additionalEquipment = (AdditionalEquipment) o;
        if (additionalEquipment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), additionalEquipment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdditionalEquipment{" +
            "id=" + getId() +
            ", hasAbs='" + isHasAbs() + "'" +
            ", hasEsp='" + isHasEsp() + "'" +
            ", hasGlassRoof='" + isHasGlassRoof() + "'" +
            ", hasAluWheels='" + isHasAluWheels() + "'" +
            "}";
    }
}
