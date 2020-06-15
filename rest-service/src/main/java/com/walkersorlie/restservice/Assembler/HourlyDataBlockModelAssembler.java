
package com.walkersorlie.restservice.Assembler;

import com.walkersorlie.restservice.Controller.HourlyDataBlockController;
import com.walkersorlie.restservice.DataBlock.HourlyDataBlock;

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
    public EntityModel<HourlyDataBlock> toModel(HourlyDataBlock HourlyDataBlock) {

        return EntityModel.of(HourlyDataBlock,
                linkTo(methodOn(HourlyDataBlockController.class).specific(HourlyDataBlock.getId())).withSelfRel(),
                linkTo(methodOn(HourlyDataBlockController.class).all()).withRel("Hourly_collection"));
    }

    public EntityModel<HourlyDataBlock> toModelLatest(HourlyDataBlock HourlyDataBlock) {

        return EntityModel.of(HourlyDataBlock,
                linkTo(methodOn(HourlyDataBlockController.class).latest()).withSelfRel(),
                linkTo(methodOn(HourlyDataBlockController.class).specific(HourlyDataBlock.getId())).withSelfRel(),
                linkTo(methodOn(HourlyDataBlockController.class).all()).withRel("Hourly_collection"));
    }
}
