/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.walkersorlie.restservice;



import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author Walker Sorlie
 */

@RepositoryRestResource(collectionResourceRel = "collection_currently", path = "currently")
public interface CurrentlyDataBlockRepository extends MongoRepository<CurrentlyDataBlock.Builder, String> {
        
    CurrentlyDataBlock.Builder findFirstByTimeLessThanEqual(long requestTime, Sort sort);
        
}