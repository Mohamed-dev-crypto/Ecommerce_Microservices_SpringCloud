package org.example.billingservice.feign;

import org.example.billingservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory")
public interface ProductRestClient {
    @GetMapping("/api/products/{id}")
    org.example.inventory.entities.Product getProductById(@PathVariable String id);
    @GetMapping("/api/products")
    PagedModel<Product> getAllProducts();
}
