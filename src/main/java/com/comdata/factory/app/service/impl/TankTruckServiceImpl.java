package com.comdata.factory.app.service.impl;

import com.comdata.factory.app.service.TankTruckService;
import com.comdata.factory.app.domain.TankTruck;
import com.comdata.factory.app.domain.Vehicle;
import com.comdata.factory.app.repository.TankTruckRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TankTruck.
 */
@Service
@Transactional
public class TankTruckServiceImpl implements TankTruckService{

    private final Logger log = LoggerFactory.getLogger(TankTruckServiceImpl.class);

    private final TankTruckRepository tankTruckRepository;

    public TankTruckServiceImpl(TankTruckRepository tankTruckRepository) {
        this.tankTruckRepository = tankTruckRepository;
    }

    /**
     * Save a tankTruck.
     *
     * @param tankTruck the entity to save
     * @return the persisted entity
     */
    @Override
    public TankTruck save(TankTruck tankTruck) {
        log.debug("Request to save TankTruck : {}", tankTruck);
        return tankTruckRepository.save(tankTruck);
    }

    /**
     *  Get all the tankTrucks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TankTruck> findAll() {
        log.debug("Request to get all TankTrucks");
        return tankTruckRepository.findAll();
    }

    
    @Override
    @Transactional(readOnly = true)
    public Page<TankTruck> findAll(Pageable pageable) {
        log.debug("Request to get all Vehicles");
        return tankTruckRepository.findAll(pageable);
    }

    /**
     *  Get one tankTruck by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TankTruck findOne(Long id) {
        log.debug("Request to get TankTruck : {}", id);
        return tankTruckRepository.findOne(id);
    }

    /**
     *  Delete the  tankTruck by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TankTruck : {}", id);
        tankTruckRepository.delete(id);
    }
}
