package com.comdata.factory.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.comdata.factory.app.domain.AdditionalEquipment;
import com.comdata.factory.app.domain.Bus;
import com.comdata.factory.app.domain.Cabrio;
import com.comdata.factory.app.domain.Car;
import com.comdata.factory.app.domain.CityBus;
import com.comdata.factory.app.domain.ClassicCar;
import com.comdata.factory.app.domain.InterCityBus;
import com.comdata.factory.app.domain.TankTruck;
import com.comdata.factory.app.domain.Truck;
import com.comdata.factory.app.domain.TructorTruck;
import com.comdata.factory.app.domain.Vehicle;
import com.comdata.factory.app.domain.enums.VehicleType;
import com.comdata.factory.app.service.BusService;
import com.comdata.factory.app.service.CarService;
import com.comdata.factory.app.service.ParkingService;
import com.comdata.factory.app.service.TruckService;
import com.comdata.factory.app.service.VehicleService;
import com.comdata.factory.app.web.rest.dto.CreateVehicleDTO;
import com.comdata.factory.app.web.rest.dto.VehicleDTO;
import com.comdata.factory.app.web.rest.util.HeaderUtil;
import com.comdata.factory.app.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Vehicle.
 */
@RestController
@RequestMapping("/api")
public class VehicleResource {

    private final Logger log = LoggerFactory.getLogger(VehicleResource.class);

    private static final String ENTITY_NAME = "vehicle";

    private final VehicleService vehicleService;
    private final CarService carService;
    private final BusService busService;
    private final TruckService truckService;
    private final ParkingService parkingService;



	public VehicleResource(VehicleService vehicleService, CarService carService, BusService busService,
			TruckService truckService, ParkingService parkingService) {
		super();
		this.vehicleService = vehicleService;
		this.carService = carService;
		this.busService = busService;
		this.truckService = truckService;
		this.parkingService = parkingService;
	}

