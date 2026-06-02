package ru.job4j.accidents.repository.rule;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Primary
@Repository
@AllArgsConstructor
public class RuleSpringDataRepository implements RuleRepository {

    private final SpringDataRuleRepository repository;

    @Override
    public List<Rule> findAll() {
        return repository.findAll();
    }

    @Override
    public Set<Rule> findByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptySet();
        }
        return repository.findByIdIn(ids);
    }
}
