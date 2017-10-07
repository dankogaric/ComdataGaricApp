package com.comdata.factory.app.web.rest;

import com.comdata.factory.app.ComdataGaricApp;

import com.comdata.factory.app.domain.AdditionalEquipment;
import com.comdata.factory.app.repository.AdditionalEquipmentRepository;
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
 * Test class for the AdditionalEquipmentResource REST controller.
 *
 * @see AdditionalEquipmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComdataGaricApp.class)
public class AdditionalEquipmentResourceIntTest {

    private static final Boolean DEFAULT_HAS_ABS = false;
    private static final Boolean UPDATED_HAS_ABS = true;

    private static final Boolean DEFAULT_HAS_ESP = false;
    private static final Boolean UPDATED_HAS_ESP = true;

    private static final Boolean DEFAULT_HAS_GLASS_ROOF = false;
    private static final Boolean UPDATED_HAS_GLASS_ROOF = true;

    private static final Boolean DEFAULT_HAS_ALU_WHEELS = false;
    private static final Boolean UPDATED_HAS_ALU_WHEELS = true;

    @Autowired
    private AdditionalEquipmentRepository additionalEquipmentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdditionalEquipmentMockMvc;

    private AdditionalEquipment additionalEquipment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdditionalEquipmentResource additionalEquipmentResource = new AdditionalEquipmentResource(additionalEquipmentRepository);
        this.restAdditionalEquipmentMockMvc = MockMvcBuilders.standaloneSetup(additionalEquipmentResource)
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
    public static AdditionalEquipment createEntity(EntityManager em) {
        AdditionalEquipment additionalEquipment = new AdditionalEquipment()
            .hasAbs(DEFAULT_HAS_ABS)
            .hasEsp(DEFAULT_HAS_ESP)
            .hasGlassRoof(DEFAULT_HAS_GLASS_ROOF)
            .hasAluWheels(DEFAULT_HAS_ALU_WHEELS);
        return additionalEquipment;
    }

