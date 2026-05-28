package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentMemRepository {

    Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public List<Accident> getAll() {
        return new ArrayList<>(accidents.values());
    }

    public Accident getById(int id) {
        return accidents.get(id);
    }
}
