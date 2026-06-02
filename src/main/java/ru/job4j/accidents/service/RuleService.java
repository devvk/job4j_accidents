package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.rule.RuleRepository;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RuleService {

    private final RuleRepository ruleRepository;

    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

    public Set<Rule> findByIds(List<Integer> ids) {
        return ruleRepository.findByIds(ids);
    }
}
