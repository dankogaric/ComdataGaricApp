package com.comdata.factory.app.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.comdata.factory.app.domain.enums.VehicleType;

/**
 * relationship OneToOne {
 * Cabrio{car} to Car{cabrio},
 * ClassicCar{car} to Car{classicCar}
 * }
 */
@Entity
@Table(name = "bus")
@Inheritance(strategy=InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@PrimaryKeyJoinColumn(name = "bus_id", referencedColumnName = "vehicle_id")
public class Bus extends Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "seats_sitting")
    private Integer seatsSitting;

    @Column(name = "seats_standing")
    private Integer seatsStanding;
    
    
    
	public Bus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bus(Long id, String color, Integer area, Manufacturer manufacturer, Parking parking,
			VehicleType vehicleType, Integer seatsSitting, Integer seatsStanding) {
		super(id, color, area, manufacturer, parking, vehicleType);
		this.seatsSitting = seatsSitting;
		this.seatsStanding = seatsStanding;
	}
	
	

	public Bus(Integer seatsSitting, Integer seatsStanding) {
		super();
		this.seatsSitting = seatsSitting;
		this.seatsStanding = seatsStanding;
	}

	public Integer getSeatsSitting() {
        return seatsSitting;
    }

    public Bus seatsSitting(Integer seatsSitting) {
        this.seatsSitting = seatsSitting;
        return this;
    }

    public void setSeatsSitting(Integer seatsSitting) {
        this.seatsSitting = seatsSitting;
    }

    public Integer getSeatsStanding() {
        return seatsStanding;
    }

    public Bus seatsStanding(Integer seatsStanding) {
        this.seatsStanding = seatsStanding;
        return this;
    }

    public void setSeatsStanding(Integer seatsStanding) {
        this.seatsStanding = seatsStanding;
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
        Bus bus = (Bus) o;
        if (bus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bus{" +
            "id=" + getId() +
            ", seatsSitting='" + getSeatsSitting() + "'" +
            ", seatsStanding='" + getSeatsStanding() + "'" +
            "}";
    }    
}
