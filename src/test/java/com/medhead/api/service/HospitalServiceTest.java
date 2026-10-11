package com.medhead.api.service;

import com.medhead.api.model.Hospital;
import com.medhead.api.repository.HospitalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class HospitalServiceTest {

    @Mock
    private HospitalRepository repository;
    @Mock
    private GisOperations gisMock;
    @InjectMocks
    private HospitalService service;
    List<Hospital> hospitalList = new ArrayList<Hospital>();

    @BeforeEach
    void setUp() {
        //préparation des mock de test
        Hospital hopital1 = new Hospital();
        Hospital hopital2 = new Hospital();
        Hospital hopital3 = new Hospital();

        hopital1.setId(1);
        hopital2.setId(2);
        hopital3.setId(3);

        hopital1.setName("Cromwell Hospital");
        hopital2.setName("Princess Grace Hospital");
        hopital3.setName("Spire Tunbridge Wells Hospital");

        hopital1.setLongitude((float)-1.21137082993984222);
        hopital2.setLongitude((float)-0.15291328728199005);
        hopital3.setLongitude((float)0.1881597638130188);

        hopital1.setLatitude((float)51.766518859863281);
        hopital2.setLatitude((float)51.495105743408203);
        hopital3.setLatitude((float)51.134513854980469);

        hopital1.setSpecialities("audio vestibular medicine,prosthodontics,endocrinology and diabetes mellitus,restorative dentistry");
        hopital2.setSpecialities("pharmacology, acute internal medicine,oral and maxillo-facial surgery,paediatric dentistry,clinical neurophysiology");
        hopital3.setSpecialities("gastroenterology,oral medicine,restorative dentistry,additional dental specialties");

        hopital1.setAddress("164-178 Cromwell Road   London  SW5 0TU");
        hopital2.setAddress("42-52 Nottingham Place   London  W1U 5NY");
        hopital3.setAddress("Tunbridge Wells Hospital Fordcombe Road Fordcombe Tunbridge Wells  TN3 0RD");

        hopital1.setFreebed(145);
        hopital2.setFreebed(0);
        hopital3.setFreebed(107);

        hospitalList.add(hopital1);
        hospitalList.add(hopital2);
        hospitalList.add(hopital3);
        System.out.println("Enter beforeEach");


    }

    @AfterEach
    void tearDown() {

    }

    @Test
    public void getAllHospital() {
        when(repository.findAll()).thenReturn(hospitalList);
        List<Hospital> emptyList = (List)service.getAllHospital();
        assertEquals(3, emptyList.size());
    }

    @Test
    public void getHospital() {
        when(repository.findById(1)).thenReturn(Optional.ofNullable((Hospital) hospitalList.get(1)));
        Optional<Hospital> testedHospital = service.getHospital(1);
        assertEquals("Princess Grace Hospital", testedHospital.get().getName());
    }

    @Test
    public void findByFreebed() {
        hospitalList.remove(1);
        when(repository.findByFreebed()).thenReturn(new ArrayList<Hospital>(hospitalList));
        List<Hospital> emptyList = (List)service.findByFreebed();
        assertEquals(2, emptyList.size());
    }

    @Test
    public void getAllHospitalInRange() {
        when(repository.findByFreebed()).thenReturn(hospitalList);
        List<Hospital> emptyList = (List)service.getAllHospitalInRange((float) -1.80, (float) 52.00,50);
        assertEquals(1, emptyList.size());
    }

}