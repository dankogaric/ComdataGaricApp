package com.comdata.factory.app.service.impl;

import com.comdata.factory.app.service.BusService;
import com.comdata.factory.app.domain.Bus;
import com.comdata.factory.app.repository.BusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Bus.
 */
@Service
@Transactional
public class BusServiceImpl implements BusService{

    private final Logger log = LoggerFactory.getLogger(BusServiceImpl.class);

    private final BusRepository busRepository;

    public BusServiceImpl(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    /**
     * Save a bus.
     *
     * @param bus the entity to save
     * @return the persisted entity
     */
    @Override
    public Bus save(Bus bus) {
        log.debug("Request to save Bus : {}", bus);
        return busRepository.save(bus);
    }

    /**
     *  Get all the buses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Bus> findAll(Pageable pageable) {
        log.debug("Request to get all Buses");
        return busRepository.findAll(pageable);
    }

    /**
     *  Get one bus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Bus findOne(Long id) {
        log.debug("Request to get Bus : {}", id);
        return busRepository.findOne(id);
    }

    /**
     *  Delete the  bus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bus : {}", id);
        busRepository.delete(id);
    }
}
