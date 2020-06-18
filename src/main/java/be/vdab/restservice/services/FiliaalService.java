package be.vdab.restservice.services;

import be.vdab.restservice.domain.Filiaal;

import java.util.List;
import java.util.Optional;

public interface FiliaalService {
    Optional<Filiaal> findById(long id);
    List<Filiaal> findAll();
    void create(Filiaal filiaal);
    void update(Filiaal filiaal);
    void delete(long id);
}
