package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.rule.RuleMemRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RuleService {

    private final RuleMemRepository repository;

    public List<Rule> findAll() {
        return repository.findAll();
    }

    public Optional<Rule> findById(Integer id) {
        return repository.findById(id);
    }

    public Set<Rule> findByIds(List<Integer> ids) {
        return repository.findByIds(ids);
    }
}
