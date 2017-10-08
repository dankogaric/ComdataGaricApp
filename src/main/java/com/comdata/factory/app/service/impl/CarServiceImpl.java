package com.comdata.factory.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdata.factory.app.domain.Cabrio;
import com.comdata.factory.app.domain.Car;
import com.comdata.factory.app.domain.ClassicCar;
import com.comdata.factory.app.repository.AdditionalEquipmentRepository;
import com.comdata.factory.app.repository.CabrioRepository;
import com.comdata.factory.app.repository.CarRepository;
import com.comdata.factory.app.repository.ClassicCarRepository;
import com.comdata.factory.app.repository.ManufacturerRepository;
import com.comdata.factory.app.service.CarService;

/**
 * Service Implementation for managing Car.
 */
@Service
@Transactional
public class CarServiceImpl implements CarService{

    private final Logger log = LoggerFactory.getLogger(CarServiceImpl.class);

    private final CabrioRepository cabrioRepository;
    private final ClassicCarRepository classicCarRepository;
    private final CarRepository carRepository;
    private final AdditionalEquipmentRepository additionalEquipmentRepository;
    private final ManufacturerRepository manufacturerRepository;

  


	public CarServiceImpl(CabrioRepository cabrioRepository, ClassicCarRepository classicCarRepository,
			CarRepository carRepository, AdditionalEquipmentRepository additionalEquipmentRepository,
			ManufacturerRepository manufacturerRepository) {
		super();
		this.cabrioRepository = cabrioRepository;
		this.classicCarRepository = classicCarRepository;
		this.carRepository = carRepository;
		this.additionalEquipmentRepository = additionalEquipmentRepository;
		this.manufacturerRepository = manufacturerRepository;
	}

	/**
     * Save a car.
     *
     * @param car the entity to save
     * @return the persisted entity
     */
    @Override
    public Car save(Car car) {
        log.debug("Request to save Car : {}", car);
        
    	if(car.getAddEq().getId() == null) {
    		car.setAddEq(additionalEquipmentRepository.save(car.getAddEq()));
    	}
    	
    	car.setManufacturer(manufacturerRepository.findOne(car.getManufacturer().getId()));

    	
        if(car instanceof ClassicCar) {
        	return classicCarRepository.save((ClassicCar)car);
        } else {
        	return cabrioRepository.save((Cabrio)car);
        }
        
    }

    /**
     *  Get all the cars.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Car> findAll() {
        log.debug("Request to get all Cars");
        List<ClassicCar> allCarsClassic = classicCarRepository.findAll();
        List<Cabrio> allCarsCabrio = cabrioRepository.findAll();
        List<Car> allCars = new ArrayList<>();
        allCars.addAll(allCarsClassic);
        allCars.addAll(allCarsCabrio);
        return allCars;
    }

    /**
     *  Get one car by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Car findOne(Long id) {
        log.debug("Request to get Car : {}", id);
        Car car = carRepository.findOne(id);
        if(car == null) {
        	return null;
        }
        car = classicCarRepository.findOne(id);
        if(car != null) {
        	return car;
        } else {
        	return cabrioRepository.findOne(id);
        }
    }

    /**
     *  Delete the  car by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Car : {}", id);
        carRepository.delete(id);
    }
}
