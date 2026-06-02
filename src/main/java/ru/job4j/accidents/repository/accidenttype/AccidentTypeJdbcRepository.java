package ru.job4j.accidents.repository.accidenttype;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
@AllArgsConstructor
public class AccidentTypeJdbcRepository implements AccidentTypeRepository {

    private final JdbcTemplate jdbc;

    @Override
    public List<AccidentType> findAll() {
        return jdbc.query("""
                SELECT *
                FROM accident_types
                ORDER BY id DESC
                """,
                (rs, row) -> buildAccidentType(rs));
    }

    @Override
    public Optional<AccidentType> findById(Integer id) {
        return jdbc.query("""
                                 SELECT *
                                 FROM accident_types
                                 WHERE id = ?
                                """,
                        (rs, row) -> buildAccidentType(rs), id)
                .stream().findFirst();
    }

    private AccidentType buildAccidentType(ResultSet rs) throws SQLException {
        AccidentType accidentType = new AccidentType();
        accidentType.setId(rs.getInt("id"));
        accidentType.setName(rs.getString("name"));
        return accidentType;
    }
}
