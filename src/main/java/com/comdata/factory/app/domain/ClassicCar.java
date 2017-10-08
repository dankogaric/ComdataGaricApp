package com.comdata.factory.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ClassicCar.
 */
@Entity
@Table(name = "classic_car")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@PrimaryKeyJoinColumn(name = "classic_car_id", referencedColumnName = "car_id")
public class ClassicCar extends Car implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(name = "roof_top_capacity")
    private Integer roofTopCapacity;

    public Integer getRoofTopCapacity() {
        return roofTopCapacity;
    }

    public ClassicCar roofTopCapacity(Integer roofTopCapacity) {
        this.roofTopCapacity = roofTopCapacity;
        return this;
    }

    public void setRoofTopCapacity(Integer roofTopCapacity) {
        this.roofTopCapacity = roofTopCapacity;
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
        ClassicCar classicCar = (ClassicCar) o;
        if (classicCar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classicCar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassicCar{" +
            "id=" + getId() +
            ", roofTopCapacity='" + getRoofTopCapacity() + "'" +
            "}";
    }
}
