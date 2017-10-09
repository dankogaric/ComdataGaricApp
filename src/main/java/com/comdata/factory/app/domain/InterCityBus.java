package com.comdata.factory.app.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.comdata.factory.app.domain.enums.VehicleType;

/**
 * A InterCityBus.
 */
@Entity
@Table(name = "inter_city_bus")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@PrimaryKeyJoinColumn(name = "inter_city_bus_id", referencedColumnName = "bus_id")
public class InterCityBus extends Bus implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int AREA = 15;
    
    
    @Column(name = "trunk_capacity")
    private Integer trunkCapacity;
    
    


    public InterCityBus(Integer trunkCapacity) {
		super();
		this.trunkCapacity = trunkCapacity;
	}

	public InterCityBus(Integer seatsSitting, Integer seatsStanding) {
		super(seatsSitting, seatsStanding);

	}

	public InterCityBus(Long id, String color, Integer area, Manufacturer manufacturer, Parking parking,
			VehicleType vehicleType, Integer seatsSitting, Integer seatsStanding, Integer trunkCapacity) {
		super(id, color, area, manufacturer, parking, vehicleType, seatsSitting, seatsStanding);
		this.trunkCapacity = trunkCapacity;
	}

	public InterCityBus() {
		super();
		vehicleType = VehicleType.INTERCITY_BUS;
	}

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
