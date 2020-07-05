package com.walkersorlie.restservice.Assembler;

import com.walkersorlie.restservice.Controller.DailyDataBlockController;
import com.walkersorlie.restservice.DataBlock.DailyDataBlock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class DailyDataBlockModelAssembler implements RepresentationModelAssembler<DailyDataBlock, EntityModel<DailyDataBlock>> {

    @Override
    public EntityModel<DailyDataBlock> toModel(DailyDataBlock dailyDataBlock) {
        
        if (dailyDataBlock.equals(DailyDataBlockController.LATEST)) {
            return EntityModel.of(dailyDataBlock,
                    linkTo(methodOn(DailyDataBlockController.class).specific(dailyDataBlock.getId())).withSelfRel(),
                    linkTo(methodOn(DailyDataBlockController.class).all()).withRel("daily_collection"),
                    linkTo(methodOn(DailyDataBlockController.class).latest()).withSelfRel());
        }
        return EntityModel.of(dailyDataBlock,
                linkTo(methodOn(DailyDataBlockController.class).latest()).withSelfRel(),
                linkTo(methodOn(DailyDataBlockController.class).specific(dailyDataBlock.getId())).withSelfRel());
    }
    
    
    @Override
    public CollectionModel<EntityModel<DailyDataBlock>> toCollectionModel(Iterable<? extends DailyDataBlock> entities) {
        Iterator itr = entities.iterator();
        List<EntityModel<DailyDataBlock>> list = new ArrayList();
        
        while(itr.hasNext()) {
            list.add(toModel((DailyDataBlock)itr.next()));
        }
        
        CollectionModel<EntityModel<DailyDataBlock>> collection = new CollectionModel<>(list);
        return collection;
    }
}
