
package com.walkersorlie.restservice.Exception;

/**
 *
 * @author Walker Sorlie
 */
public class HourlyDataBlockNotFoundException extends RuntimeException {

    public HourlyDataBlockNotFoundException(String id) {
        super("Could not find data block " + id);
    }
}

