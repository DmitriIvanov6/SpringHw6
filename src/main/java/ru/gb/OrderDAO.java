package ru.gb;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class OrderDAO {

    EntityManagerFactory managerFactory;

    public OrderDAO(Factory factory) {
        managerFactory = factory.getManagerFactory();
    }

    public List<Product> checkOrders(long id) {
        EntityManager entityManager = managerFactory.createEntityManager();
        try {
            Customer customer = entityManager.find(Customer.class, id);
            return customer.getProductList();
        } finally {
            entityManager.close();
        }
    }

    public List<Customer> checkCustomers(long id) {
        EntityManager entityManager = managerFactory.createEntityManager();
        try {
            Product product = entityManager.find(Product.class, id);
            return product.getCustomerList();
        } finally {
            entityManager.close();
        }
    }


}
