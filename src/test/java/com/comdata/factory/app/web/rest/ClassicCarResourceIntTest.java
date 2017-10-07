package com.comdata.factory.app.web.rest;

import com.comdata.factory.app.ComdataGaricApp;

import com.comdata.factory.app.domain.ClassicCar;
import com.comdata.factory.app.repository.ClassicCarRepository;
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
 * Test class for the ClassicCarResource REST controller.
 *
 * @see ClassicCarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComdataGaricApp.class)
public class ClassicCarResourceIntTest {

    private static final Integer DEFAULT_ROOF_TOP_CAPACITY = 1;
    private static final Integer UPDATED_ROOF_TOP_CAPACITY = 2;

    @Autowired
    private ClassicCarRepository classicCarRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClassicCarMockMvc;

    private ClassicCar classicCar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassicCarResource classicCarResource = new ClassicCarResource(classicCarRepository);
        this.restClassicCarMockMvc = MockMvcBuilders.standaloneSetup(classicCarResource)
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
    public static ClassicCar createEntity(EntityManager em) {
        ClassicCar classicCar = new ClassicCar()
            .roofTopCapacity(DEFAULT_ROOF_TOP_CAPACITY);
        return classicCar;
    }

    @Before
    public void initTest() {
        classicCar = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassicCar() throws Exception {
        int databaseSizeBeforeCreate = classicCarRepository.findAll().size();

        // Create the ClassicCar
        restClassicCarMockMvc.perform(post("/api/classic-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classicCar)))
            .andExpect(status().isCreated());

        // Validate the ClassicCar in the database
        List<ClassicCar> classicCarList = classicCarRepository.findAll();
        assertThat(classicCarList).hasSize(databaseSizeBeforeCreate + 1);
        ClassicCar testClassicCar = classicCarList.get(classicCarList.size() - 1);
        assertThat(testClassicCar.getRoofTopCapacity()).isEqualTo(DEFAULT_ROOF_TOP_CAPACITY);
    }

    @Test
    @Transactional
    public void createClassicCarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classicCarRepository.findAll().size();

        // Create the ClassicCar with an existing ID
        classicCar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassicCarMockMvc.perform(post("/api/classic-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classicCar)))
            .andExpect(status().isBadRequest());

        // Validate the ClassicCar in the database
        List<ClassicCar> classicCarList = classicCarRepository.findAll();
        assertThat(classicCarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClassicCars() throws Exception {
        // Initialize the database
        classicCarRepository.saveAndFlush(classicCar);

        // Get all the classicCarList
        restClassicCarMockMvc.perform(get("/api/classic-cars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classicCar.getId().intValue())))
            .andExpect(jsonPath("$.[*].roofTopCapacity").value(hasItem(DEFAULT_ROOF_TOP_CAPACITY)));
    }

    @Test
    @Transactional
    public void getClassicCar() throws Exception {
        // Initialize the database
        classicCarRepository.saveAndFlush(classicCar);

        // Get the classicCar
        restClassicCarMockMvc.perform(get("/api/classic-cars/{id}", classicCar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classicCar.getId().intValue()))
            .andExpect(jsonPath("$.roofTopCapacity").value(DEFAULT_ROOF_TOP_CAPACITY));
    }

    @Test
    @Transactional
    public void getNonExistingClassicCar() throws Exception {
        // Get the classicCar
        restClassicCarMockMvc.perform(get("/api/classic-cars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassicCar() throws Exception {
        // Initialize the database
        classicCarRepository.saveAndFlush(classicCar);
        int databaseSizeBeforeUpdate = classicCarRepository.findAll().size();

        // Update the classicCar
        ClassicCar updatedClassicCar = classicCarRepository.findOne(classicCar.getId());
        updatedClassicCar
            .roofTopCapacity(UPDATED_ROOF_TOP_CAPACITY);

        restClassicCarMockMvc.perform(put("/api/classic-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClassicCar)))
            .andExpect(status().isOk());

        // Validate the ClassicCar in the database
        List<ClassicCar> classicCarList = classicCarRepository.findAll();
        assertThat(classicCarList).hasSize(databaseSizeBeforeUpdate);
        ClassicCar testClassicCar = classicCarList.get(classicCarList.size() - 1);
        assertThat(testClassicCar.getRoofTopCapacity()).isEqualTo(UPDATED_ROOF_TOP_CAPACITY);
    }

    @Test
    @Transactional
    public void updateNonExistingClassicCar() throws Exception {
        int databaseSizeBeforeUpdate = classicCarRepository.findAll().size();

        // Create the ClassicCar

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClassicCarMockMvc.perform(put("/api/classic-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classicCar)))
            .andExpect(status().isCreated());

        // Validate the ClassicCar in the database
        List<ClassicCar> classicCarList = classicCarRepository.findAll();
        assertThat(classicCarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClassicCar() throws Exception {
        // Initialize the database
        classicCarRepository.saveAndFlush(classicCar);
        int databaseSizeBeforeDelete = classicCarRepository.findAll().size();

        // Get the classicCar
        restClassicCarMockMvc.perform(delete("/api/classic-cars/{id}", classicCar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassicCar> classicCarList = classicCarRepository.findAll();
        assertThat(classicCarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassicCar.class);
        ClassicCar classicCar1 = new ClassicCar();
        classicCar1.setId(1L);
        ClassicCar classicCar2 = new ClassicCar();
        classicCar2.setId(classicCar1.getId());
        assertThat(classicCar1).isEqualTo(classicCar2);
        classicCar2.setId(2L);
        assertThat(classicCar1).isNotEqualTo(classicCar2);
        classicCar1.setId(null);
        assertThat(classicCar1).isNotEqualTo(classicCar2);
    }
}
