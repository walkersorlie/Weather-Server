/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.walkersorlie.restservice.Advice;


import com.walkersorlie.restservice.Exception.CurrentlyDataBlockNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 *
 * @author Walker Sorlie
 */

@ControllerAdvice
public class CurrentlyDataBlockNotFoundAdvice {
    
  @ResponseBody
  @ExceptionHandler(CurrentlyDataBlockNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String currentlyDataBlockNotFoundHandler(CurrentlyDataBlockNotFoundException ex) {
    return ex.getMessage();
  }
}