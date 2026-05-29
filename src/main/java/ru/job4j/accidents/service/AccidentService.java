package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentMemRepository;

import java.util.List;
import java.util.Optional;

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

    public Accident save(Accident accident) {
        return accidentMemRepository.save(accident);
    }

    public boolean delete(Integer id) {
        return accidentMemRepository.delete(id);
    }

    public boolean update(Accident accident) {
        return accidentMemRepository.update(accident);
    }

    public List<AccidentType> getAccidentTypes() {
        return accidentMemRepository.getAccidentTypes();
    }
}
