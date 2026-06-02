package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.accident.AccidentRepository;
import ru.job4j.accidents.repository.accidenttype.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository accidentTypeRepository;
    private final RuleService ruleService;

    public List<Accident> getAll() {
        return accidentRepository.getAll();
    }

    public Optional<Accident> getById(Integer id) {
        return accidentRepository.findById(id);
    }

    public Accident save(Accident accident, List<Integer> ruleIds) {
        accidentTypeRepository.findById(accident.getType().getId())
                .ifPresent(accident::setType);
        accident.setRules(ruleService.findByIds(ruleIds));
        return accidentRepository.save(accident);
    }

    public boolean update(Accident accident, List<Integer> ruleIds) {
        accidentTypeRepository.findById(accident.getType().getId())
                .ifPresent(accident::setType);
        accident.setRules(ruleService.findByIds(ruleIds));
        return accidentRepository.update(accident);
    }

    public boolean delete(Integer id) {
        return accidentRepository.delete(id);
    }

    public List<AccidentType> getAccidentTypes() {
        return accidentTypeRepository.findAll();
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
