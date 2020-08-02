
package com.walkersorlie.restservice.Controller;

import com.walkersorlie.restservice.Assembler.HourlyDataBlockModelAssembler;
import com.walkersorlie.restservice.DataBlock.HourlyDataBlock;
import com.walkersorlie.restservice.Exception.HourlyDataBlockNotFoundException;
import com.walkersorlie.restservice.Repository.HourlyDataBlockRepository;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import static org.springframework.data.domain.Sort.Direction.DESC;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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
    public static HourlyDataBlock LATEST;

    HourlyDataBlockController(HourlyDataBlockRepository repository, HourlyDataBlockModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
        LATEST = getLatestHourlyDataBlock();
    }

    @GetMapping("/api/hourly_collection/latest")
    public EntityModel<HourlyDataBlock> latest() {
        return assembler.toModel(LATEST);
    }

    @GetMapping("/api/hourly_collection/{id}")
    public EntityModel<HourlyDataBlock> specific(@PathVariable String id) {

        HourlyDataBlock result = repository.findById(id)
                .orElseThrow(() -> new HourlyDataBlockNotFoundException(id));

        return assembler.toModel(result);
    }

    @GetMapping("/api/hourly_collection_all_old")
    public CollectionModel<EntityModel<HourlyDataBlock>> allOld() {
        List<EntityModel<HourlyDataBlock>> hourlyBlockDocuments = repository.findAllBy().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(hourlyBlockDocuments, linkTo(methodOn(HourlyDataBlockController.class).allOld()).withSelfRel());
    }

    
    @GetMapping("/api/hourly_collection")
    public PagedModel<EntityModel<HourlyDataBlock>> all(Pageable pageable, PagedResourcesAssembler<HourlyDataBlock> pagedAssembler) 
    {
        Page<HourlyDataBlock> page = repository.findAllBy(pageable);
        PagedModel<EntityModel<HourlyDataBlock>> pagedModel = pagedAssembler.toModel(page, assembler, 
                linkTo(methodOn(HourlyDataBlockController.class).all(pageable, pagedAssembler)).withSelfRel());
         
        return pagedModel;
    }
    
    
    private HourlyDataBlock getLatestHourlyDataBlock() {
        return repository.findFirstByTimeLessThanEqual(Instant.now().getEpochSecond(), Sort.by(DESC, "time"));
    }

}
