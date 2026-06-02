package ru.job4j.accidents.repository.accidenttype;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AccidentTypeMemRepository implements AccidentTypeRepository {

    private final Map<Integer, AccidentType> types = Map.of(
            1, new AccidentType(1, "Две машины"),
            2, new AccidentType(2, "Машина и человек"),
            3, new AccidentType(3, "Машина и велосипед")
    );

    @Override
    public List<AccidentType> findAll() {
        return new ArrayList<>(types.values());
    }

    @Override
    public Optional<AccidentType> findById(Integer id) {
        return Optional.ofNullable(types.get(id));
    }
}
