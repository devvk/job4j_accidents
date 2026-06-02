package ru.job4j.accidents.repository.accidenttype;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Primary
@Repository
@AllArgsConstructor
public class AccidentTypeSpringDataRepository implements AccidentTypeRepository {

    private final SpringDataAccidentTypeRepository repository;

    @Override
    public List<AccidentType> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<AccidentType> findById(Integer id) {
        return repository.findById(id);
    }
}
