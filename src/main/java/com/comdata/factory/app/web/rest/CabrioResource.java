package com.comdata.factory.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.comdata.factory.app.domain.Cabrio;

import com.comdata.factory.app.repository.CabrioRepository;
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
 * REST controller for managing Cabrio.
 */
@RestController
@RequestMapping("/api")
public class CabrioResource {

    private final Logger log = LoggerFactory.getLogger(CabrioResource.class);

    private static final String ENTITY_NAME = "cabrio";

    private final CabrioRepository cabrioRepository;

    public CabrioResource(CabrioRepository cabrioRepository) {
        this.cabrioRepository = cabrioRepository;
    }

    /**
     * POST  /cabrios : Create a new cabrio.
     *
     * @param cabrio the cabrio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cabrio, or with status 400 (Bad Request) if the cabrio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cabrios")
    @Timed
    public ResponseEntity<Cabrio> createCabrio(@RequestBody Cabrio cabrio) throws URISyntaxException {
        log.debug("REST request to save Cabrio : {}", cabrio);
        if (cabrio.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cabrio cannot already have an ID")).body(null);
        }
        Cabrio result = cabrioRepository.save(cabrio);
        return ResponseEntity.created(new URI("/api/cabrios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cabrios : Updates an existing cabrio.
     *
     * @param cabrio the cabrio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cabrio,
     * or with status 400 (Bad Request) if the cabrio is not valid,
     * or with status 500 (Internal Server Error) if the cabrio couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cabrios")
    @Timed
    public ResponseEntity<Cabrio> updateCabrio(@RequestBody Cabrio cabrio) throws URISyntaxException {
        log.debug("REST request to update Cabrio : {}", cabrio);
        if (cabrio.getId() == null) {
            return createCabrio(cabrio);
        }
        Cabrio result = cabrioRepository.save(cabrio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cabrio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cabrios : get all the cabrios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cabrios in body
     */
    @GetMapping("/cabrios")
    @Timed
    public List<Cabrio> getAllCabrios() {
        log.debug("REST request to get all Cabrios");
        return cabrioRepository.findAll();
        }

    /**
     * GET  /cabrios/:id : get the "id" cabrio.
     *
     * @param id the id of the cabrio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cabrio, or with status 404 (Not Found)
     */
    @GetMapping("/cabrios/{id}")
    @Timed
    public ResponseEntity<Cabrio> getCabrio(@PathVariable Long id) {
        log.debug("REST request to get Cabrio : {}", id);
        Cabrio cabrio = cabrioRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cabrio));
    }

    /**
     * DELETE  /cabrios/:id : delete the "id" cabrio.
     *
     * @param id the id of the cabrio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cabrios/{id}")
    @Timed
    public ResponseEntity<Void> deleteCabrio(@PathVariable Long id) {
        log.debug("REST request to delete Cabrio : {}", id);
        cabrioRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
