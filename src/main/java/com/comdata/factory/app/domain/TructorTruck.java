package com.comdata.factory.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TructorTruck.
 */
@Entity
@Table(name = "tructor_truck")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@PrimaryKeyJoinColumn(name = "tructor_truck_id", referencedColumnName = "truck_id")
public class TructorTruck extends Truck implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "horse_power")
    private Integer horsePower;

    public Integer getHorsePower() {
        return horsePower;
    }

    public TructorTruck horsePower(Integer horsePower) {
        this.horsePower = horsePower;
        return this;
    }

    public void setHorsePower(Integer horsePower) {
        this.horsePower = horsePower;
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
        TructorTruck tructorTruck = (TructorTruck) o;
        if (tructorTruck.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tructorTruck.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TructorTruck{" +
            "id=" + getId() +
            ", horsePower='" + getHorsePower() + "'" +
            "}";
    }
}
