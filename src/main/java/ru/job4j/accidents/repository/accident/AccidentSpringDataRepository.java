package ru.job4j.accidents.repository.accident;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

@Primary
@Repository
@AllArgsConstructor
public class AccidentSpringDataRepository implements AccidentRepository {

    private final SpringDataAccidentRepository repository;

    @Override
    public List<Accident> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public Optional<Accident> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Accident save(Accident accident) {
        return repository.save(accident);
    }

    @Override
    public boolean update(Accident accident) {
        repository.save(accident);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
