package org.studing.catalogue.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.studing.catalogue.controller.payload.UpdateProductPayload;
import org.studing.catalogue.entity.Product;
import org.studing.catalogue.service.ProductService;

import java.util.Locale;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@RequestMapping("catalogue-api/products/{productId:\\d+}")
public class ProductRestController {
    ProductService productService;
    MessageSource messageSource;

    @ModelAttribute("product")
    public Product getProduct(@PathVariable("productId") int productId) {
        return productService.findProduct(productId)
            .orElseThrow(() -> new NoSuchElementException("catalogue.errors.product.not_found"));
    }

    @GetMapping
    public Product findProduct(@ModelAttribute("product") Product product) {
        return product;
    }

    @PatchMapping
    public ResponseEntity<?> updateProduct(
        @PathVariable("productId") int productId,
        @Valid @RequestBody UpdateProductPayload payload,
        BindingResult bindingResult) throws BindException {

        if (bindingResult.hasErrors()) {
            throw (bindingResult instanceof BindException exception)
                ? exception
                : new BindException(bindingResult);
        } else {
            productService.updateProduct(productId, payload.title(), payload.details());
            return noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") int productId) {
        productService.delete(productId);
        return noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(
        NoSuchElementException exception,
        Locale locale) {

        return status(NOT_FOUND)
            .body(forStatusAndDetail(
                NOT_FOUND,
                requireNonNull(messageSource.getMessage(
                    exception.getMessage(),
                    new Object[0],
                    exception.getMessage(),
                    locale))));
    }
}
