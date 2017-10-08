package com.comdata.factory.app.service;

import com.comdata.factory.app.domain.Bus;

import java.util.List;

/**
 * Service Interface for managing Bus.
 */
public interface BusService {

    /**
     * Save a bus.
     *
     * @param bus the entity to save
     * @return the persisted entity
     */
    Bus save(Bus bus);

    /**
     *  Get all the buses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    List<Bus> findAll();

    /**
     *  Get the "id" bus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Bus findOne(Long id);

    /**
     *  Delete the "id" bus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
