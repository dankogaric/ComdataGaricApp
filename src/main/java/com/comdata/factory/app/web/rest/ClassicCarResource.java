package com.comdata.factory.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.comdata.factory.app.domain.ClassicCar;

import com.comdata.factory.app.repository.ClassicCarRepository;
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
 * REST controller for managing ClassicCar.
 */
@RestController
@RequestMapping("/api")
public class ClassicCarResource {

    private final Logger log = LoggerFactory.getLogger(ClassicCarResource.class);

    private static final String ENTITY_NAME = "classicCar";

    private final ClassicCarRepository classicCarRepository;

    public ClassicCarResource(ClassicCarRepository classicCarRepository) {
        this.classicCarRepository = classicCarRepository;
    }

    /**
     * POST  /classic-cars : Create a new classicCar.
     *
     * @param classicCar the classicCar to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classicCar, or with status 400 (Bad Request) if the classicCar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/classic-cars")
    @Timed
    public ResponseEntity<ClassicCar> createClassicCar(@RequestBody ClassicCar classicCar) throws URISyntaxException {
        log.debug("REST request to save ClassicCar : {}", classicCar);
        if (classicCar.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new classicCar cannot already have an ID")).body(null);
        }
        ClassicCar result = classicCarRepository.save(classicCar);
        return ResponseEntity.created(new URI("/api/classic-cars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /classic-cars : Updates an existing classicCar.
     *
     * @param classicCar the classicCar to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classicCar,
     * or with status 400 (Bad Request) if the classicCar is not valid,
     * or with status 500 (Internal Server Error) if the classicCar couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/classic-cars")
    @Timed
    public ResponseEntity<ClassicCar> updateClassicCar(@RequestBody ClassicCar classicCar) throws URISyntaxException {
        log.debug("REST request to update ClassicCar : {}", classicCar);
        if (classicCar.getId() == null) {
            return createClassicCar(classicCar);
        }
        ClassicCar result = classicCarRepository.save(classicCar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classicCar.getId().toString()))
            .body(result);
    }

    /**
     * GET  /classic-cars : get all the classicCars.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of classicCars in body
     */
    @GetMapping("/classic-cars")
    @Timed
    public List<ClassicCar> getAllClassicCars() {
        log.debug("REST request to get all ClassicCars");
        return classicCarRepository.findAll();
        }

    /**
     * GET  /classic-cars/:id : get the "id" classicCar.
     *
     * @param id the id of the classicCar to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classicCar, or with status 404 (Not Found)
     */
    @GetMapping("/classic-cars/{id}")
    @Timed
    public ResponseEntity<ClassicCar> getClassicCar(@PathVariable Long id) {
        log.debug("REST request to get ClassicCar : {}", id);
        ClassicCar classicCar = classicCarRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classicCar));
    }

    /**
     * DELETE  /classic-cars/:id : delete the "id" classicCar.
     *
     * @param id the id of the classicCar to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/classic-cars/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassicCar(@PathVariable Long id) {
        log.debug("REST request to delete ClassicCar : {}", id);
        classicCarRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
