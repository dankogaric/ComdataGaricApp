package com.comdata.factory.app.web.rest;

import com.comdata.factory.app.ComdataGaricApp;

import com.comdata.factory.app.domain.Cabrio;
import com.comdata.factory.app.repository.CabrioRepository;
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
 * Test class for the CabrioResource REST controller.
 *
 * @see CabrioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComdataGaricApp.class)
public class CabrioResourceIntTest {

    private static final Boolean DEFAULT_HAS_REMOVABLE_ROOF = false;
    private static final Boolean UPDATED_HAS_REMOVABLE_ROOF = true;

    @Autowired
    private CabrioRepository cabrioRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCabrioMockMvc;

    private Cabrio cabrio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CabrioResource cabrioResource = new CabrioResource(cabrioRepository);
        this.restCabrioMockMvc = MockMvcBuilders.standaloneSetup(cabrioResource)
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
    public static Cabrio createEntity(EntityManager em) {
        Cabrio cabrio = new Cabrio()
            .hasRemovableRoof(DEFAULT_HAS_REMOVABLE_ROOF);
        return cabrio;
    }

    @Before
    public void initTest() {
        cabrio = createEntity(em);
    }

    @Test
    @Transactional
    public void createCabrio() throws Exception {
        int databaseSizeBeforeCreate = cabrioRepository.findAll().size();

        // Create the Cabrio
        restCabrioMockMvc.perform(post("/api/cabrios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cabrio)))
            .andExpect(status().isCreated());

        // Validate the Cabrio in the database
        List<Cabrio> cabrioList = cabrioRepository.findAll();
        assertThat(cabrioList).hasSize(databaseSizeBeforeCreate + 1);
        Cabrio testCabrio = cabrioList.get(cabrioList.size() - 1);
        assertThat(testCabrio.isHasRemovableRoof()).isEqualTo(DEFAULT_HAS_REMOVABLE_ROOF);
    }

    @Test
    @Transactional
    public void createCabrioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cabrioRepository.findAll().size();

        // Create the Cabrio with an existing ID
        cabrio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCabrioMockMvc.perform(post("/api/cabrios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cabrio)))
            .andExpect(status().isBadRequest());

        // Validate the Cabrio in the database
        List<Cabrio> cabrioList = cabrioRepository.findAll();
        assertThat(cabrioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCabrios() throws Exception {
        // Initialize the database
        cabrioRepository.saveAndFlush(cabrio);

        // Get all the cabrioList
        restCabrioMockMvc.perform(get("/api/cabrios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cabrio.getId().intValue())))
            .andExpect(jsonPath("$.[*].hasRemovableRoof").value(hasItem(DEFAULT_HAS_REMOVABLE_ROOF.booleanValue())));
    }

    @Test
    @Transactional
    public void getCabrio() throws Exception {
        // Initialize the database
        cabrioRepository.saveAndFlush(cabrio);

        // Get the cabrio
        restCabrioMockMvc.perform(get("/api/cabrios/{id}", cabrio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cabrio.getId().intValue()))
            .andExpect(jsonPath("$.hasRemovableRoof").value(DEFAULT_HAS_REMOVABLE_ROOF.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCabrio() throws Exception {
        // Get the cabrio
        restCabrioMockMvc.perform(get("/api/cabrios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCabrio() throws Exception {
        // Initialize the database
        cabrioRepository.saveAndFlush(cabrio);
        int databaseSizeBeforeUpdate = cabrioRepository.findAll().size();

        // Update the cabrio
        Cabrio updatedCabrio = cabrioRepository.findOne(cabrio.getId());
        updatedCabrio
            .hasRemovableRoof(UPDATED_HAS_REMOVABLE_ROOF);

        restCabrioMockMvc.perform(put("/api/cabrios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCabrio)))
            .andExpect(status().isOk());

        // Validate the Cabrio in the database
        List<Cabrio> cabrioList = cabrioRepository.findAll();
        assertThat(cabrioList).hasSize(databaseSizeBeforeUpdate);
        Cabrio testCabrio = cabrioList.get(cabrioList.size() - 1);
        assertThat(testCabrio.isHasRemovableRoof()).isEqualTo(UPDATED_HAS_REMOVABLE_ROOF);
    }

    @Test
    @Transactional
    public void updateNonExistingCabrio() throws Exception {
        int databaseSizeBeforeUpdate = cabrioRepository.findAll().size();

        // Create the Cabrio

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCabrioMockMvc.perform(put("/api/cabrios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cabrio)))
            .andExpect(status().isCreated());

        // Validate the Cabrio in the database
        List<Cabrio> cabrioList = cabrioRepository.findAll();
        assertThat(cabrioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCabrio() throws Exception {
        // Initialize the database
        cabrioRepository.saveAndFlush(cabrio);
        int databaseSizeBeforeDelete = cabrioRepository.findAll().size();

        // Get the cabrio
        restCabrioMockMvc.perform(delete("/api/cabrios/{id}", cabrio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cabrio> cabrioList = cabrioRepository.findAll();
        assertThat(cabrioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cabrio.class);
        Cabrio cabrio1 = new Cabrio();
        cabrio1.setId(1L);
        Cabrio cabrio2 = new Cabrio();
        cabrio2.setId(cabrio1.getId());
        assertThat(cabrio1).isEqualTo(cabrio2);
        cabrio2.setId(2L);
        assertThat(cabrio1).isNotEqualTo(cabrio2);
        cabrio1.setId(null);
        assertThat(cabrio1).isNotEqualTo(cabrio2);
    }
}
