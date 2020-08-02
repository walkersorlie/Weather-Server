
package com.walkersorlie.restservice.Assembler;

import com.walkersorlie.restservice.Controller.HourlyDataBlockController;
import com.walkersorlie.restservice.DataBlock.HourlyDataBlock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 *
 * @author Walker Sorlie
 */
@Component
public class HourlyDataBlockModelAssembler implements RepresentationModelAssembler<HourlyDataBlock, EntityModel<HourlyDataBlock>> {
    
    @Override
    public EntityModel<HourlyDataBlock> toModel(HourlyDataBlock hourlyDataBlock) {
        PagedResourcesAssembler<HourlyDataBlock> pagedAssembler = new PagedResourcesAssembler(null, null);
        
        if (hourlyDataBlock.equals(HourlyDataBlockController.LATEST)) {
            return EntityModel.of(hourlyDataBlock,
                    linkTo(methodOn(HourlyDataBlockController.class).specific(hourlyDataBlock.getId())).withSelfRel(),
                    linkTo(methodOn(HourlyDataBlockController.class).all(PageRequest.of(0, 4), pagedAssembler)).withRel("daily_collection"),
                    linkTo(methodOn(HourlyDataBlockController.class).latest()).withSelfRel());
        }
        return EntityModel.of(hourlyDataBlock,
                linkTo(methodOn(HourlyDataBlockController.class).specific(hourlyDataBlock.getId())).withSelfRel(),
                linkTo(methodOn(HourlyDataBlockController.class).all(PageRequest.of(0, 4), pagedAssembler)).withRel("daily_collection"));
    }
    
    
    @Override
    public CollectionModel<EntityModel<HourlyDataBlock>> toCollectionModel(Iterable<? extends HourlyDataBlock> entities) {
        Iterator itr = entities.iterator();
        List<EntityModel<HourlyDataBlock>> list = new ArrayList();
        
        while(itr.hasNext()) {
            list.add(toModel((HourlyDataBlock)itr.next()));
        }
        
        CollectionModel<EntityModel<HourlyDataBlock>> collection = new CollectionModel<>(list);
        return collection;
    }
}
