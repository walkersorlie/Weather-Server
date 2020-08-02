
package com.walkersorlie.restservice.Controller;

import com.walkersorlie.restservice.Assembler.CurrentlyDataBlockModelAssembler;
import com.walkersorlie.restservice.Exception.CurrentlyDataBlockNotFoundException;
import com.walkersorlie.restservice.Repository.CurrentlyDataBlockRepository;
import com.walkersorlie.restservice.DataBlock.CurrentlyDataBlock;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import static org.springframework.data.domain.Sort.Direction.DESC;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.MediaTypes.ALPS_JSON_VALUE;
import org.springframework.hateoas.PagedModel;
import static org.springframework.hateoas.mediatype.PropertyUtils.getExposedProperties;
import org.springframework.hateoas.mediatype.alps.Alps;
import static org.springframework.hateoas.mediatype.alps.Alps.doc;
import org.springframework.hateoas.mediatype.alps.Descriptor;
import org.springframework.hateoas.mediatype.alps.Ext;
import org.springframework.hateoas.mediatype.alps.Format;
import org.springframework.hateoas.mediatype.alps.Type;
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
    public static CurrentlyDataBlock LATEST;

    CurrentlyDataBlockController(CurrentlyDataBlockRepository repository, CurrentlyDataBlockModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
        
        LATEST = getLatestCurrentlyDataBlock();
    }

    @GetMapping("/api/currently_collection/latest")
    public EntityModel<CurrentlyDataBlock> latest() {
//        long unixTime = System.currentTimeMillis() / 1000L;
//        System.out.println(Instant.ofEpochSecond(unixTime));
//         
//        CurrentlyDataBlock latest = repository.findFirstByTimeLessThanEqual(unixTime, Sort.by(DESC, "time"));
//        
//        System.out.println(latest.getTime());
//        Instant instant = Instant.ofEpochSecond(latest.getTime());
//        System.out.println(instant);

        return assembler.toModel(LATEST);
    }

    @GetMapping("/api/currently_collection/{id}")
    public EntityModel<CurrentlyDataBlock> specific(@PathVariable String id) {

        CurrentlyDataBlock result = repository.findById(id)
                .orElseThrow(() -> new CurrentlyDataBlockNotFoundException(id));

        return assembler.toModel(result);
    }

    @GetMapping("/api/currently_collection_all_old")
    public CollectionModel<EntityModel<CurrentlyDataBlock>> allOld() {
        List<EntityModel<CurrentlyDataBlock>> currentlyBlockDocuments = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        List<EntityModel<CurrentlyDataBlock>> currently = repository.findAllBy(PageRequest.of(0, 5)).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(currently, linkTo(methodOn(CurrentlyDataBlockController.class).allOld()).withSelfRel());
    }
    
    @GetMapping("/api/currently_collection")
    public PagedModel<EntityModel<CurrentlyDataBlock>> all(Pageable pageable, PagedResourcesAssembler<CurrentlyDataBlock> pagedAssembler) 
    {
        Page<CurrentlyDataBlock> page = repository.findAllBy(pageable);
        System.out.println(page.getPageable());
//        CollectionModel<EntityModel<CurrentlyDataBlock>> coll = assembler.toCollectionModel(page);
//        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata((long)page.getSize(), (long)page.getNumber(), page.getTotalElements());
//         
//        PagedModel<EntityModel<CurrentlyDataBlock>> pagedModel = PagedModel.of(coll.iterator(),metadata, 
//                linkTo(methodOn(CurrentlyDataBlockController.class).allPagesTest(pageable, pagedAssembler)).withSelfRel());
        

        PagedModel<EntityModel<CurrentlyDataBlock>> pagedModel = pagedAssembler.toModel(page, assembler, 
                linkTo(methodOn(CurrentlyDataBlockController.class).all(pageable, pagedAssembler)).withSelfRel());
         
        return pagedModel;
    }

    private CurrentlyDataBlock getLatestCurrentlyDataBlock() {
        return repository.findFirstByTimeLessThanEqual(Instant.now().getEpochSecond(), Sort.by(DESC, "time"));
    }
    

    @GetMapping(value = "/api/currently_collection/profile", produces = ALPS_JSON_VALUE)
    public Alps profile() {
        return Alps.alps() 
                .doc(doc()
                        .href("https://example.org/samples/full/doc.html")
                        .value("value goes here") 
                        .format(Format.TEXT)
                        .build()) 
                .descriptor(getExposedProperties(CurrentlyDataBlock.class).stream()
                        .map(property -> Descriptor.builder()
                        .id("class field [" + property.getName() + "]") 
                        .name(property.getName()) 
                        .type(Type.SEMANTIC) 
                        .ext(Ext.builder() 
                                .id("ext [" + property.getName() + "]")
                                .href("https://example.org/samples/ext/" + property.getName())
                                .value("value goes here") 
                                .build()) 
                        .rt("rt for [" + property.getName() + "]") 
                        .descriptor(Collections.singletonList(Descriptor.builder().id("embedded").build()))
                        .build()) //
                        .collect(Collectors.toList()))
                .build();
    }
}
