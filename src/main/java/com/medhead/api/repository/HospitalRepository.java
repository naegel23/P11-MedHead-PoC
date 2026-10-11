/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.medhead.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.medhead.api.model.Hospital;
/**
 *
 * @author stiven
 */
@Repository
public interface HospitalRepository extends CrudRepository<Hospital, Integer>, HospitalRepositoryCustom{

}
