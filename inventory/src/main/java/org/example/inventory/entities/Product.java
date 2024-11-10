package org.example.inventory.entities;


import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.annotation.Id;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder @ToString
public class Product {

    @jakarta.persistence.Id
    @Id
    private String id;
    private String name;
    private double price;
    private int quantity;



}
