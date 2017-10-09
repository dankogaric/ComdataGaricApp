package com.comdata.factory.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdata.factory.app.domain.TankTruck;
import com.comdata.factory.app.domain.Truck;
import com.comdata.factory.app.domain.TructorTruck;
import com.comdata.factory.app.repository.ManufacturerRepository;
import com.comdata.factory.app.repository.TankTruckRepository;
import com.comdata.factory.app.repository.TruckRepository;
import com.comdata.factory.app.repository.TructorTruckRepository;
import com.comdata.factory.app.service.TruckService;


/**
 * Service Implementation for managing Truck.
 */
@Service
@Transactional
public class TruckServiceImpl implements TruckService{

    private final Logger log = LoggerFactory.getLogger(TruckServiceImpl.class);

    private final TruckRepository truckRepository;
    private final TructorTruckRepository tructorTruckRepository;
    private final TankTruckRepository tankTruckRepository;
    private final ManufacturerRepository manufacturerRepository;

    
	public TruckServiceImpl(TruckRepository truckRepository, TructorTruckRepository tructorTruckRepository,
			TankTruckRepository tankTruckRepository, ManufacturerRepository manufacturerRepository) {
		super();
		this.truckRepository = truckRepository;
		this.tructorTruckRepository = tructorTruckRepository;
		this.tankTruckRepository = tankTruckRepository;
		this.manufacturerRepository = manufacturerRepository;
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
        truck.setManufacturer(manufacturerRepository.findOne(truck.getManufacturer().getId()));

    	
        if(truck instanceof TructorTruck) {
        	truck.setArea(TructorTruck.AREA);
        	return tructorTruckRepository.save((TructorTruck)truck);
        } else {
        	truck.setArea(TankTruck.AREA);
        	return tankTruckRepository.save((TankTruck)truck);
        }
    }

    /**
     *  Get all the trucks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Truck> findAll() {
        log.debug("Request to get all Trucks");
        List<TructorTruck> allTructorTrucks = tructorTruckRepository.findAll();
        List<TankTruck> allTankTrucks = tankTruckRepository.findAll();
        List<Truck> allTrucks = new ArrayList<>();
        allTrucks.addAll(allTructorTrucks);
        allTrucks.addAll(allTankTrucks);
        return allTrucks;
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
        Truck truck = truckRepository.findOne(id);
        if(truck == null) {
        	return null;
        }
        truck = tructorTruckRepository.findOne(id);
        if(truck != null) {
        	return truck;
        } else {
        	return tankTruckRepository.findOne(id);
        }
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

	@Override
	public Page<Truck> findAll(Pageable pageable) {
		return truckRepository.findAll(pageable);
	}
}
