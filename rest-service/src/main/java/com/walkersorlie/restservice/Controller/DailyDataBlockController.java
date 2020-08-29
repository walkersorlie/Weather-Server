package com.walkersorlie.restservice.Controller;

import com.walkersorlie.restservice.Assembler.DailyDataBlockModelAssembler;
import com.walkersorlie.restservice.DataBlock.DailyDataBlock;
import com.walkersorlie.restservice.Exception.DailyDataBlockNotFoundException;
import com.walkersorlie.restservice.Repository.DailyDataBlockRepository;
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
public class DailyDataBlockController {

    private final DailyDataBlockRepository repository;
    private final DailyDataBlockModelAssembler assembler;
    public static DailyDataBlock LATEST;

    DailyDataBlockController(DailyDataBlockRepository repository, DailyDataBlockModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
        LATEST = getLatestDailyDataBlock();
    }

    @GetMapping("/api/daily_collection/latest")
    public EntityModel<DailyDataBlock> latest() {
        DailyDataBlock checkLatest = getLatestDailyDataBlock();
        if (!checkLatest.equals(LATEST))
            LATEST = checkLatest;
        
        return assembler.toModel(LATEST);
    }

    @GetMapping("/api/daily_collection/{id}")
    public EntityModel<DailyDataBlock> specific(@PathVariable String id) {
        DailyDataBlock checkLatest = getLatestDailyDataBlock();
        if (!checkLatest.equals(LATEST))
            LATEST = checkLatest;

        DailyDataBlock result = repository.findById(id)
                .orElseThrow(() -> new DailyDataBlockNotFoundException(id));

        return assembler.toModel(result);
    }

    @GetMapping("/api/daily_collection_all_old")
    public CollectionModel<EntityModel<DailyDataBlock>> allOld() {
        DailyDataBlock checkLatest = getLatestDailyDataBlock();
        if (!checkLatest.equals(LATEST))
            LATEST = checkLatest;
        
        List<EntityModel<DailyDataBlock>> dailyBlockDocuments = repository.findAllBy().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(dailyBlockDocuments, linkTo(methodOn(DailyDataBlockController.class).allOld()).withSelfRel());
    }
    
    @GetMapping("/api/daily_collection")
    public PagedModel<EntityModel<DailyDataBlock>> all(Pageable pageable, PagedResourcesAssembler<DailyDataBlock> pagedAssembler) {
        DailyDataBlock checkLatest = getLatestDailyDataBlock();
        if (!checkLatest.equals(LATEST))
            LATEST = checkLatest;
    
        Page<DailyDataBlock> page = repository.findAllBy(pageable);
        
        PagedModel<EntityModel<DailyDataBlock>> pagedModel = pagedAssembler.toModel(page, assembler, 
                linkTo(methodOn(DailyDataBlockController.class).all(pageable, pagedAssembler)).withSelfRel());
         
        return pagedModel;
    }

    private DailyDataBlock getLatestDailyDataBlock() {
        return repository.findFirstByTimeLessThanEqual(Instant.now().getEpochSecond(), Sort.by(DESC, "time"));
    }
}
