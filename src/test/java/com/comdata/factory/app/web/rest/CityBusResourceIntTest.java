package com.comdata.factory.app.web.rest;

import com.comdata.factory.app.ComdataGaricApp;

import com.comdata.factory.app.domain.CityBus;
import com.comdata.factory.app.repository.CityBusRepository;
import com.comdata.factory.app.service.CityBusService;
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
 * Test class for the CityBusResource REST controller.
 *
 * @see CityBusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComdataGaricApp.class)
public class CityBusResourceIntTest {

    private static final Boolean DEFAULT_HAS_WHRIST = false;
    private static final Boolean UPDATED_HAS_WHRIST = true;

    @Autowired
    private CityBusRepository cityBusRepository;

    @Autowired
    private CityBusService cityBusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCityBusMockMvc;

    private CityBus cityBus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CityBusResource cityBusResource = new CityBusResource(cityBusService);
        this.restCityBusMockMvc = MockMvcBuilders.standaloneSetup(cityBusResource)
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
    public static CityBus createEntity(EntityManager em) {
        CityBus cityBus = new CityBus()
            .hasWhrist(DEFAULT_HAS_WHRIST);
        return cityBus;
    }

    @Before
    public void initTest() {
        cityBus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCityBus() throws Exception {
        int databaseSizeBeforeCreate = cityBusRepository.findAll().size();

        // Create the CityBus
        restCityBusMockMvc.perform(post("/api/city-buses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityBus)))
            .andExpect(status().isCreated());

        // Validate the CityBus in the database
        List<CityBus> cityBusList = cityBusRepository.findAll();
        assertThat(cityBusList).hasSize(databaseSizeBeforeCreate + 1);
        CityBus testCityBus = cityBusList.get(cityBusList.size() - 1);
        assertThat(testCityBus.isHasWhrist()).isEqualTo(DEFAULT_HAS_WHRIST);
    }

    @Test
    @Transactional
    public void createCityBusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cityBusRepository.findAll().size();

        // Create the CityBus with an existing ID
        cityBus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityBusMockMvc.perform(post("/api/city-buses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityBus)))
            .andExpect(status().isBadRequest());

        // Validate the CityBus in the database
        List<CityBus> cityBusList = cityBusRepository.findAll();
        assertThat(cityBusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCityBuses() throws Exception {
        // Initialize the database
        cityBusRepository.saveAndFlush(cityBus);

        // Get all the cityBusList
        restCityBusMockMvc.perform(get("/api/city-buses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cityBus.getId().intValue())))
            .andExpect(jsonPath("$.[*].hasWhrist").value(hasItem(DEFAULT_HAS_WHRIST.booleanValue())));
    }

    @Test
    @Transactional
    public void getCityBus() throws Exception {
        // Initialize the database
        cityBusRepository.saveAndFlush(cityBus);

        // Get the cityBus
        restCityBusMockMvc.perform(get("/api/city-buses/{id}", cityBus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cityBus.getId().intValue()))
            .andExpect(jsonPath("$.hasWhrist").value(DEFAULT_HAS_WHRIST.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCityBus() throws Exception {
        // Get the cityBus
        restCityBusMockMvc.perform(get("/api/city-buses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCityBus() throws Exception {
        // Initialize the database
        cityBusService.save(cityBus);

        int databaseSizeBeforeUpdate = cityBusRepository.findAll().size();

        // Update the cityBus
        CityBus updatedCityBus = cityBusRepository.findOne(cityBus.getId());
        updatedCityBus
            .hasWhrist(UPDATED_HAS_WHRIST);

        restCityBusMockMvc.perform(put("/api/city-buses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCityBus)))
            .andExpect(status().isOk());

        // Validate the CityBus in the database
        List<CityBus> cityBusList = cityBusRepository.findAll();
        assertThat(cityBusList).hasSize(databaseSizeBeforeUpdate);
        CityBus testCityBus = cityBusList.get(cityBusList.size() - 1);
        assertThat(testCityBus.isHasWhrist()).isEqualTo(UPDATED_HAS_WHRIST);
    }

    @Test
    @Transactional
    public void updateNonExistingCityBus() throws Exception {
        int databaseSizeBeforeUpdate = cityBusRepository.findAll().size();

        // Create the CityBus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCityBusMockMvc.perform(put("/api/city-buses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityBus)))
            .andExpect(status().isCreated());

        // Validate the CityBus in the database
        List<CityBus> cityBusList = cityBusRepository.findAll();
        assertThat(cityBusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCityBus() throws Exception {
        // Initialize the database
        cityBusService.save(cityBus);

        int databaseSizeBeforeDelete = cityBusRepository.findAll().size();

        // Get the cityBus
        restCityBusMockMvc.perform(delete("/api/city-buses/{id}", cityBus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CityBus> cityBusList = cityBusRepository.findAll();
        assertThat(cityBusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CityBus.class);
        CityBus cityBus1 = new CityBus();
        cityBus1.setId(1L);
        CityBus cityBus2 = new CityBus();
        cityBus2.setId(cityBus1.getId());
        assertThat(cityBus1).isEqualTo(cityBus2);
        cityBus2.setId(2L);
        assertThat(cityBus1).isNotEqualTo(cityBus2);
        cityBus1.setId(null);
        assertThat(cityBus1).isNotEqualTo(cityBus2);
    }
}
