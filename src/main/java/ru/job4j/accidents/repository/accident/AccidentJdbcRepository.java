package ru.job4j.accidents.repository.accident;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentJdbcRepository implements AccidentRepository {

    private final JdbcTemplate jdbc;

    @Override
    public List<Accident> getAll() {
        List<Accident> accidents = jdbc.query("""
                        SELECT a.id,
                               a.name,
                               a.text,
                               a.address,
                               t.id AS type_id,
                               t.name AS type_name
                        FROM accidents a
                        JOIN accident_types t ON a.type_id = t.id
                        ORDER BY a.id DESC
                        """,
                (rs, row) -> buildAccident(rs));
        accidents.forEach(accident -> accident.setRules(findRulesByAccidentId(accident.getId())));
        return accidents;
    }

    @Override
    public Optional<Accident> findById(Integer id) {
        Optional<Accident> accidentOptional = jdbc.query("""
                        SELECT a.id,
                               a.name,
                               a.text,
                               a.address,
                               t.id AS type_id,
                               t.name AS type_name
                        FROM accidents a
                        JOIN accident_types t ON a.type_id = t.id
                        WHERE a.id = ?
                        """,
                (rs, row) -> buildAccident(rs),
                id).stream().findFirst();
        accidentOptional.ifPresent(accident -> accident.setRules(findRulesByAccidentId(accident.getId())));
        return accidentOptional;
    }

    private Set<Rule> findRulesByAccidentId(Integer accidentId) {
        return new HashSet<>(jdbc.query("""
                        SELECT r.id, r.name
                        FROM rules r
                        JOIN accident_rules ar ON r.id = ar.rule_id
                        WHERE ar.accident_id = ?
                        """,
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                },
                accidentId
        ));
    }

    private Accident buildAccident(ResultSet rs) throws SQLException {
        Accident accident = new Accident();
        accident.setId(rs.getInt("id"));
        accident.setName(rs.getString("name"));
        accident.setText(rs.getString("text"));
        accident.setAddress(rs.getString("address"));
        AccidentType type = new AccidentType();
        type.setId(rs.getInt("type_id"));
        type.setName(rs.getString("type_name"));
        accident.setType(type);
        return accident;
    }

    @Override
    @Transactional
    public Accident save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("""
                    INSERT INTO accidents (name, text, address, type_id)
                    VALUES (?, ?, ?, ?)
                    """, new String[] {"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);

        accident.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        saveRules(accident);
        return accident;
    }

    @Override
    @Transactional
    public boolean update(Accident accident) {
        int updated = jdbc.update("""
                        UPDATE accidents
                        SET name = ?, text = ?, address = ?, type_id = ?
                        WHERE id = ?
                        """,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId()
        );
        if (updated == 0) {
            return false;
        }
        jdbc.update("DELETE FROM accident_rules WHERE accident_id = ?", accident.getId());
        saveRules(accident);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        jdbc.update("DELETE FROM accident_rules WHERE accident_id = ?", id);
        return jdbc.update("DELETE FROM accidents WHERE id = ?", id) > 0;
    }

    private void saveRules(Accident accident) {
        if (accident.getRules() != null) {
            for (Rule rule : accident.getRules()) {
                jdbc.update("""
                                INSERT INTO accident_rules (accident_id, rule_id)
                                VALUES (?, ?)
                                """,
                        accident.getId(),
                        rule.getId()
                );
            }
        }
    }
}
