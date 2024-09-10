package com.example.bitemeals.repository;

import com.example.bitemeals.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {
}
