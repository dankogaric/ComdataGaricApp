package com.comdata.factory.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Parking.
 */
@Entity
@Table(name = "parking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Parking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "area")
    private Integer area;

    @Column(name = "height")
    private Double height;

    @Column(name = "rest_area")
    private Integer restArea;

    @Column(name = "has_roof")
    private Boolean hasRoof;

    public Parking() {
    	
    }
    
    public Parking(Parking parking) {
    	if(parking == null) {
    		return;
    	}
    	this.id = parking.id;
		this.area = parking.area;
		this.height = parking.height;
		this.restArea = parking.restArea;
		this.hasRoof = parking.hasRoof;
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getArea() {
        return area;
    }

    public Parking area(Integer area) {
        this.area = area;
        return this;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Double getHeight() {
        return height;
    }

    public Parking height(Double height) {
        this.height = height;
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getRestArea() {
        return restArea;
    }

    public Parking restArea(Integer restArea) {
        this.restArea = restArea;
        return this;
    }

    public void setRestArea(Integer restArea) {
        this.restArea = restArea;
    }

    public Boolean isHasRoof() {
        return hasRoof;
    }

    public Parking hasRoof(Boolean hasRoof) {
        this.hasRoof = hasRoof;
        return this;
    }

    public void setHasRoof(Boolean hasRoof) {
        this.hasRoof = hasRoof;
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
        Parking parking = (Parking) o;
        if (parking.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parking.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Parking{" +
            "id=" + getId() +
            ", area='" + getArea() + "'" +
            ", height='" + getHeight() + "'" +
            ", restArea='" + getRestArea() + "'" +
            ", hasRoof='" + isHasRoof() + "'" +
            "}";
    }
}
