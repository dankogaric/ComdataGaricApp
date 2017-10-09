package com.comdata.factory.app.service;

import com.comdata.factory.app.domain.Parking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Parking.
 */
public interface ParkingService {

    /**
     * Save a parking.
     *
     * @param parking the entity to save
     * @return the persisted entity
     */
    Parking save(Parking parking);

    /**
     *  Get all the parkings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Parking> findAll(Pageable pageable);

    /**
     *  Get the "id" parking.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Parking findOne(Long id);

    /**
     *  Delete the "id" parking.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
