package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;

@Repository
@AllArgsConstructor
public class AccidentJdbcRepository {

    private final JdbcTemplate jdbc;

    public Accident save(Accident accident) {
        jdbc.update("INSERT INTO accidents (name) VALUES (?)",
                accident.getName());
        return accident;
    }

    public List<Accident> getAll() {
        return jdbc.query("SELECT id, name FROM accidents",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    return accident;
                });
    }
}
