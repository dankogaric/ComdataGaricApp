package com.comdata.factory.app.service;

import com.comdata.factory.app.domain.TankTruck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TankTruck.
 */
public interface TankTruckService {

    /**
     * Save a tankTruck.
     *
     * @param tankTruck the entity to save
     * @return the persisted entity
     */
    TankTruck save(TankTruck tankTruck);

    /**
     *  Get all the tankTrucks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TankTruck> findAll(Pageable pageable);

    /**
     *  Get the "id" tankTruck.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TankTruck findOne(Long id);

    /**
     *  Delete the "id" tankTruck.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
