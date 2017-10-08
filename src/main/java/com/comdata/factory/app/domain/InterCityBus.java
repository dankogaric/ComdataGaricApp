package com.comdata.factory.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A InterCityBus.
 */
@Entity
@Table(name = "inter_city_bus")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@PrimaryKeyJoinColumn(name = "inter_city_bus_id", referencedColumnName = "bus_id")
public class InterCityBus extends Bus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "trunk_capacity")
    private Integer trunkCapacity;


    public Integer getTrunkCapacity() {
        return trunkCapacity;
    }

    public InterCityBus trunkCapacity(Integer trunkCapacity) {
        this.trunkCapacity = trunkCapacity;
        return this;
    }

    public void setTrunkCapacity(Integer trunkCapacity) {
        this.trunkCapacity = trunkCapacity;
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
        InterCityBus interCityBus = (InterCityBus) o;
        if (interCityBus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), interCityBus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InterCityBus{" +
            "id=" + getId() +
            ", trunkCapacity='" + getTrunkCapacity() + "'" +
            "}";
    }
}
