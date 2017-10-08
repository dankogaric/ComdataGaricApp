package com.comdata.factory.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.comdata.factory.app.domain.Bus;
import com.comdata.factory.app.domain.Car;
import com.comdata.factory.app.domain.Truck;
import com.comdata.factory.app.domain.Vehicle;
import com.comdata.factory.app.service.BusService;
import com.comdata.factory.app.service.CarService;
import com.comdata.factory.app.service.TruckService;
import com.comdata.factory.app.service.VehicleService;
import com.comdata.factory.app.web.rest.dto.CarDTO;
import com.comdata.factory.app.web.rest.dto.VehicleDTO;
import com.comdata.factory.app.web.rest.util.HeaderUtil;
import com.comdata.factory.app.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public VehicleResource(VehicleService vehicleService, CarService carService, BusService busService,
			TruckService truckService) {
		super();
		this.vehicleService = vehicleService;
		this.carService = carService;
		this.busService = busService;
		this.truckService = truckService;
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
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) throws URISyntaxException {
        log.debug("REST request to save Vehicle : {}", vehicle);
        if (vehicle.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vehicle cannot already have an ID")).body(null);
        }
        Vehicle result = vehicleService.save(vehicle);
        return ResponseEntity.created(new URI("/api/vehicles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
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
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody Vehicle vehicle) throws URISyntaxException {
        log.debug("REST request to update Vehicle : {}", vehicle);
        if (vehicle.getId() == null) {
            return createVehicle(vehicle);
        }
        Vehicle result = vehicleService.save(vehicle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicle.getId().toString()))
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
    public List<VehicleDTO> getAllVehicles() {
        log.debug("REST request to get a page of Vehicles");
        List<Car> allCars =  carService.findAll();
        List<Bus> allBuses =  busService.findAll();
        List<Truck> allTrucks =  truckService.findAll();
        List<VehicleDTO> vehiclesDTOs = new ArrayList<>();
        for(Car car : allCars) {
        	vehiclesDTOs.add(new VehicleDTO(car));
        }
        for(Bus bus : allBuses) {
        	vehiclesDTOs.add(new VehicleDTO(bus));
        }
        for(Truck truck : allTrucks) {
        	vehiclesDTOs.add(new VehicleDTO(truck));
        }
        return vehiclesDTOs;
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
