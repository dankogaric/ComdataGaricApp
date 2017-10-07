package com.comdata.factory.app.web.rest;

import com.comdata.factory.app.ComdataGaricApp;

import com.comdata.factory.app.domain.Bus;
import com.comdata.factory.app.repository.BusRepository;
import com.comdata.factory.app.service.BusService;
import com.comdata.factory.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BusResource REST controller.
 *
 * @see BusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComdataGaricApp.class)
public class BusResourceIntTest {

    private static final Integer DEFAULT_SEATS_SITTING = 1;
    private static final Integer UPDATED_SEATS_SITTING = 2;

    private static final Integer DEFAULT_SEATS_STANDING = 1;
    private static final Integer UPDATED_SEATS_STANDING = 2;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private BusService busService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBusMockMvc;

    private Bus bus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusResource busResource = new BusResource(busService);
        this.restBusMockMvc = MockMvcBuilders.standaloneSetup(busResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bus createEntity(EntityManager em) {
        Bus bus = new Bus()
            .seatsSitting(DEFAULT_SEATS_SITTING)
            .seatsStanding(DEFAULT_SEATS_STANDING);
        return bus;
    }

    @Before
    public void initTest() {
        bus = createEntity(em);
    }

    @Test
    @Transactional
    public void createBus() throws Exception {
        int databaseSizeBeforeCreate = busRepository.findAll().size();

        // Create the Bus
        restBusMockMvc.perform(post("/api/buses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bus)))
            .andExpect(status().isCreated());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeCreate + 1);
        Bus testBus = busList.get(busList.size() - 1);
        assertThat(testBus.getSeatsSitting()).isEqualTo(DEFAULT_SEATS_SITTING);
        assertThat(testBus.getSeatsStanding()).isEqualTo(DEFAULT_SEATS_STANDING);
    }

    @Test
    @Transactional
    public void createBusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = busRepository.findAll().size();

        // Create the Bus with an existing ID
        bus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusMockMvc.perform(post("/api/buses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bus)))
            .andExpect(status().isBadRequest());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBuses() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList
        restBusMockMvc.perform(get("/api/buses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bus.getId().intValue())))
            .andExpect(jsonPath("$.[*].seatsSitting").value(hasItem(DEFAULT_SEATS_SITTING)))
            .andExpect(jsonPath("$.[*].seatsStanding").value(hasItem(DEFAULT_SEATS_STANDING)));
    }

    @Test
    @Transactional
    public void getBus() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get the bus
        restBusMockMvc.perform(get("/api/buses/{id}", bus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bus.getId().intValue()))
            .andExpect(jsonPath("$.seatsSitting").value(DEFAULT_SEATS_SITTING))
            .andExpect(jsonPath("$.seatsStanding").value(DEFAULT_SEATS_STANDING));
    }

    @Test
    @Transactional
    public void getNonExistingBus() throws Exception {
        // Get the bus
        restBusMockMvc.perform(get("/api/buses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBus() throws Exception {
        // Initialize the database
        busService.save(bus);

        int databaseSizeBeforeUpdate = busRepository.findAll().size();

        // Update the bus
        Bus updatedBus = busRepository.findOne(bus.getId());
        updatedBus
            .seatsSitting(UPDATED_SEATS_SITTING)
            .seatsStanding(UPDATED_SEATS_STANDING);

        restBusMockMvc.perform(put("/api/buses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBus)))
            .andExpect(status().isOk());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
        Bus testBus = busList.get(busList.size() - 1);
        assertThat(testBus.getSeatsSitting()).isEqualTo(UPDATED_SEATS_SITTING);
        assertThat(testBus.getSeatsStanding()).isEqualTo(UPDATED_SEATS_STANDING);
    }

    @Test
    @Transactional
    public void updateNonExistingBus() throws Exception {
        int databaseSizeBeforeUpdate = busRepository.findAll().size();

        // Create the Bus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBusMockMvc.perform(put("/api/buses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bus)))
            .andExpect(status().isCreated());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBus() throws Exception {
        // Initialize the database
        busService.save(bus);

        int databaseSizeBeforeDelete = busRepository.findAll().size();

        // Get the bus
        restBusMockMvc.perform(delete("/api/buses/{id}", bus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bus.class);
        Bus bus1 = new Bus();
        bus1.setId(1L);
        Bus bus2 = new Bus();
        bus2.setId(bus1.getId());
        assertThat(bus1).isEqualTo(bus2);
        bus2.setId(2L);
        assertThat(bus1).isNotEqualTo(bus2);
        bus1.setId(null);
        assertThat(bus1).isNotEqualTo(bus2);
    }
}
