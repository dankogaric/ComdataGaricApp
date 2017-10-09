package com.comdata.factory.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.comdata.factory.app.domain.enums.VehicleType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Cabrio.
 */
@Entity
@Table(name = "cabrio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@PrimaryKeyJoinColumn(name = "cabrio_id", referencedColumnName = "car_id")
public class Cabrio extends Car implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(name = "has_removable_roof")
    private Boolean hasRemovableRoof;
    
	public Cabrio() {
		super();
		vehicleType = VehicleType.CABRIO;
	}

    
	public Cabrio(Long id, String color, Integer area, Manufacturer manufacturer, Parking parking,
			VehicleType vehicleType, Integer seatsNumber, AdditionalEquipment addEq, Boolean hasRemovableRoof) {
		super(id, color, area, manufacturer, parking, vehicleType, seatsNumber, addEq);
		this.hasRemovableRoof = hasRemovableRoof;
	}


	public Boolean isHasRemovableRoof() {
        return hasRemovableRoof;
    }

    public Cabrio hasRemovableRoof(Boolean hasRemovableRoof) {
        this.hasRemovableRoof = hasRemovableRoof;
        return this;
    }

    public void setHasRemovableRoof(Boolean hasRemovableRoof) {
        this.hasRemovableRoof = hasRemovableRoof;
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
        Cabrio cabrio = (Cabrio) o;
        if (cabrio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cabrio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cabrio{" +
            "id=" + getId() +
            ", hasRemovableRoof='" + isHasRemovableRoof() + "'" +
            "}";
    }
}
