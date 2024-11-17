package org.example.billingservice.web;

import org.example.billingservice.entities.Bill;
import org.example.billingservice.feign.ProductRestClient;
import org.example.billingservice.repository.BillRepository;

import org.example.billingservice.feign.CustomerRestClient;
import org.example.billingservice.repository.ProductItemRepository;
import org.example.customservice.entities.Customer;
import org.example.inventory.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillRestController {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private CustomerRestClient customerRestClient;
    @Autowired
    private ProductRestClient productRestClient;

    @GetMapping(path = "/bills/{id}")
    public Bill getBill(@PathVariable Long id) {
        // Fetch the bill by ID from the database
        Bill bill = billRepository.findById(id).get();

        // Fetch the associated customer details
        bill.setCustomer(customerRestClient.getCustomerById(bill.getCustomerId()));

        // Fetch product details for each product item in the bill
        bill.getProductItems().forEach(productItem -> {
            productItem.setProduct(productRestClient.getProductById(productItem.getProductId()));
        });

        // Return the populated bill
        return bill;
    }
}


///l'adresse veut contacter le discovery pour communiquer vace le microservice