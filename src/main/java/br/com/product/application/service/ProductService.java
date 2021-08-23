package br.com.product.application.service;

import br.com.product.application.exception.ProductServiceGenericException;
import br.com.product.domain.entity.Product;
import br.com.product.domain.service.ProductDomainService;
import br.com.product.infraestructure.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Slf4j
@Singleton
public class ProductService implements ProductDomainService {

    private final ProductRepository repository;

    @Inject
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        try {
            log.info("Saving product: [{}]", product.getName());
            return repository.save(product);
        }catch (Exception e){
            log.error("An error has ocurred on persist product [{}]", e.getMessage());
            throw new ProductServiceGenericException("An error has ocurred on retrieve product");
        }
    }

    @Override
    public Product get(String id) {
        try {
            log.info("Retrieving product: [{}]", id);
            return repository.get(new ObjectId(id));
        }catch (Exception e){
            log.error("An error has ocurred on retrieve product [{}]", e.getMessage());
            throw new ProductServiceGenericException("An error has ocurred on retrieve product");
        }
    }

    @Override
    public Product update(String id, Product product) {
        try {
            log.info("Updating product: [{}]", id);
            return repository.update(new ObjectId(id), product);
        }catch (Exception e){
            log.error("An error has ocurred on update product [{}]", e.getMessage());
            throw new ProductServiceGenericException("An error has ocurred on update product");
        }
    }

    @Override
    public List<Product> list() {
        try {
            log.info("Listing products");
            return repository.list();
        }catch (Exception e){
            log.error("An error has ocurred on list products [{}]", e.getMessage());
            throw new ProductServiceGenericException("An error has ocurred on list products");
        }
    }

    @Override
    public void delete(String id) {
        try {
            log.info("Removing product: [{}]", id);
            repository.delete(new ObjectId(id));
        }catch (Exception e){
            log.error("An error has ocurred on remove product [{}]", e.getMessage());
            throw new ProductServiceGenericException("An error has ocurred on remove product");
        }
    }
}
