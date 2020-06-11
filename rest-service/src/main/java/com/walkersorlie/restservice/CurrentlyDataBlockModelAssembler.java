/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.walkersorlie.restservice;


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

    return EntityModel.of(currentlyDataBlock, 
            linkTo(methodOn(CurrentlyDataBlockController.class).currently()).withSelfRel());
  }
}
