package com.comdata.factory.app.web.rest;

import com.comdata.factory.app.ComdataGaricApp;

import com.comdata.factory.app.domain.TankTruck;
import com.comdata.factory.app.repository.TankTruckRepository;
import com.comdata.factory.app.service.TankTruckService;
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
 * Test class for the TankTruckResource REST controller.
 *
 * @see TankTruckResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComdataGaricApp.class)
public class TankTruckResourceIntTest {

    private static final Integer DEFAULT_TANK_CAPACITY = 1;
    private static final Integer UPDATED_TANK_CAPACITY = 2;

    @Autowired
    private TankTruckRepository tankTruckRepository;

    @Autowired
    private TankTruckService tankTruckService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTankTruckMockMvc;

    private TankTruck tankTruck;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TankTruckResource tankTruckResource = new TankTruckResource(tankTruckService);
        this.restTankTruckMockMvc = MockMvcBuilders.standaloneSetup(tankTruckResource)
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
    public static TankTruck createEntity(EntityManager em) {
        TankTruck tankTruck = new TankTruck()
            .tankCapacity(DEFAULT_TANK_CAPACITY);
        return tankTruck;
    }

    @Before
    public void initTest() {
        tankTruck = createEntity(em);
    }

    @Test
    @Transactional
    public void createTankTruck() throws Exception {
        int databaseSizeBeforeCreate = tankTruckRepository.findAll().size();

        // Create the TankTruck
        restTankTruckMockMvc.perform(post("/api/tank-trucks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tankTruck)))
            .andExpect(status().isCreated());

        // Validate the TankTruck in the database
        List<TankTruck> tankTruckList = tankTruckRepository.findAll();
        assertThat(tankTruckList).hasSize(databaseSizeBeforeCreate + 1);
        TankTruck testTankTruck = tankTruckList.get(tankTruckList.size() - 1);
        assertThat(testTankTruck.getTankCapacity()).isEqualTo(DEFAULT_TANK_CAPACITY);
    }

    @Test
    @Transactional
    public void createTankTruckWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tankTruckRepository.findAll().size();

        // Create the TankTruck with an existing ID
        tankTruck.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTankTruckMockMvc.perform(post("/api/tank-trucks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tankTruck)))
            .andExpect(status().isBadRequest());

        // Validate the TankTruck in the database
        List<TankTruck> tankTruckList = tankTruckRepository.findAll();
        assertThat(tankTruckList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTankTrucks() throws Exception {
        // Initialize the database
        tankTruckRepository.saveAndFlush(tankTruck);

        // Get all the tankTruckList
        restTankTruckMockMvc.perform(get("/api/tank-trucks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tankTruck.getId().intValue())))
            .andExpect(jsonPath("$.[*].tankCapacity").value(hasItem(DEFAULT_TANK_CAPACITY)));
    }

    @Test
    @Transactional
    public void getTankTruck() throws Exception {
        // Initialize the database
        tankTruckRepository.saveAndFlush(tankTruck);

        // Get the tankTruck
        restTankTruckMockMvc.perform(get("/api/tank-trucks/{id}", tankTruck.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tankTruck.getId().intValue()))
            .andExpect(jsonPath("$.tankCapacity").value(DEFAULT_TANK_CAPACITY));
    }

    @Test
    @Transactional
    public void getNonExistingTankTruck() throws Exception {
        // Get the tankTruck
        restTankTruckMockMvc.perform(get("/api/tank-trucks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTankTruck() throws Exception {
        // Initialize the database
        tankTruckService.save(tankTruck);

        int databaseSizeBeforeUpdate = tankTruckRepository.findAll().size();

        // Update the tankTruck
        TankTruck updatedTankTruck = tankTruckRepository.findOne(tankTruck.getId());
        updatedTankTruck
            .tankCapacity(UPDATED_TANK_CAPACITY);

        restTankTruckMockMvc.perform(put("/api/tank-trucks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTankTruck)))
            .andExpect(status().isOk());

        // Validate the TankTruck in the database
        List<TankTruck> tankTruckList = tankTruckRepository.findAll();
        assertThat(tankTruckList).hasSize(databaseSizeBeforeUpdate);
        TankTruck testTankTruck = tankTruckList.get(tankTruckList.size() - 1);
        assertThat(testTankTruck.getTankCapacity()).isEqualTo(UPDATED_TANK_CAPACITY);
    }

    @Test
    @Transactional
    public void updateNonExistingTankTruck() throws Exception {
        int databaseSizeBeforeUpdate = tankTruckRepository.findAll().size();

        // Create the TankTruck

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTankTruckMockMvc.perform(put("/api/tank-trucks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tankTruck)))
            .andExpect(status().isCreated());

        // Validate the TankTruck in the database
        List<TankTruck> tankTruckList = tankTruckRepository.findAll();
        assertThat(tankTruckList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTankTruck() throws Exception {
        // Initialize the database
        tankTruckService.save(tankTruck);

        int databaseSizeBeforeDelete = tankTruckRepository.findAll().size();

        // Get the tankTruck
        restTankTruckMockMvc.perform(delete("/api/tank-trucks/{id}", tankTruck.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TankTruck> tankTruckList = tankTruckRepository.findAll();
        assertThat(tankTruckList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TankTruck.class);
        TankTruck tankTruck1 = new TankTruck();
        tankTruck1.setId(1L);
        TankTruck tankTruck2 = new TankTruck();
        tankTruck2.setId(tankTruck1.getId());
        assertThat(tankTruck1).isEqualTo(tankTruck2);
        tankTruck2.setId(2L);
        assertThat(tankTruck1).isNotEqualTo(tankTruck2);
        tankTruck1.setId(null);
        assertThat(tankTruck1).isNotEqualTo(tankTruck2);
    }
}
