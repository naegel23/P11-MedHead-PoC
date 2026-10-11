package com.medhead.api.repository;

import com.medhead.api.model.Hospital;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class HospitalRepositoryCustomTest {
    @Autowired
    private HospitalRepository repository;

    private List<Hospital> hospitalList;

    @BeforeEach
    void setUp() {
        //add timer start
    }

    @AfterEach
    void tearDown() {
        //add timer end
    }
    @Tag("Repository")
    @Test
    void findByFreebed() {
        List<Hospital> testList = repository.findByFreebed();
        assertEquals(testList.size(), 859);
    }

    @Test
    void findBySpecialities() {
        List<Hospital> testList = repository.findBySpecialities("pharmacology");
        assertEquals(testList.size(), 200);
    }

    @Test
    void findByFreebedAndBySpecialities() {
        List<Hospital> testList = repository.findByFreebedAndBySpecialities("pharmacology");
        assertEquals(testList.size(), 132);
    }
}