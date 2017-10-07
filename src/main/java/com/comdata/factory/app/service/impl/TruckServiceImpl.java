package com.comdata.factory.app.service.impl;

import com.comdata.factory.app.service.TruckService;
import com.comdata.factory.app.domain.Truck;
import com.comdata.factory.app.repository.TruckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Truck.
 */
@Service
@Transactional
public class TruckServiceImpl implements TruckService{

    private final Logger log = LoggerFactory.getLogger(TruckServiceImpl.class);

    private final TruckRepository truckRepository;

    public TruckServiceImpl(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    /**
     * Save a truck.
     *
     * @param truck the entity to save
     * @return the persisted entity
     */
    @Override
    public Truck save(Truck truck) {
        log.debug("Request to save Truck : {}", truck);
        return truckRepository.save(truck);
    }

    /**
     *  Get all the trucks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Truck> findAll(Pageable pageable) {
        log.debug("Request to get all Trucks");
        return truckRepository.findAll(pageable);
    }

    /**
     *  Get one truck by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Truck findOne(Long id) {
        log.debug("Request to get Truck : {}", id);
        return truckRepository.findOne(id);
    }

    /**
     *  Delete the  truck by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Truck : {}", id);
        truckRepository.delete(id);
    }
}
