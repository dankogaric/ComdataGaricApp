package com.comdata.factory.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdata.factory.app.domain.Bus;
import com.comdata.factory.app.domain.CityBus;
import com.comdata.factory.app.domain.InterCityBus;
import com.comdata.factory.app.repository.BusRepository;
import com.comdata.factory.app.repository.CityBusRepository;
import com.comdata.factory.app.repository.InterCityBusRepository;
import com.comdata.factory.app.repository.ManufacturerRepository;
import com.comdata.factory.app.service.BusService;


/**
 * Service Implementation for managing Bus.
 */
@Service
@Transactional
public class BusServiceImpl implements BusService{

    private final Logger log = LoggerFactory.getLogger(BusServiceImpl.class);

    private final BusRepository busRepository;
    private final CityBusRepository cityBusRepository;
    private final InterCityBusRepository interCityBusRepository;
    private final ManufacturerRepository manufacturerRepository;

    public BusServiceImpl(BusRepository busRepository, CityBusRepository cityBusRepository,
			InterCityBusRepository interCityBusRepository, ManufacturerRepository manufacturerRepository) {
		super();
		this.busRepository = busRepository;
		this.cityBusRepository = cityBusRepository;
		this.interCityBusRepository = interCityBusRepository;
		this.manufacturerRepository = manufacturerRepository;
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
        bus.setManufacturer(manufacturerRepository.findOne(bus.getManufacturer().getId()));
        
    	
        if(bus instanceof CityBus) {
        	bus.setArea(CityBus.AREA);
        	return cityBusRepository.save((CityBus)bus);
        } else {
        	bus.setArea(InterCityBus.AREA);
        	return interCityBusRepository.save((InterCityBus)bus);
        }
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
        Bus bus = busRepository.findOne(id);
        if(bus == null) {
        	return null;
        }
        bus = cityBusRepository.findOne(id);
        if(bus != null) {
        	return bus;
        } else {
        	return interCityBusRepository.findOne(id);
        }
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

	@Override
	public List<Bus> findAll() {
        List<CityBus> allCityBuses= cityBusRepository.findAll();
        List<InterCityBus> allInterCityBuses = interCityBusRepository.findAll();
        List<Bus> allBuses = new ArrayList<>();
        allBuses.addAll(allCityBuses);
        allBuses.addAll(allInterCityBuses);
        return allBuses;
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

}
