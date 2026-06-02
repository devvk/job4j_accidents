package ru.job4j.accidents.repository.rule;

import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Set;

public interface RuleRepository {

    List<Rule> findAll();

    Set<Rule> findByIds(List<Integer> ids);
}
