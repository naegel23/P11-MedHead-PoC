/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.medhead.api.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import java.util.Comparator;

/**
 *
 * @author stiven
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Itineraire {
    private final Hospital hopitalDestination;
    private final float distanceGoal;
    private final float durationDrive;
    //add more attr if necessary --> Ajouter origine
    
    public Itineraire(Hospital hopital, float distance, float duree){
        this.hopitalDestination = hopital;
        this.distanceGoal=distance;
        this.durationDrive=duree;
    }
    
    public static Comparator<Itineraire> sortByDistance = (Itineraire t, Itineraire t1) -> (int) (t.distanceGoal-t1.distanceGoal);

    
}


