package com.walkersorlie.restservice.Controller;

import com.walkersorlie.restservice.Assembler.DailyDataBlockModelAssembler;
import com.walkersorlie.restservice.DataBlock.DailyDataBlock;
import com.walkersorlie.restservice.Exception.DailyDataBlockNotFoundException;
import com.walkersorlie.restservice.Repository.DailyDataBlockRepository;

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
public class DailyDataBlockController {

    private final DailyDataBlockRepository repository;
    private final DailyDataBlockModelAssembler assembler;

    DailyDataBlockController(DailyDataBlockRepository repository, DailyDataBlockModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/daily_collection/latest")
    public EntityModel<DailyDataBlock> latest() {
        return assembler.toModelLatest(getLatestDailyDataBlock());
    }

    @GetMapping("daily_collection/{id}")
    public EntityModel<DailyDataBlock> specific(@PathVariable String id) {

        DailyDataBlock result = repository.findById(id)
                .orElseThrow(() -> new DailyDataBlockNotFoundException(id));

        return assembler.toModel(result);
    }

    @GetMapping("/daily_collection")
    public CollectionModel<EntityModel<DailyDataBlock>> all() {
        List<EntityModel<DailyDataBlock>> dailyBlockDocuments = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        System.out.println(dailyBlockDocuments.size());
        return CollectionModel.of(dailyBlockDocuments, linkTo(methodOn(DailyDataBlockController.class).all()).withSelfRel());
    }

    private DailyDataBlock getLatestDailyDataBlock() {
        return repository.findFirstByTimeLessThanEqual(System.currentTimeMillis() / 1000L, Sort.by(DESC, "time"));
    }
}
