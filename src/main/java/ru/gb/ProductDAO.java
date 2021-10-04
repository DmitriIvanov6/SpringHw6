package ru.gb;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class ProductDAO {

    private final EntityManagerFactory managerFactory;

    public ProductDAO(Factory factory) {
        managerFactory = factory.getManagerFactory();
    }

    public Product findByID(long id) {
        EntityManager entityManager = managerFactory.createEntityManager();
        try {
            return entityManager.find(Product.class, id);
        } finally {
            entityManager.close();
        }
    }

    public List<Product> findAll() {
        EntityManager entityManager = managerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT product FROM Product AS product", Product.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void deleteById(long id) {
        EntityManager entityManager = managerFactory.createEntityManager();
        try {
            if (findByID(id) != null) {
                Product toDelete = entityManager.find(Product.class, id);
                entityManager.getTransaction().begin();
                entityManager.remove(toDelete);
                entityManager.getTransaction().commit();
            } else {
                System.out.println("Нет продукта с таким id");
            }
        } finally {
            entityManager.close();
        }
    }

    public Product saveOrUpdate(Product product) {
        EntityManager entityManager = managerFactory.createEntityManager();
        long id = product.getId();
        try {
            entityManager.getTransaction().begin();
            if (id == 0L) {
                entityManager.persist(product);
                id = product.getId();
                entityManager.getTransaction().commit();
                return findByID(id);
            } else {
                Product productToChange = findByID(id);
                if (productToChange != null) {
                    productToChange.setPrice(product.getPrice());
                    productToChange.setTitle(product.getTitle());
                    entityManager.merge(productToChange);
                    entityManager.getTransaction().commit();
                } else {
                    System.out.println("Продукта с таким ID не существует");
                    return product;
                }
            }
            return findByID(id);
        } finally {
            entityManager.close();
        }
    }

}
