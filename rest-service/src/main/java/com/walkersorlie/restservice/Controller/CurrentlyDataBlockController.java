
package com.walkersorlie.restservice.Controller;

import com.walkersorlie.restservice.Assembler.CurrentlyDataBlockModelAssembler;
import com.walkersorlie.restservice.Exception.CurrentlyDataBlockNotFoundException;
import com.walkersorlie.restservice.Repository.CurrentlyDataBlockRepository;
import com.walkersorlie.restservice.DataBlock.CurrentlyDataBlock;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import static org.springframework.data.domain.Sort.Direction.DESC;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/currently_collection/latest")
    public EntityModel<CurrentlyDataBlock> latest() {
//        long unixTime = System.currentTimeMillis() / 1000L;
//        System.out.println(Instant.ofEpochSecond(unixTime));
//         
//        CurrentlyDataBlock latest = repository.findFirstByTimeLessThanEqual(unixTime, Sort.by(DESC, "time"));
//        
//        System.out.println(latest.getTime());
//        Instant instant = Instant.ofEpochSecond(latest.getTime());
//        System.out.println(instant);

        return assembler.toModelLatest(getLatestCurrentlyDataBlock());
    }

    @GetMapping("currently_collection/{id}")
    public EntityModel<CurrentlyDataBlock> specific(@PathVariable String id) {

        CurrentlyDataBlock result = repository.findById(id)
                .orElseThrow(() -> new CurrentlyDataBlockNotFoundException(id));

        return assembler.toModel(result);
    }

    @GetMapping("/currently_collection")
    public CollectionModel<EntityModel<CurrentlyDataBlock>> all() {
        List<EntityModel<CurrentlyDataBlock>> currentlyBlockDocuments = repository.findAllBy().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(currentlyBlockDocuments, linkTo(methodOn(CurrentlyDataBlockController.class).all()).withSelfRel());
    }

    private CurrentlyDataBlock getLatestCurrentlyDataBlock() {
        return repository.findFirstByTimeLessThanEqual(Instant.now().getEpochSecond(), Sort.by(DESC, "time"));
    }
}
