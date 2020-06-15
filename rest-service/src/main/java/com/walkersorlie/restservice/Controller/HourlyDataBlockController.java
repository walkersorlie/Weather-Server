
package com.walkersorlie.restservice.Controller;

import com.walkersorlie.restservice.Assembler.HourlyDataBlockModelAssembler;
import com.walkersorlie.restservice.DataBlock.HourlyDataBlock;
import com.walkersorlie.restservice.Exception.HourlyDataBlockNotFoundException;
import com.walkersorlie.restservice.Repository.HourlyDataBlockRepository;
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
public class HourlyDataBlockController {
    
    
    private final HourlyDataBlockRepository repository;
    private final HourlyDataBlockModelAssembler assembler;

    HourlyDataBlockController(HourlyDataBlockRepository repository, HourlyDataBlockModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/hourly_collection/latest")
    public EntityModel<HourlyDataBlock> latest() {
        return assembler.toModelLatest(getLatestHourlyDataBlock());
    }

    @GetMapping("hourly_collection/{id}")
    public EntityModel<HourlyDataBlock> specific(@PathVariable String id) {

        HourlyDataBlock result = repository.findById(id)
                .orElseThrow(() -> new HourlyDataBlockNotFoundException(id));

        return assembler.toModel(result);
    }

    @GetMapping("/hourly_collection")
    public CollectionModel<EntityModel<HourlyDataBlock>> all() {
        List<EntityModel<HourlyDataBlock>> hourlyBlockDocuments = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        System.out.println(hourlyBlockDocuments.size());
        return CollectionModel.of(hourlyBlockDocuments, linkTo(methodOn(HourlyDataBlockController.class).all()).withSelfRel());
    }

    private HourlyDataBlock getLatestHourlyDataBlock() {
        return repository.findFirstByTimeLessThanEqual(System.currentTimeMillis() / 1000L, Sort.by(DESC, "time"));
    }

}
