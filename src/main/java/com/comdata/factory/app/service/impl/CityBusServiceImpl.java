package com.comdata.factory.app.service.impl;

import com.comdata.factory.app.service.CityBusService;
import com.comdata.factory.app.domain.CityBus;
import com.comdata.factory.app.repository.CityBusRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CityBus.
 */
@Service
@Transactional
public class CityBusServiceImpl implements CityBusService{

    private final Logger log = LoggerFactory.getLogger(CityBusServiceImpl.class);

    private final CityBusRepository cityBusRepository;

    public CityBusServiceImpl(CityBusRepository cityBusRepository) {
        this.cityBusRepository = cityBusRepository;
    }

    /**
     * Save a cityBus.
     *
     * @param cityBus the entity to save
     * @return the persisted entity
     */
    @Override
    public CityBus save(CityBus cityBus) {
        log.debug("Request to save CityBus : {}", cityBus);
        return cityBusRepository.save(cityBus);
    }

    /**
     *  Get all the cityBuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CityBus> findAll() {
        log.debug("Request to get all CityBuses");
        return cityBusRepository.findAll();
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public Page<CityBus> findAllPage(Pageable pageable) {
        log.debug("Request to get all Vehicles");
        return cityBusRepository.findAll(pageable);
    }

    /**
     *  Get one cityBus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CityBus findOne(Long id) {
        log.debug("Request to get CityBus : {}", id);
        return cityBusRepository.findOne(id);
    }

    /**
     *  Delete the  cityBus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CityBus : {}", id);
        cityBusRepository.delete(id);
    }
}
