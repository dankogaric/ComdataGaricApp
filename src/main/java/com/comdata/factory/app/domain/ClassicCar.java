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
public class ClassicCar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "roof_top_capacity")
    private Integer roofTopCapacity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
