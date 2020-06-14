package com.walkersorlie.restservice.Assembler;

import com.walkersorlie.restservice.Controller.DailyDataBlockController;
import com.walkersorlie.restservice.DataBlock.DailyDataBlock;

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

        return EntityModel.of(dailyDataBlock,
                linkTo(methodOn(DailyDataBlockController.class).specific(dailyDataBlock.getId())).withSelfRel(),
                linkTo(methodOn(DailyDataBlockController.class).all()).withRel("daily_collection"));
    }

    public EntityModel<DailyDataBlock> toModelLatest(DailyDataBlock dailyDataBlock) {

        return EntityModel.of(dailyDataBlock,
                linkTo(methodOn(DailyDataBlockController.class).latest()).withSelfRel(),
                linkTo(methodOn(DailyDataBlockController.class).specific(dailyDataBlock.getId())).withSelfRel(),
                linkTo(methodOn(DailyDataBlockController.class).all()).withRel("daily_collection"));
    }
}
