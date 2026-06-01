package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentRepository {

    List<Accident> getAll();

    Optional<Accident> findById(Integer id);

    Accident save(Accident accident, List<Integer> ruleIds);

    boolean update(Accident accident, List<Integer> ruleIds);

    boolean delete(Integer id);
}
