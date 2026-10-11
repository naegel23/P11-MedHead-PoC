package com.medhead.api.service;

import com.medhead.api.model.Hospital;
import com.medhead.api.repository.HospitalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HospitalServiceTestIT {

    @Autowired
    private HospitalRepository repository;
    @Autowired
    private HospitalService service;
    List<Hospital> hospitalList = new ArrayList<Hospital>();

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    public void findBySpecialities() {
        List<Hospital> emptyList = service.findBySpecialities("pharmacology");
        assertEquals(200, emptyList.size());
    }

}