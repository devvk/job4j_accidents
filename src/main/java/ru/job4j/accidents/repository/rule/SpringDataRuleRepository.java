package ru.job4j.accidents.repository.rule;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Set;

public interface SpringDataRuleRepository extends JpaRepository<Rule, Integer> {

    Set<Rule> findByIdIn(List<Integer> ids);
}
