package ru.job4j.accidents.repository.rule;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@AllArgsConstructor
public class RuleJdbcRepository implements RuleRepository {

    private final JdbcTemplate jdbc;

    @Override
    public List<Rule> findAll() {
        return jdbc.query("""
                SELECT *
                FROM rules
                ORDER BY id DESC
                """,
                (rs, row) -> buildRule(rs));
    }

    @Override
    public Set<Rule> findByIds(List<Integer> ids) {
        if (ids.isEmpty()) {
            return Set.of();
        }
        String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));
        return new HashSet<>(jdbc.query(
                """
                        SELECT id, name
                        FROM rules
                        WHERE id IN (%s)
                        """.formatted(placeholders),
                (rs, row) -> buildRule(rs),
                ids.toArray()
        ));
    }

    private Rule buildRule(ResultSet rs) throws SQLException {
        Rule rule = new Rule();
        rule.setId(rs.getInt("id"));
        rule.setName(rs.getString("name"));
        return rule;
    }
}
