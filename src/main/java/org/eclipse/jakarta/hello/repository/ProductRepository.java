package org.eclipse.jakarta.hello.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.eclipse.jakarta.hello.model.Product;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Stateless
public class ProductRepository {
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @PersistenceContext(unitName = "products")
    private EntityManager entityManager;

    public Product create(Product product) {
        logger.info("Creating a new product " + product.getName());
        entityManager.persist(product);

        return product;
    }

    public List<Product> findAll() {
        logger.info("Finding all products");
        return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    public Optional<Product> findById(Long id) {
        logger.info("Finding product by id " + id);
        return Optional.ofNullable(entityManager.find(Product.class, id));
    }

    public void delete(Long id) {
        logger.info("Deleting coffee by id " + id);
        var coffee = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid coffee Id:" + id));
        entityManager.remove(coffee);
    }

    public Product update(Product product) {
        logger.info("Updating product by id " + product.getId());
        return entityManager.merge(product);
    }
}
