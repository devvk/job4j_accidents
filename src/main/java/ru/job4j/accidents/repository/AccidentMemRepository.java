package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMemRepository {

    private final AtomicInteger counter = new AtomicInteger(0);

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public List<Accident> getAll() {
        return new ArrayList<>(accidents.values());
    }

    public Optional<Accident> getById(int id) {
        return accidents.get(id) != null ? Optional.of(accidents.get(id)) : Optional.empty();
    }

    public Accident save(Accident accident) {
        int id = counter.incrementAndGet();
        accident.setId(id);
        accidents.put(id, accident);
        return accident;
    }

    public boolean delete(int id) {
        return accidents.remove(id) != null;
    }

    public boolean update(Accident accident) {
        return accidents.put(accident.getId(), accident) != null;
    }
}
