package ru.job4j.accidents.repository.accidenttype;

import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Primary
@Repository
@AllArgsConstructor
public class AccidentTypeHibernateRepository implements AccidentTypeRepository {

    private final EntityManagerFactory entityManagerFactory;

    private Session openSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    @Override
    public List<AccidentType> findAll() {
        try (Session session = openSession()) {
            return session.createQuery("""
                                FROM AccidentType a
                                ORDER BY a.id
                            """, AccidentType.class)
                    .getResultList();
        }
    }

    @Override
    public Optional<AccidentType> findById(Integer id) {
        try (Session session = openSession()) {
            return session.createQuery("""
                            FROM AccidentType a
                            WHERE a.id = :id
                            """, AccidentType.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }
}
