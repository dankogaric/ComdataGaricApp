package com.comdata.factory.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.comdata.factory.app.domain.AdditionalEquipment;

import com.comdata.factory.app.repository.AdditionalEquipmentRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing AdditionalEquipment.
 */
@RestController
@RequestMapping("/api")
public class AdditionalEquipmentResource {

    private final Logger log = LoggerFactory.getLogger(AdditionalEquipmentResource.class);

    private static final String ENTITY_NAME = "additionalEquipment";

    private final AdditionalEquipmentRepository additionalEquipmentRepository;

    public AdditionalEquipmentResource(AdditionalEquipmentRepository additionalEquipmentRepository) {
        this.additionalEquipmentRepository = additionalEquipmentRepository;
    }

    /**
     * POST  /additional-equipments : Create a new additionalEquipment.
     *
     * @param additionalEquipment the additionalEquipment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new additionalEquipment, or with status 400 (Bad Request) if the additionalEquipment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/additional-equipments")
    @Timed
    public ResponseEntity<AdditionalEquipment> createAdditionalEquipment(@RequestBody AdditionalEquipment additionalEquipment) throws URISyntaxException {
        log.debug("REST request to save AdditionalEquipment : {}", additionalEquipment);
        if (additionalEquipment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new additionalEquipment cannot already have an ID")).body(null);
        }
        AdditionalEquipment result = additionalEquipmentRepository.save(additionalEquipment);
        return ResponseEntity.created(new URI("/api/additional-equipments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /additional-equipments : Updates an existing additionalEquipment.
     *
     * @param additionalEquipment the additionalEquipment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated additionalEquipment,
     * or with status 400 (Bad Request) if the additionalEquipment is not valid,
     * or with status 500 (Internal Server Error) if the additionalEquipment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/additional-equipments")
    @Timed
    public ResponseEntity<AdditionalEquipment> updateAdditionalEquipment(@RequestBody AdditionalEquipment additionalEquipment) throws URISyntaxException {
        log.debug("REST request to update AdditionalEquipment : {}", additionalEquipment);
        if (additionalEquipment.getId() == null) {
            return createAdditionalEquipment(additionalEquipment);
        }
        AdditionalEquipment result = additionalEquipmentRepository.save(additionalEquipment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, additionalEquipment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /additional-equipments : get all the additionalEquipments.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of additionalEquipments in body
     */
    @GetMapping("/additional-equipments")
    @Timed
    public List<AdditionalEquipment> getAllAdditionalEquipments(@RequestParam(required = false) String filter) {
        if ("car-is-null".equals(filter)) {
            log.debug("REST request to get all AdditionalEquipments where car is null");
            return StreamSupport
                .stream(additionalEquipmentRepository.findAll().spliterator(), false)
               
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all AdditionalEquipments");
        return additionalEquipmentRepository.findAll();
        }

    /**
     * GET  /additional-equipments/:id : get the "id" additionalEquipment.
     *
     * @param id the id of the additionalEquipment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the additionalEquipment, or with status 404 (Not Found)
     */
    @GetMapping("/additional-equipments/{id}")
    @Timed
    public ResponseEntity<AdditionalEquipment> getAdditionalEquipment(@PathVariable Long id) {
        log.debug("REST request to get AdditionalEquipment : {}", id);
        AdditionalEquipment additionalEquipment = additionalEquipmentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(additionalEquipment));
    }

    /**
     * DELETE  /additional-equipments/:id : delete the "id" additionalEquipment.
     *
     * @param id the id of the additionalEquipment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/additional-equipments/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdditionalEquipment(@PathVariable Long id) {
        log.debug("REST request to delete AdditionalEquipment : {}", id);
        additionalEquipmentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
