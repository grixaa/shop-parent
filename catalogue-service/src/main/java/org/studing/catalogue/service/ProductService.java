package org.studing.catalogue.service;

import org.studing.catalogue.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Iterable<Product> findAllProducts();

    Product createProduct(String title, String details);

    Optional<Product> findProduct(int productId);

    void updateProduct(Integer id, String title, String details);

    void delete(Integer id);
}
