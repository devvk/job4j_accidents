package ru.job4j.accidents.repository.accident;

import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHibernateRepository implements AccidentRepository {

    private final EntityManagerFactory entityManagerFactory;

    private Session openSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    @Override
    public List<Accident> getAll() {
        try (Session session = openSession()) {
            return session.createQuery("""
                            SELECT DISTINCT a
                            FROM Accident a
                            JOIN FETCH a.type
                            LEFT JOIN FETCH a.rules
                            ORDER BY a.id DESC
                            """, Accident.class)
                    .getResultList();
        }
    }

    @Override
    public Optional<Accident> findById(Integer id) {
        try (Session session = openSession()) {
            return session.createQuery("""
                            SELECT DISTINCT a
                            FROM Accident a
                            JOIN FETCH a.type
                            LEFT JOIN FETCH a.rules
                            WHERE a.id = :id
                            """, Accident.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    @Override
    public Accident save(Accident accident) {
        Transaction tx = null;
        try (Session session = openSession()) {
            tx = session.beginTransaction();
            session.persist(accident);
            tx.commit();
            return accident;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public boolean update(Accident accident) {
        Transaction tx = null;
        try (Session session = openSession()) {
            tx = session.beginTransaction();
            Accident merged = session.merge(accident);
            tx.commit();
            return merged != null;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public boolean delete(Integer id) {
        Transaction tx = null;
        try (Session session = openSession()) {
            tx = session.beginTransaction();
            Accident accident = session.find(Accident.class, id);
            if (accident == null) {
                tx.commit();
                return false;
            }
            session.remove(accident);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}
