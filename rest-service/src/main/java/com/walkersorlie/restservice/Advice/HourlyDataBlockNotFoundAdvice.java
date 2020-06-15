package com.walkersorlie.restservice.Advice;

import com.walkersorlie.restservice.Exception.HourlyDataBlockNotFoundException;
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
public class HourlyDataBlockNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(HourlyDataBlockNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String HourlyDataBlockNotFoundHandler(HourlyDataBlockNotFoundException ex) {
        return ex.getMessage();
    }
}
