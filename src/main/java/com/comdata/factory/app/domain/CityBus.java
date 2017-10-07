package com.comdata.factory.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CityBus.
 */
@Entity
@Table(name = "city_bus")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CityBus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "has_whrist")
    private Boolean hasWhrist;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isHasWhrist() {
        return hasWhrist;
    }

    public CityBus hasWhrist(Boolean hasWhrist) {
        this.hasWhrist = hasWhrist;
        return this;
    }

    public void setHasWhrist(Boolean hasWhrist) {
        this.hasWhrist = hasWhrist;
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
        CityBus cityBus = (CityBus) o;
        if (cityBus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cityBus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CityBus{" +
            "id=" + getId() +
            ", hasWhrist='" + isHasWhrist() + "'" +
            "}";
    }
}
