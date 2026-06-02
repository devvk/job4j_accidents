package ru.job4j.accidents.repository.rule;

import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RuleRepository {

    List<Rule> findAll();

    Optional<Rule> findById(Integer id);

    Set<Rule> findByIds(List<Integer> ids);
}
