package ru.job4j.accidents.repository.accident;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentRepository {

    List<Accident> getAll();

    Optional<Accident> findById(Integer id);

    Accident save(Accident accident);

    boolean update(Accident accident);

    boolean delete(Integer id);
}
