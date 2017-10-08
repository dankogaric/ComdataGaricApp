package com.comdata.factory.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.comdata.factory.app.domain.Car;
import com.comdata.factory.app.domain.Truck;
import com.comdata.factory.app.service.BusService;
import com.comdata.factory.app.service.TruckService;
import com.comdata.factory.app.web.rest.dto.BusDTO;
import com.comdata.factory.app.web.rest.dto.CarDTO;
import com.comdata.factory.app.web.rest.dto.TruckDTO;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Truck.
 */
@RestController
@RequestMapping("/api")
public class TruckResource {

    private final Logger log = LoggerFactory.getLogger(TruckResource.class);

    private static final String ENTITY_NAME = "truck";

    private final TruckService truckService;

    public TruckResource(TruckService truckService) {
        this.truckService = truckService;
    }

    /**
     * POST  /trucks : Create a new truck.
     *
     * @param truck the truck to create
     * @return the ResponseEntity with status 201 (Created) and with body the new truck, or with status 400 (Bad Request) if the truck has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trucks")
    @Timed
    public ResponseEntity<Truck> createTruck(@Valid @RequestBody TruckDTO dto) throws URISyntaxException {
        log.debug("REST request to save Truck : {}", dto);
        if (dto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new truck cannot already have an ID")).body(null);
        }
        Truck result = truckService.save(dto.convertToTruckEntity());
        return ResponseEntity.created(new URI("/api/trucks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trucks : Updates an existing truck.
     *
     * @param truck the truck to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated truck,
     * or with status 400 (Bad Request) if the truck is not valid,
     * or with status 500 (Internal Server Error) if the truck couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trucks")
    @Timed
    public ResponseEntity<TruckDTO> updateTruck(@Valid @RequestBody TruckDTO truck) throws URISyntaxException {
        log.debug("REST request to update Truck : {}", truck);
        if (truck.getId() == null) {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "You can only update an existing truck.")).body(null);
        }
        Truck result = truckService.save(truck.convertToTruckEntity());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, truck.getId().toString()))
            .body(new TruckDTO(result));
    }

    /**
     * GET  /trucks : get all the trucks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trucks in body
     */
    @GetMapping("/trucks")
    @Timed
    public List<TruckDTO> getAllTrucks() {
        log.debug("REST request to get a page of Trucks");
        List<Truck> allTrucks =  truckService.findAll();
        List<TruckDTO> allDTOs = new ArrayList<>();
        for(Truck truck: allTrucks) {
        	allDTOs.add(new TruckDTO(truck));
        }
        return allDTOs;
    }

    /**
     * GET  /trucks/:id : get the "id" truck.
     *
     * @param id the id of the truck to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the truck, or with status 404 (Not Found)
     */
    @GetMapping("/trucks/{id}")
    @Timed
    public ResponseEntity<TruckDTO> getTruck(@PathVariable Long id) {
        log.debug("REST request to get Truck : {}", id);
        Truck truck = truckService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new TruckDTO(truck)));
    }

    /**
     * DELETE  /trucks/:id : delete the "id" truck.
     *
     * @param id the id of the truck to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trucks/{id}")
    @Timed
    public ResponseEntity<Void> deleteTruck(@PathVariable Long id) {
        log.debug("REST request to delete Truck : {}", id);
        truckService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
