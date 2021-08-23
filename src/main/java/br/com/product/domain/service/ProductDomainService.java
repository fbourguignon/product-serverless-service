package br.com.product.domain.service;

import br.com.product.domain.entity.Product;

import java.util.List;

public interface ProductDomainService {

    Product save(Product product);
    Product get(String id);
    Product update(String id, Product product);
    List<Product> list();
    void delete(String id);
}
