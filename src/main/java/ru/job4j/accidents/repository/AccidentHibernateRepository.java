package ru.job4j.accidents.repository;

import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;

@Repository
@AllArgsConstructor
public class AccidentHibernateRepository {

    private final EntityManagerFactory entityManagerFactory;

    private SessionFactory sessionFactory() {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }

    public Accident save(Accident accident) {
        try (Session session = sessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(accident);
                tx.commit();
                return accident;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    public List<Accident> getAll() {
        try (Session session = sessionFactory().openSession()) {
            return session.createQuery("FROM Accident", Accident.class).list();
        }
    }
}
