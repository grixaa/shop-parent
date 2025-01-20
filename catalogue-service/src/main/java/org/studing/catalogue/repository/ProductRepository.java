package org.studing.catalogue.repository;

import org.springframework.data.repository.CrudRepository;
import org.studing.catalogue.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    Iterable<Product> findAllByTitleLikeIgnoreCase(String filter);
}
