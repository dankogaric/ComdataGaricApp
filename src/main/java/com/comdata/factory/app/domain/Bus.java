package com.comdata.factory.app.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * relationship OneToOne {
 * Cabrio{car} to Car{cabrio},
 * ClassicCar{car} to Car{classicCar}
 * }
 */
@ApiModel(description = "relationship OneToOne { Cabrio{car} to Car{cabrio}, ClassicCar{car} to Car{classicCar} }")
@Entity
@Table(name = "bus")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seats_sitting")
    private Integer seatsSitting;

    @Column(name = "seats_standing")
    private Integer seatsStanding;
    
    @Column(name = "color")
    private String color;
    
    @ManyToOne
    private Manufacturer manufacturer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}
    
}
