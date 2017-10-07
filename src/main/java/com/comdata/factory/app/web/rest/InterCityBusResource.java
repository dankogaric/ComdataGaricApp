package com.comdata.factory.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.comdata.factory.app.domain.InterCityBus;

import com.comdata.factory.app.repository.InterCityBusRepository;
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
 * REST controller for managing InterCityBus.
 */
@RestController
@RequestMapping("/api")
public class InterCityBusResource {

    private final Logger log = LoggerFactory.getLogger(InterCityBusResource.class);

    private static final String ENTITY_NAME = "interCityBus";

    private final InterCityBusRepository interCityBusRepository;

    public InterCityBusResource(InterCityBusRepository interCityBusRepository) {
        this.interCityBusRepository = interCityBusRepository;
    }

    /**
     * POST  /inter-city-buses : Create a new interCityBus.
     *
     * @param interCityBus the interCityBus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new interCityBus, or with status 400 (Bad Request) if the interCityBus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inter-city-buses")
    @Timed
    public ResponseEntity<InterCityBus> createInterCityBus(@RequestBody InterCityBus interCityBus) throws URISyntaxException {
        log.debug("REST request to save InterCityBus : {}", interCityBus);
        if (interCityBus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new interCityBus cannot already have an ID")).body(null);
        }
        InterCityBus result = interCityBusRepository.save(interCityBus);
        return ResponseEntity.created(new URI("/api/inter-city-buses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inter-city-buses : Updates an existing interCityBus.
     *
     * @param interCityBus the interCityBus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated interCityBus,
     * or with status 400 (Bad Request) if the interCityBus is not valid,
     * or with status 500 (Internal Server Error) if the interCityBus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inter-city-buses")
    @Timed
    public ResponseEntity<InterCityBus> updateInterCityBus(@RequestBody InterCityBus interCityBus) throws URISyntaxException {
        log.debug("REST request to update InterCityBus : {}", interCityBus);
        if (interCityBus.getId() == null) {
            return createInterCityBus(interCityBus);
        }
        InterCityBus result = interCityBusRepository.save(interCityBus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, interCityBus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inter-city-buses : get all the interCityBuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of interCityBuses in body
     */
    @GetMapping("/inter-city-buses")
    @Timed
    public List<InterCityBus> getAllInterCityBuses() {
        log.debug("REST request to get all InterCityBuses");
        return interCityBusRepository.findAll();
        }

    /**
     * GET  /inter-city-buses/:id : get the "id" interCityBus.
     *
     * @param id the id of the interCityBus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the interCityBus, or with status 404 (Not Found)
     */
    @GetMapping("/inter-city-buses/{id}")
    @Timed
    public ResponseEntity<InterCityBus> getInterCityBus(@PathVariable Long id) {
        log.debug("REST request to get InterCityBus : {}", id);
        InterCityBus interCityBus = interCityBusRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(interCityBus));
    }

    /**
     * DELETE  /inter-city-buses/:id : delete the "id" interCityBus.
     *
     * @param id the id of the interCityBus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inter-city-buses/{id}")
    @Timed
    public ResponseEntity<Void> deleteInterCityBus(@PathVariable Long id) {
        log.debug("REST request to delete InterCityBus : {}", id);
        interCityBusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
