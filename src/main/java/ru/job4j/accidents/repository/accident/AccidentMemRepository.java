package ru.job4j.accidents.repository.accident;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMemRepository implements AccidentRepository {

    private final AtomicInteger counter = new AtomicInteger(0);

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    @Override
    public List<Accident> getAll() {
        return new ArrayList<>(accidents.values());
    }

    @Override
    public Optional<Accident> findById(Integer id) {
        return Optional.ofNullable(accidents.get(id));
    }

    @Override
    public Accident save(Accident accident) {
        int id = counter.incrementAndGet();
        accident.setId(id);
        accidents.put(id, accident);
        return accident;
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.put(accident.getId(), accident) != null;
    }

    @Override
    public boolean delete(Integer id) {
        return accidents.remove(id) != null;
    }
}
