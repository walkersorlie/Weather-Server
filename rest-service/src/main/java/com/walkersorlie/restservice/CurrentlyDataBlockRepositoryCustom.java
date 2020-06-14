/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.walkersorlie.restservice;

import com.walkersorlie.restservice.DataBlock.CurrentlyDataBlock;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Walker Sorlie
 */
public interface CurrentlyDataBlockRepositoryCustom {
    
    CurrentlyDataBlock findFirstByTimeLessThanEqual(long requestTime, Sort sort);
    
    List<CurrentlyDataBlock> findAllCustom(long requestTime);
    
}
