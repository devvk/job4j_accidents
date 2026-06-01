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

    private final AccidentMemRepository accidentMemRepository;

    public List<Accident> getAll() {
        return accidentMemRepository.getAll();
    }

    public Optional<Accident> getById(Integer id) {
        return accidentMemRepository.getById(id);
    }

    public Accident save(Accident accident, List<Integer> ruleIds) {
        return accidentMemRepository.save(accident,  ruleIds);
    }

    public boolean update(Accident accident, List<Integer> ruleIds) {
        return accidentMemRepository.update(accident, ruleIds);
    }

    public boolean delete(Integer id) {
        return accidentMemRepository.delete(id);
    }

    public List<AccidentType> getAccidentTypes() {
        return accidentMemRepository.getAccidentTypes();
    }

    public List<Rule> getRules() { return accidentMemRepository.getRules(); }

    public Set<Integer> getSelectedRuleIds(Accident accident) {
        return accident.getRules().stream()
                .map(Rule::getId)
                .collect(Collectors.toSet());
    }
}
