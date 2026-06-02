package ru.job4j.accidents.repository.rule;

import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Primary
@Repository
@AllArgsConstructor
public class RuleHibernateRepository implements RuleRepository {

    private final EntityManagerFactory entityManagerFactory;

    private Session openSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    @Override
    public List<Rule> findAll() {
        try (Session session = openSession()) {
            return session.createQuery("""
                            FROM Rule a
                            ORDER BY a.id
                            """, Rule.class)
                    .getResultList();
        }
    }

    @Override
    public Set<Rule> findByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptySet();
        }
        try (Session session = openSession()) {
            return Set.copyOf(
                    session.createQuery(
                                    """
                                            FROM Rule r
                                            WHERE r.id IN (:ids)
                                            """, Rule.class)
                            .setParameterList("ids", ids)
                            .getResultList()
            );
        }
    }
}
