package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMemRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentMemRepository accidentMemRepository;

    public List<Accident> getAll() {
        return accidentMemRepository.getAll();
    }

    public Accident getById(int id) {
        return accidentMemRepository.getById(id);
    }
}
