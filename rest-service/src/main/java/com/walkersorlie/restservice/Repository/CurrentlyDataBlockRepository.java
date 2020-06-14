/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.walkersorlie.restservice.Repository;


import com.walkersorlie.restservice.DataBlock.CurrentlyDataBlock;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author Walker Sorlie
 */

@RepositoryRestResource(collectionResourceRel = "collection_currently", path = "currently_collection")
public interface CurrentlyDataBlockRepository extends MongoRepository<CurrentlyDataBlock, String> {
        
    CurrentlyDataBlock findFirstByTimeLessThanEqual(long requestTime, Sort sort);        
}