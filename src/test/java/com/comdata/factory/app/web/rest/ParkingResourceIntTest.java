package com.comdata.factory.app.web.rest;

import com.comdata.factory.app.ComdataGaricApp;

import com.comdata.factory.app.domain.Parking;
import com.comdata.factory.app.repository.ParkingRepository;
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
 * Test class for the ParkingResource REST controller.
 *
 * @see ParkingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComdataGaricApp.class)
public class ParkingResourceIntTest {

    private static final Integer DEFAULT_AREA = 1;
    private static final Integer UPDATED_AREA = 2;

    private static final Double DEFAULT_HEIGHT = 1D;
    private static final Double UPDATED_HEIGHT = 2D;

    private static final Integer DEFAULT_REST_AREA = 1;
    private static final Integer UPDATED_REST_AREA = 2;

    private static final Boolean DEFAULT_HAS_ROOF = false;
    private static final Boolean UPDATED_HAS_ROOF = true;

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParkingMockMvc;

    private Parking parking;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParkingResource parkingResource = new ParkingResource(parkingRepository);
        this.restParkingMockMvc = MockMvcBuilders.standaloneSetup(parkingResource)
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
    public static Parking createEntity(EntityManager em) {
        Parking parking = new Parking()
            .area(DEFAULT_AREA)
            .height(DEFAULT_HEIGHT)
            .restArea(DEFAULT_REST_AREA)
            .hasRoof(DEFAULT_HAS_ROOF);
        return parking;
    }

    @Before
    public void initTest() {
        parking = createEntity(em);
    }

    @Test
    @Transactional
    public void createParking() throws Exception {
        int databaseSizeBeforeCreate = parkingRepository.findAll().size();

        // Create the Parking
        restParkingMockMvc.perform(post("/api/parkings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parking)))
            .andExpect(status().isCreated());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeCreate + 1);
        Parking testParking = parkingList.get(parkingList.size() - 1);
        assertThat(testParking.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testParking.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testParking.getRestArea()).isEqualTo(DEFAULT_REST_AREA);
        assertThat(testParking.isHasRoof()).isEqualTo(DEFAULT_HAS_ROOF);
    }

    @Test
    @Transactional
    public void createParkingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parkingRepository.findAll().size();

        // Create the Parking with an existing ID
        parking.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParkingMockMvc.perform(post("/api/parkings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parking)))
            .andExpect(status().isBadRequest());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllParkings() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList
        restParkingMockMvc.perform(get("/api/parkings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parking.getId().intValue())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].restArea").value(hasItem(DEFAULT_REST_AREA)))
            .andExpect(jsonPath("$.[*].hasRoof").value(hasItem(DEFAULT_HAS_ROOF.booleanValue())));
    }

    @Test
    @Transactional
    public void getParking() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get the parking
        restParkingMockMvc.perform(get("/api/parkings/{id}", parking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parking.getId().intValue()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.restArea").value(DEFAULT_REST_AREA))
            .andExpect(jsonPath("$.hasRoof").value(DEFAULT_HAS_ROOF.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingParking() throws Exception {
        // Get the parking
        restParkingMockMvc.perform(get("/api/parkings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParking() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);
        int databaseSizeBeforeUpdate = parkingRepository.findAll().size();

        // Update the parking
        Parking updatedParking = parkingRepository.findOne(parking.getId());
        updatedParking
            .area(UPDATED_AREA)
            .height(UPDATED_HEIGHT)
            .restArea(UPDATED_REST_AREA)
            .hasRoof(UPDATED_HAS_ROOF);

        restParkingMockMvc.perform(put("/api/parkings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParking)))
            .andExpect(status().isOk());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeUpdate);
        Parking testParking = parkingList.get(parkingList.size() - 1);
        assertThat(testParking.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testParking.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testParking.getRestArea()).isEqualTo(UPDATED_REST_AREA);
        assertThat(testParking.isHasRoof()).isEqualTo(UPDATED_HAS_ROOF);
    }

    @Test
    @Transactional
    public void updateNonExistingParking() throws Exception {
        int databaseSizeBeforeUpdate = parkingRepository.findAll().size();

        // Create the Parking

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParkingMockMvc.perform(put("/api/parkings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parking)))
            .andExpect(status().isCreated());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParking() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);
        int databaseSizeBeforeDelete = parkingRepository.findAll().size();

        // Get the parking
        restParkingMockMvc.perform(delete("/api/parkings/{id}", parking.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parking.class);
        Parking parking1 = new Parking();
        parking1.setId(1L);
        Parking parking2 = new Parking();
        parking2.setId(parking1.getId());
        assertThat(parking1).isEqualTo(parking2);
        parking2.setId(2L);
        assertThat(parking1).isNotEqualTo(parking2);
        parking1.setId(null);
        assertThat(parking1).isNotEqualTo(parking2);
    }
}
