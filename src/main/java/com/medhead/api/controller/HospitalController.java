package com.medhead.api.controller;


import com.google.common.collect.Lists;
import com.medhead.api.exeptions.NoApiCommunication;
import com.medhead.api.exeptions.NoHospitalFound;
import com.medhead.api.exeptions.NoParametersEntered;
import com.medhead.api.exeptions.NotYetImplemented;
import com.medhead.api.model.Hospital;
import com.medhead.api.model.Itineraire;
import com.medhead.api.service.GisOperations;
import com.medhead.api.service.HospitalService;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author stiven
 */
@RestController
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private GisOperations gisOperation;

    /**
     *
     */
    @RequestMapping("/")
    public void handleRequest() {
        throw new RuntimeException("Exeption racine");
    }
    
    /**
     *
     * @throws NotYetImplemented - generic error for no implementation
     */
    @RequestMapping("/app-info")
    public void handleAppInfoRequest() throws NotYetImplemented {
        throw new NotYetImplemented("La requete n'est pas encore implementée");
    }
      
    /**
     * Read - Get all hospitals
     * @return - An Iterable object of Hospital full filled
     * @throws com.medhead.api.exeptions.NoHospitalFound - Generic error for no hospital
     */
    @GetMapping("/hospital")
    public Iterable<Hospital> getHospital() throws NoHospitalFound{
        if (hospitalService.getAllHospital()!=null){
            return hospitalService.getAllHospital();
        }else {
            throw new NoHospitalFound("No hospital found in database");
        }
        
    }
    
    /**
     * Read - Get all hospital
     * @throws com.medhead.api.exeptions.NoParametersEntered  - Generic error for no parameters entered
     */
    @RequestMapping("/hospital/speciality")
    public void noSpecEntered() throws NoParametersEntered {
        throw new NoParametersEntered("Warning, missing the speciality");
    }
        
    /**
     *
     * @return - Iterable of all free hospital
     * @throws NoHospitalFound  - Generic error for no hospital
     */
    @GetMapping("/hospital/free")
    public Iterable<Hospital> getAllFreebedHospital()  throws NoHospitalFound{
        if (hospitalService.findByFreebed()!=null){
            return hospitalService.findByFreebed();
        }else {
            throw new NoHospitalFound("No hospital found in database");
        }
    }

    /**
     * Read - Get hospitals by Id
     * @param id - Integer of hospital's ID
     * @return - An object of Hospital full filled with given Id
     * @throws com.medhead.api.exeptions.NoHospitalFound -  - Generic error for no hospital
     */
    @GetMapping("/hospital/{id}")
    public Optional<Hospital> getHospital(@PathVariable("id") final Integer id)  throws NoHospitalFound{
        Optional<Hospital> hospital = hospitalService.getHospital(id);
        if (hospital.isPresent()){
            return hospital;
        }
        else {
            throw new NoHospitalFound("No hospital found in database with ID "+id);
        }
    }

    /**
     * Read - Get hospitals by Specialities
     * @param speciality - String of the speciality's name
     * @return - An object of Hospital full filled with given speciality
     * @throws com.medhead.api.exeptions.NoHospitalFound -  - Generic error for no hospital
     */    
    @GetMapping("/hospital/speciality/{spec}")
    public Iterable<Hospital> getAllBySpec(@PathVariable("spec") final String speciality) throws NoHospitalFound{
        ArrayList<Hospital> myHospitalList = (ArrayList<Hospital>) hospitalService.findBySpecialities(speciality);
        if (!myHospitalList.isEmpty()){
            return hospitalService.findBySpecialities(speciality);
        } else {
            throw new NoHospitalFound("No hospital found in database with this speciality");
        }
    }
    
    /**
     *
     * @param speciality - String of speciality's name
     * @return - An Iterable of hospital list with freebed in the speciality given
     * @throws NoHospitalFound - Generic error for no hospital
     */
    @GetMapping("/hospital/free/speciality/{spec}")
    public Iterable<Hospital> getAllFreeBySpec(@PathVariable("spec") final String speciality) throws NoHospitalFound{
        ArrayList<Hospital> myHospitalList = (ArrayList<Hospital>) hospitalService.findByFreebedAndBySpecialities(speciality);
        if (!myHospitalList.isEmpty()){
            return hospitalService.findByFreebedAndBySpecialities(speciality);
        } else {
            throw new NoHospitalFound("No Free hospital found in database with this speciality");
        }
    }
    
    /**
     *
     * @param speciality - String of speciality's name
     * @param latInit - Latitude (ex : 51.525756)
     * @param longInit - Longitude (ex : -0.712585)
     * @param distance - Range for searching hospital
     * @return - Iterable of hospital with free bed and speciality in a range with origin long/lat
     * @throws NoHospitalFound - Generic error for no hospital
     */
    @GetMapping("/hospital/free/speciality/{spec}/range/{longInit}/{latInit}/{distance}")
    public Iterable<Hospital> getAllFreeHospitalInRangeWithSpeciality(@PathVariable("spec") final String speciality, @PathVariable("latInit") final float latInit, @PathVariable("longInit") final float longInit, @PathVariable("distance") final int distance ) throws NoHospitalFound {
        ArrayList<Hospital> myHospitalList = (ArrayList<Hospital>) hospitalService.getAllFreeHospitalInRangeWithSpeciality(latInit, longInit, distance, speciality);
        if (!myHospitalList.isEmpty()){
            return myHospitalList;
        } else {
            throw new NoHospitalFound("No hospital found in database with this speciality at this range");
        }
    }
    
     /**
     * Read - Get hospitals by range
     * @param latInit - Latitude (ex : 51.525756)
     * @param longInit - Longitude (ex : -0.712585)
     * @param distance - Range for searching hospital
     * @return - An object of Hospital full filled with given speciality
     * @throws com.medhead.api.exeptions.NoHospitalFound - Generic error for no hospital
     */
    @GetMapping("/hospital/range/{longInit}/{latInit}/{distance}")
    public Iterable<Hospital> getAllHospitalInRange(@PathVariable("longInit") final float longInit, @PathVariable("latInit") final float latInit, @PathVariable("distance") final int distance ) throws NoHospitalFound{
        ArrayList<Hospital> hospitalInRange;
        hospitalInRange = (ArrayList<Hospital>) hospitalService.getAllHospitalInRange(longInit, latInit, distance);
        if (!hospitalInRange.isEmpty()){
            return hospitalInRange;
        } else {
            throw new NoHospitalFound("No hospital found in database in this range");
        }
    }

    /**
     *
     * @param speciality - String of speciality's name
     * @param latInit - Latitude (ex : 51.525756)
     * @param longInit - Longitude (ex : -0.712585)
     * @param distance - Range for searching hospital
     * @return - An object of Hospital full filled with given speciality, in a range, ordered and with use of external API for real distance
     * @throws NoHospitalFound - Generic error for no hospital
     */
    @GetMapping("/nearest/{longInit}/{latInit}/{distance}/{spec}")
    public Iterable<Itineraire> getNearest(@PathVariable("spec") final String speciality, @PathVariable("latInit") final float latInit, @PathVariable("longInit") final float longInit, @PathVariable("distance") final int distance ){
        ArrayList<Itineraire> hospitalNearest = (ArrayList<Itineraire>) hospitalService.getNearest(latInit, longInit, distance, speciality);

        if (!hospitalNearest.isEmpty()){
            Collections.sort(hospitalNearest, Itineraire.sortByDistance);
            return hospitalNearest;
        } else if (Lists.newArrayList(getAllFreeHospitalInRangeWithSpeciality(speciality, latInit, longInit, distance)).isEmpty()){
            throw new NoHospitalFound("No hospital found in database with this speciality at this range");
        } else {
            throw new NoApiCommunication("Communication error with API. Switching to hospital/free/speciality/{spec}/range/{longInit}/{latInit}/{distance}");
        }
    }

}

