package com.comdata.factory.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Cabrio.
 */
@Entity
@Table(name = "cabrio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cabrio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "has_removable_roof")
    private Boolean hasRemovableRoof;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
