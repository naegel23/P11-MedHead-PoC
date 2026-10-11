/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.medhead.api.service;

import com.medhead.api.model.Hospital;
import com.medhead.api.model.Itineraire;
import io.sentry.spring.tracing.SentrySpan;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.medhead.api.repository.HospitalRepository;
import com.medhead.api.repository.HospitalRepositoryCustom;
import java.util.ArrayList;
import java.util.List;


import java.util.Optional;

/**
 *
 * @author stiven
 */
@Data
@Service
public class HospitalService implements HospitalRepositoryCustom{

    @Autowired
    private HospitalRepository hospitalRepository;
    @SentrySpan
    @Override
    public List<Hospital> findByFreebed() {
        return hospitalRepository.findByFreebed();
    }
    @SentrySpan
    @Override
    public List<Hospital> findBySpecialities(String spec) {
        return hospitalRepository.findBySpecialities(spec);
    }

    @SentrySpan
    @Override
    public List<Hospital> findByFreebedAndBySpecialities(String spec) {
        return hospitalRepository.findByFreebedAndBySpecialities(spec);
    }

    @SentrySpan
    public Optional<Hospital> getHospital(final Integer id) {
        return hospitalRepository.findById(id);
    }
    @SentrySpan
    public Iterable<Hospital> getAllHospital() {
        return hospitalRepository.findAll();
    }
    @SentrySpan
    public void deleteHospital(final Integer id) {
        hospitalRepository.deleteById(id);
    }
    @SentrySpan
    public Hospital saveHospital(Hospital hopital) {
        return hospitalRepository.save(hopital);
    }
    @SentrySpan
    public Iterable<Hospital> getAllHospitalInRange(float longCentre, float latCentre, int distance){
        ArrayList<Hospital> myHospitalList = new ArrayList<>();
        for (Hospital hopital : findByFreebed()) {
            if (GisOperations.distFrom(longCentre, latCentre, hopital.getLongitude(), hopital.getLatitude()) < distance) {
                myHospitalList.add(hopital);
            }
        }
        return myHospitalList;
    }

    @SentrySpan
    public Iterable<Hospital> getAllFreeHospitalInRangeWithSpeciality(float longCentre, float latCentre, int distance, String speciality){
        ArrayList<Hospital> myHospitalList = new ArrayList<>();
        ArrayList<Hospital> myHospitalFreeList = new ArrayList<>(findByFreebedAndBySpecialities(speciality));
        for (Hospital hopital : myHospitalFreeList){
            if (GisOperations.distFrom(longCentre, latCentre, hopital.getLatitude(), hopital.getLongitude()) < distance) {
                myHospitalList.add(hopital);
            }
        }      
        return myHospitalList;
    }
    @SentrySpan
    public Iterable<Itineraire> getNearest(float longCentre, float latCentre, int distance, String speciality) {
        Iterable<Itineraire> myItineraireList;
        Iterable<Hospital> myHospitalList;
        myHospitalList = this.getAllFreeHospitalInRangeWithSpeciality(longCentre, latCentre, distance, speciality);
        myItineraireList = GisOperations.getItineraireOSRM(longCentre, latCentre, myHospitalList);
        return myItineraireList;
    }

    
}
