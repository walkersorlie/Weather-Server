/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.walkersorlie.restservice.Assembler;

import com.walkersorlie.restservice.Controller.CurrentlyDataBlockController;
import com.walkersorlie.restservice.DataBlock.CurrentlyDataBlock;
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
public class CurrentlyDataBlockModelAssembler implements RepresentationModelAssembler<CurrentlyDataBlock, EntityModel<CurrentlyDataBlock>> {

    @Override
    public EntityModel<CurrentlyDataBlock> toModel(CurrentlyDataBlock currentlyDataBlock) {
        if (currentlyDataBlock.equals(CurrentlyDataBlockController.LATEST)) {
            return EntityModel.of(currentlyDataBlock,
                linkTo(methodOn(CurrentlyDataBlockController.class).specific(currentlyDataBlock.getId())).withSelfRel(),
                linkTo(methodOn(CurrentlyDataBlockController.class).all()).withRel("currently_collection"),
                linkTo(methodOn(CurrentlyDataBlockController.class).latest()).withSelfRel());
        }
        
        return EntityModel.of(currentlyDataBlock,
                linkTo(methodOn(CurrentlyDataBlockController.class).specific(currentlyDataBlock.getId())).withSelfRel(),
                linkTo(methodOn(CurrentlyDataBlockController.class).all()).withRel("currently_collection"));
    }
    
    @Override
    public CollectionModel<EntityModel<CurrentlyDataBlock>> toCollectionModel(Iterable<? extends CurrentlyDataBlock> entities) {
        Iterator itr = entities.iterator();
        List<EntityModel<CurrentlyDataBlock>> list = new ArrayList();
        
        while(itr.hasNext()) {
            list.add(toModel((CurrentlyDataBlock)itr.next()));
        }
        
        CollectionModel<EntityModel<CurrentlyDataBlock>> collection = new CollectionModel<>(list);
        return collection;
    }
}
