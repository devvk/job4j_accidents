package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentMemRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentMemRepository repository;
    private final RuleService ruleService;

    public List<Accident> getAll() {
        return repository.getAll();
    }

    public Optional<Accident> getById(Integer id) {
        return repository.findById(id);
    }

    public Accident save(Accident accident, List<Integer> ruleIds) {
        accident.setRules(ruleService.findByIds(ruleIds));
        return repository.save(accident);
    }

    public boolean update(Accident accident, List<Integer> ruleIds) {
        accident.setRules(ruleService.findByIds(ruleIds));
        return repository.update(accident);
    }

    public boolean delete(Integer id) {
        return repository.delete(id);
    }

    public List<AccidentType> getAccidentTypes() {
        return repository.getAccidentTypes();
    }

    public List<Rule> getRules() {
        return ruleService.findAll();
    }

    public Set<Integer> getSelectedRuleIds(Accident accident) {
        return accident.getRules().stream()
                .map(Rule::getId)
                .collect(Collectors.toSet());
    }
}
