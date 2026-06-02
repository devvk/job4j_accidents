package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMemRepository {

    private final AtomicInteger counter = new AtomicInteger(0);

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public List<Accident> getAll() {
        return new ArrayList<>(accidents.values());
    }

    public Optional<Accident> findById(Integer id) {
        return Optional.ofNullable(accidents.get(id));
    }

    public Accident save(Accident accident) {
        int id = counter.incrementAndGet();
        accident.setId(id);

        var typeOptional = findAccidentTypeById(accident.getType().getId());
        typeOptional.ifPresent(accident::setType);

        accidents.put(id, accident);
        return accident;
    }

    public boolean update(Accident accident) {
        var typeOptional = findAccidentTypeById(accident.getType().getId());
        typeOptional.ifPresent(accident::setType);
        return accidents.put(accident.getId(), accident) != null;
    }

    public boolean delete(Integer id) {
        return accidents.remove(id) != null;
    }

    public List<AccidentType> getAccidentTypes() {
        return List.of(
                new AccidentType(1, "Две машины"),
                new AccidentType(2, "Машина и человек"),
                new AccidentType(3, "Машина и велосипед")
        );
    }

    public Optional<AccidentType> findAccidentTypeById(Integer id) {
        return getAccidentTypes().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }
}
