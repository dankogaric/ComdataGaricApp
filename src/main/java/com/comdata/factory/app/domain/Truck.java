package com.comdata.factory.app.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.comdata.factory.app.domain.enums.VehicleType;

/**
 * A bus
 */
@Entity
@Table(name = "truck")
@Inheritance(strategy=InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@PrimaryKeyJoinColumn(name = "truck_id", referencedColumnName = "vehicle_id")
public class Truck extends Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(value = 2)
    @Max(value = 4)
    @Column(name = "number_of_axles")
    private Integer numberOfAxles;
    
    

    public Truck() {
		super();
		
	}
    
    

	public Truck(Integer numberOfAxles) {
		super();
		this.numberOfAxles = numberOfAxles;
	}



	public Truck(Long id, String color, Integer area, Manufacturer manufacturer, Parking parking,
			VehicleType vehicleType, Integer numberOfAxles) {
		super(id, color, area, manufacturer, parking, vehicleType);
		this.numberOfAxles = numberOfAxles;
		
	}

	public Integer getNumberOfAxles() {
        return numberOfAxles;
    }

    public Truck numberOfAxles(Integer numberOfAxles) {
        this.numberOfAxles = numberOfAxles;
        return this;
    }

    public void setNumberOfAxles(Integer numberOfAxles) {
        this.numberOfAxles = numberOfAxles;
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
        Truck truck = (Truck) o;
        if (truck.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), truck.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Truck{" +
            "id=" + getId() +
            ", numberOfAxles='" + getNumberOfAxles() + "'" +
            "}";
    }
 
    
}
