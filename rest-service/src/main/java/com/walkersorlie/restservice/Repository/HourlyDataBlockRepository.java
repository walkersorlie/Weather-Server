
package com.walkersorlie.restservice.Repository;

import com.walkersorlie.restservice.DataBlock.HourlyDataBlock;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author Walker Sorlie
 */

@RepositoryRestResource(collectionResourceRel = "collection_hourly", path = "hourly_collection")
public interface HourlyDataBlockRepository extends MongoRepository<HourlyDataBlock, String> {
    
    @Query(sort = "{ time : -1 }")
    List<HourlyDataBlock> findAllBy();
    
    HourlyDataBlock findFirstByTimeLessThanEqual(long requestTime, Sort sort);  

}
