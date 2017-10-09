package com.comdata.factory.app.service;

import com.comdata.factory.app.domain.CityBus;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CityBus.
 */
public interface CityBusService {

    /**
     * Save a cityBus.
     *
     * @param cityBus the entity to save
     * @return the persisted entity
     */
    CityBus save(CityBus cityBus);

    /**
     *  Get all the cityBuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    List<CityBus> findAll();
    
    Page<CityBus> findAllPage(Pageable pageable);

    /**
     *  Get the "id" cityBus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CityBus findOne(Long id);

    /**
     *  Delete the "id" cityBus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
