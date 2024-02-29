package org.eclipse.jakarta.hello.resources;

import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.jakarta.hello.model.Product;
import org.eclipse.jakarta.hello.repository.ProductRepository;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

@Path("products")
public class ProductResource {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    private ProductRepository productRepository;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Product findProduct(@PathParam("id") Long id) {
        logger.info("Finding product by id " + id);
        return productRepository.findById(id)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces("application/json")
    public List<Product> findAll() {
        logger.info("Finding all products");
        return productRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Product create(Product product) {
        logger.info("Creating a new product " + product.getName());
        try {
            return productRepository.create(product);
        } catch (PersistenceException ex) {
            logger.info("Error creating product " + product.getName());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        logger.info("Deleting product by id " + id);
        try {
            productRepository.delete(id);
        } catch (IllegalArgumentException e) {
            logger.info("Error deleting product by id " + id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Product update(Product product) {
        logger.info("Updating product by id " + product.getId());
        try {
            return productRepository.create(product);
        } catch (PersistenceException ex) {
            logger.info("Error updating product " + product.getName());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
