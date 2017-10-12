package com.comdata.factory.app.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdata.factory.app.domain.Bus;
import com.comdata.factory.app.domain.Parking;
import com.comdata.factory.app.domain.Truck;
import com.comdata.factory.app.domain.Vehicle;
import com.comdata.factory.app.repository.ParkingRepository;
import com.comdata.factory.app.repository.VehicleRepository;
import com.comdata.factory.app.service.ParkingService;


/**
 * Service Implementation for managing Vehicle.
 */
@Service
@Transactional
public class ParkingServiceImpl implements ParkingService {

    private final Logger log = LoggerFactory.getLogger(ParkingServiceImpl.class);

    private final VehicleRepository vehicleRepository;
    private final ParkingRepository parkingRepository;

    
    

    

	public ParkingServiceImpl(VehicleRepository vehicleRepository, ParkingRepository parkingRepository) {
		super();
		this.vehicleRepository = vehicleRepository;
		this.parkingRepository = parkingRepository;
	}

	@Override
	public Parking save(Parking parking) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Parking findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Page<Parking> findAll(Pageable pageable) {
		return parkingRepository.findAll(pageable);
	}



	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean park(Vehicle result) {
		List<Parking> parkings = null;
		if((result instanceof Truck || result instanceof Bus )) {
			parkings = parkingRepository.findByAreaGreaterThanOrderByAreaAsc(30);
			for (Parking parking : parkings) {
				if (parking.getRestArea() >= result.getArea()) {
					result.setParking(parking);
					parking.setRestArea(parking.getRestArea() - result.getArea());
					parkingRepository.save(parking);
					return true;
				}
			}
		} else {
			parkings = parkingRepository.findAllByOrderByAreaAsc();
			for (Parking parking : parkings) {
				if (parking.getRestArea() >= result.getArea()) {
					result.setParking(parking);
					parking.setRestArea(parking.getRestArea() - result.getArea());
					parkingRepository.save(parking);
					return true;
				}
			}
		}
		return false;
		
	}

	@Override
	public Page<Parking> findByParkingId(Integer parkingId, Pageable pageable) {
		
		return null;
	}
}
