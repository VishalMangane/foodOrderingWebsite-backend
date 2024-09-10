package com.example.bitemeals.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "carts")
public class Cart {

    @Id
    private String id;
     private String userId;
     private String mealId;
     private int quantity;

    // Getters and Setters
}
