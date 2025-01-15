package org.studing.catalogue.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.studing.catalogue.controller.payload.NewProductPayload;
import org.studing.catalogue.entity.Product;
import org.studing.catalogue.service.ProductService;

import java.util.List;

import static java.util.Map.of;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@RequestMapping("catalogue-api/products")
public class ProductsRestController {
    ProductService productService;

    @GetMapping
    public Iterable<Product> findProducts() {
        return productService.findAllProducts();
    }

    @PostMapping
    public ResponseEntity<?> createProduct(
        @Valid @RequestBody NewProductPayload payload,
        BindingResult bindingResult,
        UriComponentsBuilder uriBuilder) throws BindException {

        if (bindingResult.hasErrors()) {
            throw (bindingResult instanceof BindException exception)
                ? exception
                : new BindException(bindingResult);
        } else {
            val product = productService.createProduct(payload.title(), payload.details());
            return created(uriBuilder
                .replacePath("catalogue-api/products/{productId}")
                .build(of("productId", product.getId())))
                .body(product);
        }
    }
}