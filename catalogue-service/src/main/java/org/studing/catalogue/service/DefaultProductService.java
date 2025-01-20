package org.studing.catalogue.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.studing.catalogue.entity.Product;
import org.studing.catalogue.repository.ProductRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class DefaultProductService implements ProductService {
    ProductRepository productRepository;

    @Override
    public Iterable<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String title, String details) {
        return productRepository.save(new Product(null, title, details));
    }

    @Override
    public Optional<Product> findProduct(int productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void updateProduct(Integer id, String title, String details) {
        productRepository.findById(id)
            .ifPresentOrElse(product -> {
                product.setTitle(title);
                product.setDetails(details);
            }, () -> {
                throw new NoSuchElementException();
            });
    }

    @Override
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}