	/**
     * POST  /vehicles : Create a new vehicle.
     *
     * @param vehicle the vehicle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehicle, or with status 400 (Bad Request) if the vehicle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicles")
    @Timed
    public ResponseEntity<Vehicle> createVehicle(@RequestBody CreateVehicleDTO dto) throws URISyntaxException {
        log.debug("REST request to save Vehicle : {}", dto);
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vehicle cannot already have an ID")).body(null);
        }
        Vehicle result = null;
        if (dto.getVehicleType() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "badrequest", "Vehicle type has to be specified!")).body(null);
        }
        
        Vehicle vehicle = convertToEntity(dto);

        boolean isParked = parkingService.park(vehicle);
        
        if (!isParked) {
        	return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "no parking space", "Vehicle type has to be specified!")).body(null);
        }
        
        if(dto.getVehicleType().equals(VehicleType.CLASSIC_CAR.toString()) || dto.getVehicleType().equals(VehicleType.CABRIO.toString())) {
        	result = carService.save((Car)vehicle);
        	
        } else if (dto.getVehicleType().equals(VehicleType.CITY_BUS.toString()) || dto.getVehicleType().equals(VehicleType.INTERCITY_BUS.toString())) {
        	result = busService.save((Bus)vehicle);
        	
        } else if (dto.getVehicleType().equals(VehicleType.TRUCTOR_TRUCK.toString()) || dto.getVehicleType().equals(VehicleType.TANK_TRUCK.toString()) ) {
        	result = truckService.save((Truck)vehicle);
        	
        }
        
        return ResponseEntity.created(new URI("/api/vehicles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
	private Vehicle convertToEntity(CreateVehicleDTO dto) {
    	Vehicle returnValue = null;
    	
        if(dto.getVehicleType().equals(VehicleType.CLASSIC_CAR.toString())) {
    		AdditionalEquipment addEq = new AdditionalEquipment();
			addEq.setHasAbs(dto.getHasAbs());
			addEq.setHasAluWheels(dto.getHasAluWheels());
			addEq.setHasEsp(dto.getHasEsp());
			addEq.setHasGlassRoof(dto.getHasGlassRoof());
			
			
        	returnValue = new ClassicCar(dto.getId(), dto.getColor(), ClassicCar.AREA, dto.getManufacturer(), dto.getParking(), VehicleType.CLASSIC_CAR, 
        								dto.getSeatsNumber(), addEq, dto.getRoofTopCapacity());
        	
        	
        	
        } else if (dto.getVehicleType().equals(VehicleType.CABRIO.toString())) {
    		AdditionalEquipment addEq = new AdditionalEquipment();
			addEq.setHasAbs(dto.getHasAbs());
			addEq.setHasAluWheels(dto.getHasAluWheels());
			addEq.setHasEsp(dto.getHasEsp());
			addEq.setHasGlassRoof(dto.getHasGlassRoof());
			
			
        	returnValue = new Cabrio(dto.getId(), dto.getColor(), Cabrio.AREA, dto.getManufacturer(), dto.getParking(), VehicleType.CABRIO, 
        								dto.getSeatsNumber(), addEq, dto.getHasRemovableRoof());
        	
        	
        } else if (dto.getVehicleType().equals(VehicleType.CITY_BUS.toString())) {
        	returnValue = new CityBus(dto.getId(), dto.getColor(), CityBus.AREA, dto.getManufacturer(), dto.getParking(), VehicleType.CITY_BUS, 
				dto.getSeatsSitting(), dto.getSeatsStanding(), dto.getHasWhrist());
        
        	
        } else if (dto.getVehicleType().equals(VehicleType.INTERCITY_BUS.toString())) {
        	returnValue = new InterCityBus(dto.getId(), dto.getColor(),InterCityBus.AREA, dto.getManufacturer(), dto.getParking(), VehicleType.INTERCITY_BUS, 
    				dto.getSeatsSitting(), dto.getSeatsStanding(), dto.getTrunkCapacity());
        	
        } else if (dto.getVehicleType().equals(VehicleType.TRUCTOR_TRUCK.toString())) {
        	returnValue = new TructorTruck(dto.getId(), dto.getColor(),TructorTruck.AREA, dto.getManufacturer(), dto.getParking(), VehicleType.TRUCTOR_TRUCK, 
        									dto.getNumberOfAxles(), dto.getHorsePower());
        	
        } else {
        	returnValue = new TankTruck(dto.getId(), dto.getColor(),TankTruck.AREA, dto.getManufacturer(), dto.getParking(), VehicleType.TANK_TRUCK, 
					dto.getNumberOfAxles(), dto.getTankCapacity());
        }

        	
        	return returnValue;
    }

    /**
     * PUT  /vehicles : Updates an existing vehicle.
     *
     * @param vehicle the vehicle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehicle,
     * or with status 400 (Bad Request) if the vehicle is not valid,
     * or with status 500 (Internal Server Error) if the vehicle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicles")
    @Timed
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody CreateVehicleDTO dto) throws URISyntaxException {
        Vehicle result = null;
        log.debug("REST request to update Vehicle : {}", dto);
        if (dto.getVehicleType() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "badrequest", "Vehicle type has to be specified!")).body(null);
        }
        
        Vehicle vehicle = convertToEntity(dto);
        
        if(dto.getVehicleType().equals(VehicleType.CLASSIC_CAR.toString()) || dto.getVehicleType().equals(VehicleType.CABRIO.toString())) {
        	result = carService.save((Car)vehicle);
        	
        } else if (dto.getVehicleType().equals(VehicleType.CITY_BUS.toString()) || dto.getVehicleType().equals(VehicleType.INTERCITY_BUS.toString())) {
        	result = busService.save((Bus)vehicle);
        	
        } else if (dto.getVehicleType().equals(VehicleType.TRUCTOR_TRUCK.toString()) || dto.getVehicleType().equals(VehicleType.TANK_TRUCK.toString()) ) {
        	result = truckService.save((Truck)vehicle);
        	
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    
    
    
    /**
     * GET  /vehicles : get all the vehicles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vehicles in body
     */
    @GetMapping("/vehicles")
    @Timed
	public ResponseEntity<List<VehicleDTO>> getAllVehicles(@ApiParam Pageable pageable) {
		log.debug("REST request to get a page of Vehicles");
		Page<Vehicle> page = vehicleService.findAll(pageable);
		List<VehicleDTO> allDTOs = new ArrayList<>();
		List<Vehicle> allVehicles = page.getContent();

		for (Vehicle vehicle : allVehicles) {
			allDTOs.add(new VehicleDTO(vehicle));
		}

		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cars");

		return new ResponseEntity<>(allDTOs, headers, HttpStatus.OK);
	}

    /**
     * GET  /vehicles/:id : get the "id" vehicle.
     *
     * @param id the id of the vehicle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehicle, or with status 404 (Not Found)
     */
    @GetMapping("/vehicles/{id}")
    @Timed
    public ResponseEntity<Vehicle> getVehicle(@PathVariable Long id) {
        log.debug("REST request to get Vehicle : {}", id);
        Vehicle vehicle = vehicleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehicle));
    }

    /**
     * DELETE  /vehicles/:id : delete the "id" vehicle.
     *
     * @param id the id of the vehicle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicles/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        log.debug("REST request to delete Vehicle : {}", id);
        vehicleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
