package org.studing.manager.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.studing.manager.client.BadRequestException;
import org.studing.manager.client.ProductsRestClient;
import org.studing.manager.controller.payload.UpdateProductPayload;
import org.studing.manager.entity.Product;

import java.util.Locale;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@RequestMapping("catalogue/products/{productId:\\d+}")
public class ProductController {
    ProductsRestClient productsRestClient;
    MessageSource messageSource;

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") int productId) {
        return productsRestClient.findProduct(productId)
            .orElseThrow(() -> new NoSuchElementException("catalogue.errors.product.not_found"));
    }

    @GetMapping
    public String getProduct() {
        return "catalogue/products/product";
    }

    @GetMapping("edit")
    public String getProductEditPage() {
        return "catalogue/products/edit";
    }

    @PostMapping("edit")
    public String updateProduct(
        @ModelAttribute(name = "product", binding = false) Product product,
        UpdateProductPayload payload,
        Model model) {

        try {
            productsRestClient.updateProduct(product.id(), payload.title(), payload.details());
            return "redirect:/catalogue/products/%d".formatted(product.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "catalogue/products/edit";
        }
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        productsRestClient.deleteProduct(product.id());
        return "redirect:/catalogue/products/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(
        NoSuchElementException exception,
        Model model,
        HttpServletResponse response,
        Locale locale) {

        response.setStatus(NOT_FOUND.value());
        model.addAttribute(
            "error",
            messageSource.getMessage(exception.getMessage(), new Object[0], locale));
        return "errors/404";
    }
}