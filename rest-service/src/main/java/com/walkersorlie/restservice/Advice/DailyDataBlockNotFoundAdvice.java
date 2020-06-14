
package com.walkersorlie.restservice.Advice;


import com.walkersorlie.restservice.Exception.DailyDataBlockNotFoundException;

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
public class DailyDataBlockNotFoundAdvice {
 
    @ResponseBody
    @ExceptionHandler(DailyDataBlockNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String currentlyDataBlockNotFoundHandler(DailyDataBlockNotFoundException ex) {
        return ex.getMessage();
    }
}
