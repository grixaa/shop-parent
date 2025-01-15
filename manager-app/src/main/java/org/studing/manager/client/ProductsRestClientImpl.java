package org.studing.manager.client;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.studing.manager.controller.payload.NewProductPayload;
import org.studing.manager.controller.payload.UpdateProductPayload;
import org.studing.manager.entity.Product;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Optional.*;
import static java.util.Optional.ofNullable;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class ProductsRestClientImpl implements ProductsRestClient {
    private static final String URI_PRODUCT_ID = "catalogue-api/products/{productId}";
    private static final ParameterizedTypeReference<List<Product>> PRODUCTS_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };
    RestClient restClient;

    @Override
    public List<Product> findAllProducts() {
        return restClient
            .get()
            .uri("catalogue-api/products")
            .retrieve()
            .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public Product createProduct(String title, String details) {
        try {
            return restClient
                .post()
                .uri("catalogue-api/products")
                .contentType(APPLICATION_JSON)
                .body(new NewProductPayload(title, details))
                .retrieve()
                .body(Product.class);
        } catch (HttpClientErrorException exception) {
            throw new BadRequestException((List<String>) exception
                .getResponseBodyAs(ProblemDetail.class)
                .getProperties()
                .get("errors"));
        }
    }

    @Override
    public Optional<Product> findProduct(int productId) {
        try {
            return ofNullable(restClient
                .get()
                .uri(URI_PRODUCT_ID, productId)
                .retrieve()
                .body(Product.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return empty();
        }
    }

    @Override
    public void updateProduct(int productId, String title, String details) {
        try {
            restClient
                .patch()
                .uri(URI_PRODUCT_ID, productId)
                .contentType(APPLICATION_JSON)
                .body(new UpdateProductPayload(title, details))
                .retrieve()
                .toBodilessEntity();
        } catch (HttpClientErrorException exception) {
            throw new BadRequestException((List<String>) exception
                .getResponseBodyAs(ProblemDetail.class)
                .getProperties()
                .get("errors"));
        }
    }

    @Override
    public void deleteProduct(int productId) {
        try {
            restClient
                .delete()
                .uri(URI_PRODUCT_ID, productId)
                .retrieve()
                .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
