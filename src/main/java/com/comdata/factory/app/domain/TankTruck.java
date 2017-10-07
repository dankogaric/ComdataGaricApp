package com.comdata.factory.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TankTruck.
 */
@Entity
@Table(name = "tank_truck")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TankTruck implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tank_capacity")
    private Integer tankCapacity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTankCapacity() {
        return tankCapacity;
    }

    public TankTruck tankCapacity(Integer tankCapacity) {
        this.tankCapacity = tankCapacity;
        return this;
    }

    public void setTankCapacity(Integer tankCapacity) {
        this.tankCapacity = tankCapacity;
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
        TankTruck tankTruck = (TankTruck) o;
        if (tankTruck.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tankTruck.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TankTruck{" +
            "id=" + getId() +
            ", tankCapacity='" + getTankCapacity() + "'" +
            "}";
    }
}
