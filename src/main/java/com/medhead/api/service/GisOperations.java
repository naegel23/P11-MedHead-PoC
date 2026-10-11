/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.medhead.api.service;

import com.medhead.api.model.Hospital;
import com.medhead.api.model.Itineraire;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 * @author stiven
 */
@Service
public class GisOperations {

    public static double distFrom(double longAmbulance, double latAmbulance, double longHopital, double latHopital) {
        double earthRadius = 6371.0; // 3958.75 miles (ou 6371.0 kilometers)
        double dLat = Math.toRadians(latHopital-latAmbulance);
        double dLng = Math.toRadians(longHopital-longAmbulance);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(latAmbulance)) * Math.cos(Math.toRadians(latHopital));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return earthRadius * c;
    }
    
    /**
     *
     * @param latCentre - Origin's Latitude (ex : 51.525756)
     * @param longCentre - Origin's Longitude (ex : -0.712585)
     * @param listHospital - List of all hospital selected in method distFrom
     * @return - Return a new ordinate list of hospital
     */
    public static Iterable<Itineraire> getItineraireOSRM(float longCentre, float latCentre, Iterable<Hospital> listHospital) {

        String apiUrl = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=";
        String apiKey ="5b3ce3597851110001cf6248dc9db6fb880d46b7b63a907d42d826e5" ;
        ArrayList<Itineraire> myItineraireList = new ArrayList<>();
        String origin = latCentre+","+longCentre;
        String requestUrl = apiUrl+apiKey+"&start="+origin+"&end=";

        RestTemplate restTemplate = new RestTemplate();
        final float[] distanceTrajet = {0};
        final float[] tempsTrajet = {0};
        final JSONObject[] reponseParsed = {null};
        final JSONArray[] reponseParsedArray = {null};

        Stream<Hospital> streamedList = StreamSupport.stream(listHospital.spliterator(),true);
        Instant start = Instant.now();
        streamedList.parallel().forEach(hopital -> {
            try {
                ResponseEntity<String> reponse = restTemplate.getForEntity(requestUrl+hopital.getLongitude()+","+hopital.getLatitude(), String.class);
                reponseParsed[0] = new JSONObject(reponse.getBody());
                reponseParsedArray[0] = reponseParsed[0].getJSONArray("features");
                reponseParsed[0] = reponseParsedArray[0].getJSONObject(0);
                reponseParsed[0] = reponseParsed[0].getJSONObject("properties");
                reponseParsed[0] = reponseParsed[0].getJSONObject("summary");
                distanceTrajet[0] = reponseParsed[0].getFloat("distance")/1000;
                tempsTrajet[0] = reponseParsed[0].getFloat("duration");

                myItineraireList.add(new Itineraire(hopital, distanceTrajet[0], tempsTrajet[0]));
            } catch (Exception ex) {
                Logger.getLogger(GisOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Duree : "+ tempsTrajet[0] +" -- "+hopital.getLongitude()+","+hopital.getLatitude()+"--"+ distanceTrajet[0] +" Km");
        });

        Instant finish = Instant.now();
        System.out.println("Duree de la methode : "+ Duration.between(start, finish).toMillis());
        return myItineraireList;   
    }

}


