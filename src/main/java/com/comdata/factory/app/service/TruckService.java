package com.comdata.factory.app.service;

import com.comdata.factory.app.domain.Truck;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Truck.
 */
public interface TruckService {

    /**
     * Save a truck.
     *
     * @param truck the entity to save
     * @return the persisted entity
     */
    Truck save(Truck truck);

    /**
     *  Get all the trucks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public List<Truck> findAll();
    /**
     *  Get the "id" truck.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Truck findOne(Long id);

    /**
     *  Delete the "id" truck.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
