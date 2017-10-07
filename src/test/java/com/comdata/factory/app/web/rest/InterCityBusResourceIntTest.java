package com.comdata.factory.app.web.rest;

import com.comdata.factory.app.ComdataGaricApp;

import com.comdata.factory.app.domain.InterCityBus;
import com.comdata.factory.app.repository.InterCityBusRepository;
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
 * Test class for the InterCityBusResource REST controller.
 *
 * @see InterCityBusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComdataGaricApp.class)
public class InterCityBusResourceIntTest {

    private static final Integer DEFAULT_TRUNK_CAPACITY = 1;
    private static final Integer UPDATED_TRUNK_CAPACITY = 2;

    @Autowired
    private InterCityBusRepository interCityBusRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInterCityBusMockMvc;

    private InterCityBus interCityBus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InterCityBusResource interCityBusResource = new InterCityBusResource(interCityBusRepository);
        this.restInterCityBusMockMvc = MockMvcBuilders.standaloneSetup(interCityBusResource)
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
    public static InterCityBus createEntity(EntityManager em) {
        InterCityBus interCityBus = new InterCityBus()
            .trunkCapacity(DEFAULT_TRUNK_CAPACITY);
        return interCityBus;
    }

    @Before
    public void initTest() {
        interCityBus = createEntity(em);
    }

    @Test
    @Transactional
    public void createInterCityBus() throws Exception {
        int databaseSizeBeforeCreate = interCityBusRepository.findAll().size();

        // Create the InterCityBus
        restInterCityBusMockMvc.perform(post("/api/inter-city-buses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interCityBus)))
            .andExpect(status().isCreated());

        // Validate the InterCityBus in the database
        List<InterCityBus> interCityBusList = interCityBusRepository.findAll();
        assertThat(interCityBusList).hasSize(databaseSizeBeforeCreate + 1);
        InterCityBus testInterCityBus = interCityBusList.get(interCityBusList.size() - 1);
        assertThat(testInterCityBus.getTrunkCapacity()).isEqualTo(DEFAULT_TRUNK_CAPACITY);
    }

    @Test
    @Transactional
    public void createInterCityBusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = interCityBusRepository.findAll().size();

        // Create the InterCityBus with an existing ID
        interCityBus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterCityBusMockMvc.perform(post("/api/inter-city-buses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interCityBus)))
            .andExpect(status().isBadRequest());

        // Validate the InterCityBus in the database
        List<InterCityBus> interCityBusList = interCityBusRepository.findAll();
        assertThat(interCityBusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInterCityBuses() throws Exception {
        // Initialize the database
        interCityBusRepository.saveAndFlush(interCityBus);

        // Get all the interCityBusList
        restInterCityBusMockMvc.perform(get("/api/inter-city-buses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interCityBus.getId().intValue())))
            .andExpect(jsonPath("$.[*].trunkCapacity").value(hasItem(DEFAULT_TRUNK_CAPACITY)));
    }

    @Test
    @Transactional
    public void getInterCityBus() throws Exception {
        // Initialize the database
        interCityBusRepository.saveAndFlush(interCityBus);

        // Get the interCityBus
        restInterCityBusMockMvc.perform(get("/api/inter-city-buses/{id}", interCityBus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(interCityBus.getId().intValue()))
            .andExpect(jsonPath("$.trunkCapacity").value(DEFAULT_TRUNK_CAPACITY));
    }

    @Test
    @Transactional
    public void getNonExistingInterCityBus() throws Exception {
        // Get the interCityBus
        restInterCityBusMockMvc.perform(get("/api/inter-city-buses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInterCityBus() throws Exception {
        // Initialize the database
        interCityBusRepository.saveAndFlush(interCityBus);
        int databaseSizeBeforeUpdate = interCityBusRepository.findAll().size();

        // Update the interCityBus
        InterCityBus updatedInterCityBus = interCityBusRepository.findOne(interCityBus.getId());
        updatedInterCityBus
            .trunkCapacity(UPDATED_TRUNK_CAPACITY);

        restInterCityBusMockMvc.perform(put("/api/inter-city-buses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInterCityBus)))
            .andExpect(status().isOk());

        // Validate the InterCityBus in the database
        List<InterCityBus> interCityBusList = interCityBusRepository.findAll();
        assertThat(interCityBusList).hasSize(databaseSizeBeforeUpdate);
        InterCityBus testInterCityBus = interCityBusList.get(interCityBusList.size() - 1);
        assertThat(testInterCityBus.getTrunkCapacity()).isEqualTo(UPDATED_TRUNK_CAPACITY);
    }

    @Test
    @Transactional
    public void updateNonExistingInterCityBus() throws Exception {
        int databaseSizeBeforeUpdate = interCityBusRepository.findAll().size();

        // Create the InterCityBus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInterCityBusMockMvc.perform(put("/api/inter-city-buses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interCityBus)))
            .andExpect(status().isCreated());

        // Validate the InterCityBus in the database
        List<InterCityBus> interCityBusList = interCityBusRepository.findAll();
        assertThat(interCityBusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInterCityBus() throws Exception {
        // Initialize the database
        interCityBusRepository.saveAndFlush(interCityBus);
        int databaseSizeBeforeDelete = interCityBusRepository.findAll().size();

        // Get the interCityBus
        restInterCityBusMockMvc.perform(delete("/api/inter-city-buses/{id}", interCityBus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InterCityBus> interCityBusList = interCityBusRepository.findAll();
        assertThat(interCityBusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterCityBus.class);
        InterCityBus interCityBus1 = new InterCityBus();
        interCityBus1.setId(1L);
        InterCityBus interCityBus2 = new InterCityBus();
        interCityBus2.setId(interCityBus1.getId());
        assertThat(interCityBus1).isEqualTo(interCityBus2);
        interCityBus2.setId(2L);
        assertThat(interCityBus1).isNotEqualTo(interCityBus2);
        interCityBus1.setId(null);
        assertThat(interCityBus1).isNotEqualTo(interCityBus2);
    }
}
