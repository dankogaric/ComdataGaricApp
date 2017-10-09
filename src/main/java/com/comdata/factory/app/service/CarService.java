package com.comdata.factory.app.service;

import com.comdata.factory.app.domain.Bus;
import com.comdata.factory.app.domain.Car;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Car.
 */
public interface CarService {

    /**
     * Save a car.
     *
     * @param car the entity to save
     * @return the persisted entity
     */
    Car save(Car car);

    /**
     *  Get all the cars.
     *
     *  @return the list of entities
     */
    List<Car> findAll();
    
    /**
     *  Get all the cars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Car> findAll(Pageable pageable);

    /**
     *  Get the "id" car.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Car findOne(Long id);

    /**
     *  Delete the "id" car.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
