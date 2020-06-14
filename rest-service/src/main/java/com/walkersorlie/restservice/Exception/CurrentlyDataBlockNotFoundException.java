/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.walkersorlie.restservice.Exception;


/**
 *
 * @author Walker Sorlie
 */
public class CurrentlyDataBlockNotFoundException extends RuntimeException {

    public CurrentlyDataBlockNotFoundException(String id) {
        super("Could not find data block " + id);
    }
}
