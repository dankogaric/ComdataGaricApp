package com.comdata.factory.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TructorTruck.
 */
@Entity
@Table(name = "tructor_truck")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TructorTruck implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "horse_power")
    private Integer horsePower;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHorsePower() {
        return horsePower;
    }

    public TructorTruck horsePower(Integer horsePower) {
        this.horsePower = horsePower;
        return this;
    }

    public void setHorsePower(Integer horsePower) {
        this.horsePower = horsePower;
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
        TructorTruck tructorTruck = (TructorTruck) o;
        if (tructorTruck.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tructorTruck.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TructorTruck{" +
            "id=" + getId() +
            ", horsePower='" + getHorsePower() + "'" +
            "}";
    }
}
