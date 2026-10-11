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
@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class NotYetImplemented extends Exception {
  public NotYetImplemented(String message) {
      super(message);
  }
}
