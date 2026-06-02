package ru.job4j.accidents.repository.rule;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class RuleMemRepository implements RuleRepository {

    private final AtomicInteger counter = new AtomicInteger(0);

    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    public RuleMemRepository() {
        save(new Rule(null, "Статья 1"));
        save(new Rule(null, "Статья 2"));
        save(new Rule(null, "Статья 3"));
    }

    public Rule save(Rule rule) {
        int id = counter.incrementAndGet();
        rule.setId(id);
        rules.put(id, rule);
        return rule;
    }

    @Override
    public List<Rule> findAll() {
        return new ArrayList<>(rules.values());
    }

    @Override
    public Set<Rule> findByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Set.of();
        }
        return ids.stream()
                .map(this::findById)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }

    private Optional<Rule> findById(Integer id) {
        return Optional.ofNullable(rules.get(id));
    }
}
