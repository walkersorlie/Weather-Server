/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.walkersorlie.restservice;

import com.walkersorlie.restservice.DataBlock.CurrentlyDataBlock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.data.mongodb.core.query.Query;
import static org.springframework.data.mongodb.core.query.Query.query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;


/**
 *
 * @author Walker Sorlie
 */
@Component
@RepositoryRestResource(collectionResourceRel = "collection_currently", path = "currently_collection_test")
public class CurrentlyDataBlockRepositoryCustomImpl implements CurrentlyDataBlockRepositoryCustom { 
    
    @Autowired
    MongoTemplate mongoTemplate;
    
    @Override
    public CurrentlyDataBlock findFirstByTimeLessThanEqual(long requestTime, Sort sort) {
        Query query = new Query(Criteria.where("time").lt(requestTime)).with(sort);
        
        CurrentlyDataBlock result = mongoTemplate.query(CurrentlyDataBlock.class)
                .matching(query(where("time").lt(requestTime)).with(sort))
                .first()
                .get();
                
        return result;
//        CurrentlyDataBlock currently = template.query(CurrentlyDataBlock.class)
//                   .matching(query(with(sort).where("time").lt(requestTime)));
//                   
// 
    }
    
    
    @Override
    public List<CurrentlyDataBlock> findAllCustom(long requestTime) {
        List<CurrentlyDataBlock> currentlyAll = mongoTemplate.findAll(CurrentlyDataBlock.class);
        
        return currentlyAll;
    }
}
