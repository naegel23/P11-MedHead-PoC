/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.medhead.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;


/**
 *
 * @author stiven
 */
@SpringBootTest
@AutoConfigureMockMvc
public class HospitalControllerIT {
    
    @Autowired
    public MockMvc mock;
    
    /*
    public HospitalControllerIT() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }*/


    /**
     * Test of getHospital method, of class HospitalController.
     * @throws java.lang.Exception
     */
    @Test
    public void getHospital() throws Exception {
        mock.perform(get("/hospital"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1290)))
                .andExpect(jsonPath("$[0].name", is("Walton Community Hospital - Virgin Care Services Ltd")))
                .andExpect(jsonPath("$[1].address", is("Heathside Road  Woking Surrey GU22 7HS")))
                .andExpect(jsonPath("$[2].freebed", is(83)));
    }
    
    /**
     * Test of getHospital method, of class HospitalController.
     * @throws java.lang.Exception
     */
    @Test
    public void getHospitalById() throws Exception {
        mock.perform(get("/hospital/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(4)))
                .andExpect(jsonPath("name", is("Bridgewater Hospital")))
                .andExpect(jsonPath("address", is("120 Princess Road   Manchester Greater Manchester M15 5AT")))
                .andExpect(jsonPath("freebed", is(0)));
    }

     /**
     * Test of getAllBySpec method, of class HospitalController.
     * @throws java.lang.Exception
     */
    @Test
    public void getAllBySpec() throws Exception {
        mock.perform(get("/hospital/speciality/pharmacology"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(200)))
                .andExpect(jsonPath("$[0].name", is("Bridgewater Hospital")))
                .andExpect(jsonPath("$[6].address", is("The Alexandra Hospital Mill Lane  Cheadle Cheshire SK8 2PX")))
                .andExpect(jsonPath("$[3].freebed", is(0)));
    }
    
    /**
     * Test of getAllFreeBySpec method, of class HospitalController.
     * @throws java.lang.Exception
     */
    @Test
    public void getAllFreeBySpec() throws Exception {
        mock.perform(get("/hospital/free/speciality/pharmacology"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(132)))
                .andExpect(jsonPath("$[0].name", is("Nuffield Health")))
                .andExpect(jsonPath("$[6].address", is("Benslow Lane  Hitchin Hertfordshire SG4 9QZ")))
                .andExpect(jsonPath("$[3].freebed", is(29)));
    }

    @Test
    public void failAtNotFoundHospital() throws Exception{
        mock.perform(get("/hospital/400258"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void failAtNotFoundHospitalInRange() throws Exception{
        mock.perform(get("/nearest/-1.8/51.25/2/pharmacology"))
                .andExpect(status().isNotFound());
    }

    
    /**
     * Test of getAllHospitalInRange method, of class HospitalController.
     */
    /*
    @Test
    public void testGetAllHospitalInRange() {
        System.out.println("getAllHospitalInRange");
        float longInit = 0.0F;
        float latInit = 0.0F;
        int distance = 0;
        HospitalController instance = new HospitalController();
        Iterable<Hospital> expResult = null;
        Iterable<Hospital> result = instance.getAllHospitalInRange(longInit, latInit, distance);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of getNearest method, of class HospitalController.
     */
   /* @Test
    public void testGetNearest() {
        System.out.println("getNearest");
        String speciality = "";
        float latInit = 0.0F;
        float longInit = 0.0F;
        int distance = 0;
        HospitalController instance = new HospitalController();
        Iterable<Itineraire> expResult = null;
        Iterable<Itineraire> result = instance.getNearest(speciality, latInit, longInit, distance);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        // TODO Nearest : VOir ce qui se passe en cas de panne reseau
    }*/
    
}
