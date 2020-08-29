
package com.walkersorlie.restservice.Repository;


import com.walkersorlie.restservice.DataBlock.CurrentlyDataBlock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author Walker Sorlie
 */

@RepositoryRestResource(collectionResourceRel = "collection_currently", path = "currently_collection")
public interface CurrentlyDataBlockRepository extends MongoRepository<CurrentlyDataBlock, String> {   
    
    @Query(sort = "{time: -1}")
    Page<CurrentlyDataBlock> findAllBy(Pageable p);
        
    CurrentlyDataBlock findFirstByTimeLessThanEqual(long requestTime, Sort sort);        
}