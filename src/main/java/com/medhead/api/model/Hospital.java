package com.medhead.api.model;
import lombok.Data;

import javax.persistence.*;
/**
 *
 * @author stiven
 */
@Data
@Entity
@Table(name = "Hospital")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="hospital_name")
    private String name;
    
    private float longitude;
    
    private float latitude;
    
    @Column(name="spec")
    private String specialities;

    //private ArrayList<String> specialitiesSplitted;

    private String address;
    
    private Integer freebed;
    // @Trancient --> pour pas besoin de chamops dans la bdd
}
