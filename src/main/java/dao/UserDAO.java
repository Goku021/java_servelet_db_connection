package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import model.User;

import java.util.List;

public class UserDAO {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");

    public boolean addUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
                throw new IllegalArgumentException("Username, Email, and Password cannot be null");
            }

            entityManager.persist(user);
            entityManager.getTransaction().commit();
            System.out.println("User added successfully: " + user.getUsername());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }


    public List<User> getAllUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<User> users = null;

        try {
            entityManager.getTransaction().begin();
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
            users = query.getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.close();
        }
        return users;
    }


}
