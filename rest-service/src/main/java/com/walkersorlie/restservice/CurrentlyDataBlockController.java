/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.walkersorlie.restservice;


import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Walker Sorlie
 */

@RestController
public class CurrentlyDataBlockController {
    
    private final CurrentlyDataBlockRepository repository;
    private final CurrentlyDataBlockModelAssembler assembler;
    
    
    CurrentlyDataBlockController(CurrentlyDataBlockRepository repository, CurrentlyDataBlockModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }
    
    
    @GetMapping("/currently")
    EntityModel<CurrentlyDataBlock> currently() {
        long unixTime = System.currentTimeMillis() / 1000L;
//        CurrentlyDataBlock currently = new CurrentlyDataBlock.Builder(44.0583333, -121.3141667, unixTime).build();
        System.out.println(Instant.ofEpochSecond(unixTime));
         
        CurrentlyDataBlock currently = repository.findFirstByTimeLessThanEqual(unixTime, Sort.by(DESC, "time")).setRequestTime(unixTime).build();
        
        System.out.println(currently.getTime());
        Instant instant = Instant.ofEpochSecond(currently.getTime());
        System.out.println(instant);
        
        return assembler.toModel(currently);
    }
    

}
