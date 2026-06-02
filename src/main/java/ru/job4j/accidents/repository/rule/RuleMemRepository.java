package ru.job4j.accidents.repository.rule;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class RuleMemRepository {

    private final Map<Integer, Rule> rules = Map.of(
            1, new Rule(1, "Статья 1"),
            2, new Rule(2, "Статья 2"),
            3, new Rule(3, "Статья 3")
    );

    public List<Rule> findAll() {
        return new ArrayList<>(rules.values());
    }

    public Optional<Rule> findById(Integer id) {
        return Optional.ofNullable(rules.get(id));
    }

    public Set<Rule> findByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Set.of();
        }
        return ids.stream()
                .map(this::findById)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }
}
