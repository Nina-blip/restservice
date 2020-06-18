package be.vdab.restservice.services;

import be.vdab.restservice.domain.Filiaal;
import be.vdab.restservice.exceptions.FiliaalNietGevondenException;
import be.vdab.restservice.repositories.FiliaalRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DefaultFiliaalService implements FiliaalService {
    private final FiliaalRepository filiaalRepository;

    public DefaultFiliaalService(FiliaalRepository filiaalRepository) {
        this.filiaalRepository = filiaalRepository;
    }

    @Override
    public Optional<Filiaal> findById(long id) {
        return filiaalRepository.findById(id);
    }

    @Override
    public List<Filiaal> findAll() {
        return filiaalRepository.findAll();
    }

    @Override
    public void create(Filiaal filiaal) {
        filiaalRepository.save(filiaal);
    }

    @Override
    public void update(Filiaal filiaal) {
        filiaalRepository.save(filiaal);
    }

    @Override
    public void delete(long id) {
        try {
            filiaalRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex){
            throw new FiliaalNietGevondenException();
        }
    }
}
