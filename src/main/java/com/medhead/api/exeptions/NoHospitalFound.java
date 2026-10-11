/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.medhead.api.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author stiven
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoHospitalFound extends RuntimeException{
    public NoHospitalFound(String message) {
      super(message);
  }
}