    @Before
    public void initTest() {
        additionalEquipment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdditionalEquipment() throws Exception {
        int databaseSizeBeforeCreate = additionalEquipmentRepository.findAll().size();

        // Create the AdditionalEquipment
        restAdditionalEquipmentMockMvc.perform(post("/api/additional-equipments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(additionalEquipment)))
            .andExpect(status().isCreated());

        // Validate the AdditionalEquipment in the database
        List<AdditionalEquipment> additionalEquipmentList = additionalEquipmentRepository.findAll();
        assertThat(additionalEquipmentList).hasSize(databaseSizeBeforeCreate + 1);
        AdditionalEquipment testAdditionalEquipment = additionalEquipmentList.get(additionalEquipmentList.size() - 1);
        assertThat(testAdditionalEquipment.isHasAbs()).isEqualTo(DEFAULT_HAS_ABS);
        assertThat(testAdditionalEquipment.isHasEsp()).isEqualTo(DEFAULT_HAS_ESP);
        assertThat(testAdditionalEquipment.isHasGlassRoof()).isEqualTo(DEFAULT_HAS_GLASS_ROOF);
        assertThat(testAdditionalEquipment.isHasAluWheels()).isEqualTo(DEFAULT_HAS_ALU_WHEELS);
    }

    @Test
    @Transactional
    public void createAdditionalEquipmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = additionalEquipmentRepository.findAll().size();

        // Create the AdditionalEquipment with an existing ID
        additionalEquipment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdditionalEquipmentMockMvc.perform(post("/api/additional-equipments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(additionalEquipment)))
            .andExpect(status().isBadRequest());

        // Validate the AdditionalEquipment in the database
        List<AdditionalEquipment> additionalEquipmentList = additionalEquipmentRepository.findAll();
        assertThat(additionalEquipmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAdditionalEquipments() throws Exception {
        // Initialize the database
        additionalEquipmentRepository.saveAndFlush(additionalEquipment);

        // Get all the additionalEquipmentList
        restAdditionalEquipmentMockMvc.perform(get("/api/additional-equipments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalEquipment.getId().intValue())))
            .andExpect(jsonPath("$.[*].hasAbs").value(hasItem(DEFAULT_HAS_ABS.booleanValue())))
            .andExpect(jsonPath("$.[*].hasEsp").value(hasItem(DEFAULT_HAS_ESP.booleanValue())))
            .andExpect(jsonPath("$.[*].hasGlassRoof").value(hasItem(DEFAULT_HAS_GLASS_ROOF.booleanValue())))
            .andExpect(jsonPath("$.[*].hasAluWheels").value(hasItem(DEFAULT_HAS_ALU_WHEELS.booleanValue())));
    }

    @Test
    @Transactional
    public void getAdditionalEquipment() throws Exception {
        // Initialize the database
        additionalEquipmentRepository.saveAndFlush(additionalEquipment);

        // Get the additionalEquipment
        restAdditionalEquipmentMockMvc.perform(get("/api/additional-equipments/{id}", additionalEquipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(additionalEquipment.getId().intValue()))
            .andExpect(jsonPath("$.hasAbs").value(DEFAULT_HAS_ABS.booleanValue()))
            .andExpect(jsonPath("$.hasEsp").value(DEFAULT_HAS_ESP.booleanValue()))
            .andExpect(jsonPath("$.hasGlassRoof").value(DEFAULT_HAS_GLASS_ROOF.booleanValue()))
            .andExpect(jsonPath("$.hasAluWheels").value(DEFAULT_HAS_ALU_WHEELS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAdditionalEquipment() throws Exception {
        // Get the additionalEquipment
        restAdditionalEquipmentMockMvc.perform(get("/api/additional-equipments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdditionalEquipment() throws Exception {
        // Initialize the database
        additionalEquipmentRepository.saveAndFlush(additionalEquipment);
        int databaseSizeBeforeUpdate = additionalEquipmentRepository.findAll().size();

        // Update the additionalEquipment
        AdditionalEquipment updatedAdditionalEquipment = additionalEquipmentRepository.findOne(additionalEquipment.getId());
        updatedAdditionalEquipment
            .hasAbs(UPDATED_HAS_ABS)
            .hasEsp(UPDATED_HAS_ESP)
            .hasGlassRoof(UPDATED_HAS_GLASS_ROOF)
            .hasAluWheels(UPDATED_HAS_ALU_WHEELS);

        restAdditionalEquipmentMockMvc.perform(put("/api/additional-equipments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdditionalEquipment)))
            .andExpect(status().isOk());

        // Validate the AdditionalEquipment in the database
        List<AdditionalEquipment> additionalEquipmentList = additionalEquipmentRepository.findAll();
        assertThat(additionalEquipmentList).hasSize(databaseSizeBeforeUpdate);
        AdditionalEquipment testAdditionalEquipment = additionalEquipmentList.get(additionalEquipmentList.size() - 1);
        assertThat(testAdditionalEquipment.isHasAbs()).isEqualTo(UPDATED_HAS_ABS);
        assertThat(testAdditionalEquipment.isHasEsp()).isEqualTo(UPDATED_HAS_ESP);
        assertThat(testAdditionalEquipment.isHasGlassRoof()).isEqualTo(UPDATED_HAS_GLASS_ROOF);
        assertThat(testAdditionalEquipment.isHasAluWheels()).isEqualTo(UPDATED_HAS_ALU_WHEELS);
    }

    @Test
    @Transactional
    public void updateNonExistingAdditionalEquipment() throws Exception {
        int databaseSizeBeforeUpdate = additionalEquipmentRepository.findAll().size();

        // Create the AdditionalEquipment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdditionalEquipmentMockMvc.perform(put("/api/additional-equipments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(additionalEquipment)))
            .andExpect(status().isCreated());

        // Validate the AdditionalEquipment in the database
        List<AdditionalEquipment> additionalEquipmentList = additionalEquipmentRepository.findAll();
        assertThat(additionalEquipmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAdditionalEquipment() throws Exception {
        // Initialize the database
        additionalEquipmentRepository.saveAndFlush(additionalEquipment);
        int databaseSizeBeforeDelete = additionalEquipmentRepository.findAll().size();

        // Get the additionalEquipment
        restAdditionalEquipmentMockMvc.perform(delete("/api/additional-equipments/{id}", additionalEquipment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdditionalEquipment> additionalEquipmentList = additionalEquipmentRepository.findAll();
        assertThat(additionalEquipmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdditionalEquipment.class);
        AdditionalEquipment additionalEquipment1 = new AdditionalEquipment();
        additionalEquipment1.setId(1L);
        AdditionalEquipment additionalEquipment2 = new AdditionalEquipment();
        additionalEquipment2.setId(additionalEquipment1.getId());
        assertThat(additionalEquipment1).isEqualTo(additionalEquipment2);
        additionalEquipment2.setId(2L);
        assertThat(additionalEquipment1).isNotEqualTo(additionalEquipment2);
        additionalEquipment1.setId(null);
        assertThat(additionalEquipment1).isNotEqualTo(additionalEquipment2);
    }
}
