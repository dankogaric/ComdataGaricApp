package com.comdata.factory.app.service;

import com.comdata.factory.app.domain.Vehicle;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Vehicle.
 */
public interface VehicleService {

    /**
     * Save a vehicle.
     *
     * @param vehicle the entity to save
     * @return the persisted entity
     */
    Vehicle save(Vehicle vehicle);

    /**
     *  Get all the vehicles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Vehicle> findAll(Pageable pageable);
    
    /**
     *  Get  vehicles per parking.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
//    Page<Vehicle> findVehiclesByParkingId(Integer parkingId, Pageable pageable);

    /**
     *  Get the "id" vehicle.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Vehicle findOne(Long id);

    /**
     *  Delete the "id" vehicle.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    Page<Vehicle> findVehiclesByParkingId(Long parkingId,Pageable pageable);
}
