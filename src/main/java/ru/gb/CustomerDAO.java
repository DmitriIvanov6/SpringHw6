package ru.gb;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class CustomerDAO {

    private final EntityManagerFactory managerFactory;

    public CustomerDAO(Factory factory) {
        managerFactory = factory.getManagerFactory();
    }

    public Customer findByID(long id) {
        EntityManager entityManager = managerFactory.createEntityManager();
        try {
            return entityManager.find(Customer.class, id);
        } finally {
            entityManager.close();
        }
    }

    public List<Customer> findAll() {
        EntityManager entityManager = managerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT customer FROM Customer AS customer", Customer.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void deleteById(long id) {
        EntityManager entityManager = managerFactory.createEntityManager();
        try {
            if (findByID(id) != null) {
                Customer toDelete = entityManager.find(Customer.class, id);
                entityManager.getTransaction().begin();
                entityManager.remove(toDelete);
                entityManager.getTransaction().commit();
            } else {
                System.out.println("Нет покупателя с таким id");
            }
        } finally {
            entityManager.close();
        }
    }

    public Customer saveOrUpdate(Customer customer) {
        EntityManager entityManager = managerFactory.createEntityManager();
        long id = customer.getId();
        try {
            entityManager.getTransaction().begin();
            if (id == 0L) {
                entityManager.persist(customer);
                id = customer.getId();
                entityManager.getTransaction().commit();
                return findByID(id);
            } else {
                Customer customerToChange = findByID(id);
                if (customerToChange != null) {
                    customerToChange.setName(customer.getName());
                    entityManager.merge(customerToChange);
                    entityManager.getTransaction().commit();
                } else {
                    System.out.println("Покупателя с таким ID не существует");
                    return customer;
                }

            }
            return findByID(id);
        } finally {
            entityManager.close();
        }
    }
}
