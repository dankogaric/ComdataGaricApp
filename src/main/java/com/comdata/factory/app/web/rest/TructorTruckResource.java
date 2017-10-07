package com.comdata.factory.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.comdata.factory.app.domain.TructorTruck;

import com.comdata.factory.app.repository.TructorTruckRepository;
import com.comdata.factory.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TructorTruck.
 */
@RestController
@RequestMapping("/api")
public class TructorTruckResource {

    private final Logger log = LoggerFactory.getLogger(TructorTruckResource.class);

    private static final String ENTITY_NAME = "tructorTruck";

    private final TructorTruckRepository tructorTruckRepository;

    public TructorTruckResource(TructorTruckRepository tructorTruckRepository) {
        this.tructorTruckRepository = tructorTruckRepository;
    }

    /**
     * POST  /tructor-trucks : Create a new tructorTruck.
     *
     * @param tructorTruck the tructorTruck to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tructorTruck, or with status 400 (Bad Request) if the tructorTruck has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tructor-trucks")
    @Timed
    public ResponseEntity<TructorTruck> createTructorTruck(@RequestBody TructorTruck tructorTruck) throws URISyntaxException {
        log.debug("REST request to save TructorTruck : {}", tructorTruck);
        if (tructorTruck.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tructorTruck cannot already have an ID")).body(null);
        }
        TructorTruck result = tructorTruckRepository.save(tructorTruck);
        return ResponseEntity.created(new URI("/api/tructor-trucks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tructor-trucks : Updates an existing tructorTruck.
     *
     * @param tructorTruck the tructorTruck to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tructorTruck,
     * or with status 400 (Bad Request) if the tructorTruck is not valid,
     * or with status 500 (Internal Server Error) if the tructorTruck couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tructor-trucks")
    @Timed
    public ResponseEntity<TructorTruck> updateTructorTruck(@RequestBody TructorTruck tructorTruck) throws URISyntaxException {
        log.debug("REST request to update TructorTruck : {}", tructorTruck);
        if (tructorTruck.getId() == null) {
            return createTructorTruck(tructorTruck);
        }
        TructorTruck result = tructorTruckRepository.save(tructorTruck);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tructorTruck.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tructor-trucks : get all the tructorTrucks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tructorTrucks in body
     */
    @GetMapping("/tructor-trucks")
    @Timed
    public List<TructorTruck> getAllTructorTrucks() {
        log.debug("REST request to get all TructorTrucks");
        return tructorTruckRepository.findAll();
        }

    /**
     * GET  /tructor-trucks/:id : get the "id" tructorTruck.
     *
     * @param id the id of the tructorTruck to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tructorTruck, or with status 404 (Not Found)
     */
    @GetMapping("/tructor-trucks/{id}")
    @Timed
    public ResponseEntity<TructorTruck> getTructorTruck(@PathVariable Long id) {
        log.debug("REST request to get TructorTruck : {}", id);
        TructorTruck tructorTruck = tructorTruckRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tructorTruck));
    }

    /**
     * DELETE  /tructor-trucks/:id : delete the "id" tructorTruck.
     *
     * @param id the id of the tructorTruck to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tructor-trucks/{id}")
    @Timed
    public ResponseEntity<Void> deleteTructorTruck(@PathVariable Long id) {
        log.debug("REST request to delete TructorTruck : {}", id);
        tructorTruckRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
