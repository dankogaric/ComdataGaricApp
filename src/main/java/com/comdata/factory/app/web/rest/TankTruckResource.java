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
import com.comdata.factory.app.domain.TankTruck;
import com.comdata.factory.app.service.TankTruckService;
import com.comdata.factory.app.web.rest.dto.TruckDTO;
import com.comdata.factory.app.web.rest.util.HeaderUtil;
import com.comdata.factory.app.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing TankTruck.
 */
@RestController
@RequestMapping("/api")
public class TankTruckResource {

    private final Logger log = LoggerFactory.getLogger(TankTruckResource.class);

    private static final String ENTITY_NAME = "tankTruck";

    private final TankTruckService tankTruckService;

    public TankTruckResource(TankTruckService tankTruckService) {
        this.tankTruckService = tankTruckService;
    }

    /**
     * POST  /tank-trucks : Create a new tankTruck.
     *
     * @param tankTruck the tankTruck to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tankTruck, or with status 400 (Bad Request) if the tankTruck has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tank-trucks")
    @Timed
    public ResponseEntity<TankTruck> createTankTruck(@RequestBody TankTruck tankTruck) throws URISyntaxException {
        log.debug("REST request to save TankTruck : {}", tankTruck);
        if (tankTruck.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tankTruck cannot already have an ID")).body(null);
        }
        TankTruck result = tankTruckService.save(tankTruck);
        return ResponseEntity.created(new URI("/api/tank-trucks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tank-trucks : Updates an existing tankTruck.
     *
     * @param tankTruck the tankTruck to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tankTruck,
     * or with status 400 (Bad Request) if the tankTruck is not valid,
     * or with status 500 (Internal Server Error) if the tankTruck couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tank-trucks")
    @Timed
    public ResponseEntity<TankTruck> updateTankTruck(@RequestBody TankTruck tankTruck) throws URISyntaxException {
        log.debug("REST request to update TankTruck : {}", tankTruck);
        if (tankTruck.getId() == null) {
            return createTankTruck(tankTruck);
        }
        TankTruck result = tankTruckService.save(tankTruck);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tankTruck.getId().toString()))
            .body(result);
    }

    
    /*
    
        public ResponseEntity<List<BusDTO>> getAllCityBuses(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CityBuses");
        Page<CityBus> page = cityBusService.findAllPage(pageable);
        List<CityBus> buses = page.getContent();
        List<BusDTO> allDTOs = new ArrayList<>();
        for(Bus bus : buses) {
        	allDTOs.add(new BusDTO(bus));
        }
        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/city-buses");
        
        return new ResponseEntity<>(allDTOs, headers, HttpStatus.OK);
    
    */
    /**
     * GET  /tank-trucks : get all the tankTrucks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tankTrucks in body
     */
    @GetMapping("/tank-trucks")
    @Timed
    public ResponseEntity<List<TruckDTO>> getAllTankTrucks(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TankTrucks");
        Page<TankTruck> page =  tankTruckService.findAll(pageable);
        List<TankTruck> trucks = page.getContent();
        
        List<TruckDTO> allDTOs = new ArrayList<>();
        for(TankTruck tankTruck : trucks) {
        	allDTOs.add(new TruckDTO(tankTruck));
        }
        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tank-trucks");
        
        return new ResponseEntity<>(allDTOs, headers, HttpStatus.OK);
    }

    /**
     * GET  /tank-trucks/:id : get the "id" tankTruck.
     *
     * @param id the id of the tankTruck to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tankTruck, or with status 404 (Not Found)
     */
    @GetMapping("/tank-trucks/{id}")
    @Timed
    public ResponseEntity<TankTruck> getTankTruck(@PathVariable Long id) {
        log.debug("REST request to get TankTruck : {}", id);
        TankTruck tankTruck = tankTruckService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tankTruck));
    }

    /**
     * DELETE  /tank-trucks/:id : delete the "id" tankTruck.
     *
     * @param id the id of the tankTruck to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tank-trucks/{id}")
    @Timed
    public ResponseEntity<Void> deleteTankTruck(@PathVariable Long id) {
        log.debug("REST request to delete TankTruck : {}", id);
        tankTruckService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
