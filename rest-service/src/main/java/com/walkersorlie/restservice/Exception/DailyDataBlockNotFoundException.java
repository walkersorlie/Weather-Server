
package com.walkersorlie.restservice.Exception;

/**
 *
 * @author Walker Sorlie
 */
public class DailyDataBlockNotFoundException extends RuntimeException {
    
    public DailyDataBlockNotFoundException(String id) {
        super("Could not find data block " + id);
  }
}
