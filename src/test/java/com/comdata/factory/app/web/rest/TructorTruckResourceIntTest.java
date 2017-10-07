package com.comdata.factory.app.web.rest;

import com.comdata.factory.app.ComdataGaricApp;

import com.comdata.factory.app.domain.TructorTruck;
import com.comdata.factory.app.repository.TructorTruckRepository;
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
 * Test class for the TructorTruckResource REST controller.
 *
 * @see TructorTruckResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComdataGaricApp.class)
public class TructorTruckResourceIntTest {

    private static final Integer DEFAULT_HORSE_POWER = 1;
    private static final Integer UPDATED_HORSE_POWER = 2;

    @Autowired
    private TructorTruckRepository tructorTruckRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTructorTruckMockMvc;

    private TructorTruck tructorTruck;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TructorTruckResource tructorTruckResource = new TructorTruckResource(tructorTruckRepository);
        this.restTructorTruckMockMvc = MockMvcBuilders.standaloneSetup(tructorTruckResource)
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
    public static TructorTruck createEntity(EntityManager em) {
        TructorTruck tructorTruck = new TructorTruck()
            .horsePower(DEFAULT_HORSE_POWER);
        return tructorTruck;
    }

    @Before
    public void initTest() {
        tructorTruck = createEntity(em);
    }

    @Test
    @Transactional
    public void createTructorTruck() throws Exception {
        int databaseSizeBeforeCreate = tructorTruckRepository.findAll().size();

        // Create the TructorTruck
        restTructorTruckMockMvc.perform(post("/api/tructor-trucks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tructorTruck)))
            .andExpect(status().isCreated());

        // Validate the TructorTruck in the database
        List<TructorTruck> tructorTruckList = tructorTruckRepository.findAll();
        assertThat(tructorTruckList).hasSize(databaseSizeBeforeCreate + 1);
        TructorTruck testTructorTruck = tructorTruckList.get(tructorTruckList.size() - 1);
        assertThat(testTructorTruck.getHorsePower()).isEqualTo(DEFAULT_HORSE_POWER);
    }

    @Test
    @Transactional
    public void createTructorTruckWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tructorTruckRepository.findAll().size();

        // Create the TructorTruck with an existing ID
        tructorTruck.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTructorTruckMockMvc.perform(post("/api/tructor-trucks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tructorTruck)))
            .andExpect(status().isBadRequest());

        // Validate the TructorTruck in the database
        List<TructorTruck> tructorTruckList = tructorTruckRepository.findAll();
        assertThat(tructorTruckList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTructorTrucks() throws Exception {
        // Initialize the database
        tructorTruckRepository.saveAndFlush(tructorTruck);

        // Get all the tructorTruckList
        restTructorTruckMockMvc.perform(get("/api/tructor-trucks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tructorTruck.getId().intValue())))
            .andExpect(jsonPath("$.[*].horsePower").value(hasItem(DEFAULT_HORSE_POWER)));
    }

    @Test
    @Transactional
    public void getTructorTruck() throws Exception {
        // Initialize the database
        tructorTruckRepository.saveAndFlush(tructorTruck);

        // Get the tructorTruck
        restTructorTruckMockMvc.perform(get("/api/tructor-trucks/{id}", tructorTruck.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tructorTruck.getId().intValue()))
            .andExpect(jsonPath("$.horsePower").value(DEFAULT_HORSE_POWER));
    }

    @Test
    @Transactional
    public void getNonExistingTructorTruck() throws Exception {
        // Get the tructorTruck
        restTructorTruckMockMvc.perform(get("/api/tructor-trucks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTructorTruck() throws Exception {
        // Initialize the database
        tructorTruckRepository.saveAndFlush(tructorTruck);
        int databaseSizeBeforeUpdate = tructorTruckRepository.findAll().size();

        // Update the tructorTruck
        TructorTruck updatedTructorTruck = tructorTruckRepository.findOne(tructorTruck.getId());
        updatedTructorTruck
            .horsePower(UPDATED_HORSE_POWER);

        restTructorTruckMockMvc.perform(put("/api/tructor-trucks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTructorTruck)))
            .andExpect(status().isOk());

        // Validate the TructorTruck in the database
        List<TructorTruck> tructorTruckList = tructorTruckRepository.findAll();
        assertThat(tructorTruckList).hasSize(databaseSizeBeforeUpdate);
        TructorTruck testTructorTruck = tructorTruckList.get(tructorTruckList.size() - 1);
        assertThat(testTructorTruck.getHorsePower()).isEqualTo(UPDATED_HORSE_POWER);
    }

    @Test
    @Transactional
    public void updateNonExistingTructorTruck() throws Exception {
        int databaseSizeBeforeUpdate = tructorTruckRepository.findAll().size();

        // Create the TructorTruck

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTructorTruckMockMvc.perform(put("/api/tructor-trucks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tructorTruck)))
            .andExpect(status().isCreated());

        // Validate the TructorTruck in the database
        List<TructorTruck> tructorTruckList = tructorTruckRepository.findAll();
        assertThat(tructorTruckList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTructorTruck() throws Exception {
        // Initialize the database
        tructorTruckRepository.saveAndFlush(tructorTruck);
        int databaseSizeBeforeDelete = tructorTruckRepository.findAll().size();

        // Get the tructorTruck
        restTructorTruckMockMvc.perform(delete("/api/tructor-trucks/{id}", tructorTruck.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TructorTruck> tructorTruckList = tructorTruckRepository.findAll();
        assertThat(tructorTruckList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TructorTruck.class);
        TructorTruck tructorTruck1 = new TructorTruck();
        tructorTruck1.setId(1L);
        TructorTruck tructorTruck2 = new TructorTruck();
        tructorTruck2.setId(tructorTruck1.getId());
        assertThat(tructorTruck1).isEqualTo(tructorTruck2);
        tructorTruck2.setId(2L);
        assertThat(tructorTruck1).isNotEqualTo(tructorTruck2);
        tructorTruck1.setId(null);
        assertThat(tructorTruck1).isNotEqualTo(tructorTruck2);
    }
}
