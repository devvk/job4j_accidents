package ru.job4j.accidents.repository.accident;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.accidents.model.Accident;

public interface SpringDataAccidentRepository extends JpaRepository<Accident, Integer> {
}
