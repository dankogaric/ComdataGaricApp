package com.comdata.factory.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Car.
 */
@Entity
@Table(name = "car")
@Inheritance(strategy=InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final long area = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="car_id")
    protected Long id;

    @Column(name = "seats_number")
    protected Integer seatsNumber;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(unique = true)
    protected AdditionalEquipment addEq;
    
    @Column(name = "color")
    protected String color;
    
    @ManyToOne(fetch=FetchType.EAGER)
    private Manufacturer manufacturer;
    
    @Column(name = "parking_id")
    protected Integer parkingId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeatsNumber() {
        return seatsNumber;
    }

    public Car seatsNumber(Integer seatsNumber) {
        this.seatsNumber = seatsNumber;
        return this;
    }

    public void setSeatsNumber(Integer seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public AdditionalEquipment getAddEq() {
        return addEq;
    }

    public Car addEq(AdditionalEquipment additionalEquipment) {
        this.addEq = additionalEquipment;
        return this;
    }

    public void setAddEq(AdditionalEquipment additionalEquipment) {
        this.addEq = additionalEquipment;
    }
 
    
    public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        if (car.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), car.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Car{" +
            "id=" + getId() +
            ", seatsNumber='" + getSeatsNumber() + "'" +
            "}";
    }

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getParkingId() {
		return parkingId;
	}

	public void setParkingId(Integer parkingId) {
		this.parkingId = parkingId;
	}
    
    
}
