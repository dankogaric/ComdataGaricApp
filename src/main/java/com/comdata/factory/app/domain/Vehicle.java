package com.comdata.factory.app.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.comdata.factory.app.domain.enums.VehicleType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Basic attributes of every vehicle
 */
@ApiModel(description = "Basic attributes of every vehicle")
@Entity
@Table(name = "vehicle")
@Inheritance(strategy=InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "vehicle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "color")
    protected String color;

    @Column(name = "area")
    protected Integer area;

    @ManyToOne(fetch=FetchType.EAGER)
    protected Manufacturer manufacturer;

    @ManyToOne(fetch=FetchType.EAGER)
    protected Parking parking;
    
    @Column(name = "vehicle_type")
    @Enumerated(EnumType.STRING)
    protected VehicleType vehicleType;
    
    

    public Vehicle() {
		super();
		
	}

	public Vehicle(Long id, String color, Integer area, Manufacturer manufacturer, Parking parking,
			VehicleType vehicleType) {
		super();
		this.id = id;
		this.color = color;
		this.area = area;
		this.manufacturer = manufacturer;
		this.parking = parking;
		this.vehicleType = vehicleType;
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public Vehicle color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getArea() {
        return area;
    }

    public Vehicle area(Integer area) {
        this.area = area;
        return this;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public Vehicle manufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Parking getParking() {
        return parking;
    }

    public Vehicle parking(Parking parking) {
        this.parking = parking;
        return this;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
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
        Vehicle vehicle = (Vehicle) o;
        if (vehicle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", color='" + getColor() + "'" +
            ", area='" + getArea() + "'" +
            "}";
    }

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

    
    
    
    
}
