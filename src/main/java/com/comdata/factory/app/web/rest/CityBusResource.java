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
import com.comdata.factory.app.domain.Bus;
import com.comdata.factory.app.domain.CityBus;
import com.comdata.factory.app.service.CityBusService;
import com.comdata.factory.app.web.rest.dto.BusDTO;
import com.comdata.factory.app.web.rest.util.HeaderUtil;
import com.comdata.factory.app.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing CityBus.
 */
@RestController
@RequestMapping("/api")
public class CityBusResource {

    private final Logger log = LoggerFactory.getLogger(CityBusResource.class);

    private static final String ENTITY_NAME = "cityBus";

    private final CityBusService cityBusService;

    public CityBusResource(CityBusService cityBusService) {
        this.cityBusService = cityBusService;
    }

    /**
     * POST  /city-buses : Create a new cityBus.
     *
     * @param cityBus the cityBus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cityBus, or with status 400 (Bad Request) if the cityBus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/city-buses")
    @Timed
    public ResponseEntity<CityBus> createCityBus(@RequestBody CityBus cityBus) throws URISyntaxException {
        log.debug("REST request to save CityBus : {}", cityBus);
        if (cityBus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cityBus cannot already have an ID")).body(null);
        }
        CityBus result = cityBusService.save(cityBus);
        return ResponseEntity.created(new URI("/api/city-buses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /city-buses : Updates an existing cityBus.
     *
     * @param cityBus the cityBus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cityBus,
     * or with status 400 (Bad Request) if the cityBus is not valid,
     * or with status 500 (Internal Server Error) if the cityBus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/city-buses")
    @Timed
    public ResponseEntity<CityBus> updateCityBus(@RequestBody CityBus cityBus) throws URISyntaxException {
        log.debug("REST request to update CityBus : {}", cityBus);
        if (cityBus.getId() == null) {
            return createCityBus(cityBus);
        }
        CityBus result = cityBusService.save(cityBus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cityBus.getId().toString()))
            .body(result);
    }

    
    
    
    
    /*
    
        @GetMapping("/cars")
    @Timed
    public ResponseEntity<List<CarDTO>> getAllCars(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Cars");
        Page<Car> page =  carService.findAll(pageable);
       List<Car> allCars = page.getContent();
        List<CarDTO> allDTOs = new ArrayList<>();
        for(Car car : allCars) {
        	allDTOs.add(new CarDTO(car));
        }
        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cars");
        
        return new ResponseEntity<>(allDTOs, headers, HttpStatus.OK);    
    }
    
    
    */
    
    /**
     * GET  /city-buses : get all the cityBuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cityBuses in body
     */
    @GetMapping("/city-buses")
    @Timed
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
    }

    /**
     * GET  /city-buses/:id : get the "id" cityBus.
     *
     * @param id the id of the cityBus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cityBus, or with status 404 (Not Found)
     */
    @GetMapping("/city-buses/{id}")
    @Timed
    public ResponseEntity<CityBus> getCityBus(@PathVariable Long id) {
        log.debug("REST request to get CityBus : {}", id);
        CityBus cityBus = cityBusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cityBus));
    }

    /**
     * DELETE  /city-buses/:id : delete the "id" cityBus.
     *
     * @param id the id of the cityBus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/city-buses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCityBus(@PathVariable Long id) {
        log.debug("REST request to delete CityBus : {}", id);
        cityBusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
