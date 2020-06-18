package be.vdab.restservice.restcontrollers;

import be.vdab.restservice.domain.Filiaal;
import be.vdab.restservice.services.FiliaalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filialen")
class FiliaalController {
    private final FiliaalService filiaalService;

    public FiliaalController(FiliaalService filiaalService) {
        this.filiaalService = filiaalService;
    }

    @GetMapping("{id}")
    Filiaal get(@PathVariable long id){
        return filiaalService.findById(id).get();
    }
}
