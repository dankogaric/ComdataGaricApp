package com.comdata.factory.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.comdata.factory.app.domain.enums.VehicleType;

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
@PrimaryKeyJoinColumn(name = "car_id", referencedColumnName = "vehicle_id")
public class Car extends Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int AREA = 8;

    @Column(name = "seats_number")
    protected Integer seatsNumber;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(unique = true)
    protected AdditionalEquipment addEq;
    
    

    public Car() {
		super();
		
	}
    

	public Car(Long id, String color, Integer area, Manufacturer manufacturer, Parking parking,
			VehicleType vehicleType, Integer seatsNumber, AdditionalEquipment addEq) {
		super(id, color, area, manufacturer, parking, vehicleType);
		this.seatsNumber = seatsNumber;
		this.addEq = addEq;
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

    
}
