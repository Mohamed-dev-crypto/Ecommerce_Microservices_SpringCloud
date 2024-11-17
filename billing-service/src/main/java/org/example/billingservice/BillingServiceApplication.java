package org.example.billingservice;

import org.example.billingservice.entities.Bill;
import org.example.billingservice.entities.ProductItem;
import org.example.billingservice.feign.CustomerRestClient;
import org.example.billingservice.feign.ProductRestClient;
import org.example.billingservice.model.Customer;
import org.example.billingservice.model.Product;
import org.example.billingservice.repository.BillRepository;
import org.example.billingservice.repository.ProductItemRepository;
import org.example.customservice.repository.CustomerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;

@SpringBootApplication
@EnableFeignClients(basePackages = "org.example.billingservice.feign")
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }



    @Bean
    CommandLineRunner commandLineRunner(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductRestClient productRestClient) {
        return args -> {
            // Fetch all customers and products dynamically
            Collection<Customer> customers = customerRestClient.getAllCustomers().getContent();
            Collection<Product> products = productRestClient.getAllProducts().getContent();

            // For each customer, create a bill and associated product items
            customers.forEach(customer -> {
                // Create and save a bill for each customer
                Bill bill = Bill.builder()
                        .billingDate(new Date())
                        .customerId(customer.getId())
                        .build();
                billRepository.save(bill);

                // Randomly assign some products to this bill
                products.forEach(product -> {
                    ProductItem productItem = ProductItem.builder()
                            .productId(product.getId())
                            .quantity((int) (Math.random() * 5) + 1) // Random quantity between 1 and 5
                            .unitPrice(product.getPrice())
                            .bill(bill)
                            .build();
                    productItemRepository.save(productItem);
                });
            });

            System.out.println("Inserted bills and product items for all customers.");
        };
    }

}
