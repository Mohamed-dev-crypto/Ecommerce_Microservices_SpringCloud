package org.example.billingservice.feign;


import org.example.billingservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "custom-service")
public interface CustomerRestClient {
    @GetMapping("/api/customers/{id}")
    org.example.customservice.entities.Customer getCustomerById(@PathVariable Long id);

    @GetMapping("/api/customers")
    PagedModel<Customer> getAllCustomers();

}
