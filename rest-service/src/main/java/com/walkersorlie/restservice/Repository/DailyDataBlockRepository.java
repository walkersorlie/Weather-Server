
package com.walkersorlie.restservice.Repository;


import com.walkersorlie.restservice.DataBlock.DailyDataBlock;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author Walker Sorlie
 */

@RepositoryRestResource(collectionResourceRel = "collection_daily", path = "daily_collection")
public interface DailyDataBlockRepository extends MongoRepository<DailyDataBlock, String> {
        
    DailyDataBlock findFirstByTimeLessThanEqual(long requestTime, Sort sort);        
}