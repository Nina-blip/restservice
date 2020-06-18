package be.vdab.restservice.restcontrollers;

import be.vdab.restservice.domain.Filiaal;
import be.vdab.restservice.exceptions.FiliaalNietGevondenException;
import be.vdab.restservice.services.FiliaalService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/filialen")
@ExposesResourceFor(Filiaal.class)
class FiliaalController {
    private final FiliaalService filiaalService;
    private final EntityLinks entityLinks;

    public FiliaalController(FiliaalService filiaalService, EntityLinks entityLinks) {
        this.filiaalService = filiaalService;
        this.entityLinks = entityLinks;
    }

    @GetMapping("{id}")
    EntityModel<Filiaal> get(@PathVariable long id){
        return filiaalService.findById(id).map(filiaal -> EntityModel.of(filiaal, entityLinks.linkToItemResource(Filiaal.class, filiaal.getId()),
                entityLinks.linkForItemResource(Filiaal.class, filiaal.getId()).slash("werknemers").withRel("werknemers")))
                .orElseThrow(FiliaalNietGevondenException::new);
    }

    @ExceptionHandler(FiliaalNietGevondenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void filiaalNietGevonden(){
    };

    @DeleteMapping("{id}")
    void delete(@PathVariable long id){
        filiaalService.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    HttpHeaders post(@RequestBody @Valid Filiaal filiaal){
        filiaalService.create(filiaal);
        var link = entityLinks.linkToItemResource(Filiaal.class, filiaal.getId());
        var header = new HttpHeaders();
        header.setLocation(link.toUri());
        return header;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> verkeerdeData(MethodArgumentNotValidException ex){
        return ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }

    @PutMapping("{id}")
    void put(@RequestBody @Valid Filiaal filiaal){
        filiaalService.update(filiaal);
    }

    @GetMapping
    CollectionModel<EntityModel<FiliaalIdNaam>> findAll(){
        return CollectionModel.of(filiaalService.findAll().stream()
        .map(filiaal -> EntityModel.of(new FiliaalIdNaam(filiaal),
                entityLinks.linkToItemResource(Filiaal.class, filiaal.getId())))
                        .collect(Collectors.toList()),
                entityLinks.linkToCollectionResource(Filiaal.class));
    }

    private static class FiliaalIdNaam{
        private final long id;
        private final String naam;

        public FiliaalIdNaam(Filiaal filiaal) {
            this.id = filiaal.getId();
            this.naam = filiaal.getNaam();
        }

        public long getId() {
            return id;
        }

        public String getNaam() {
            return naam;
        }
    }
}
