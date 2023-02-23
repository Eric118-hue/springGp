package com.example.crud.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.crud.model.Product;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {
	
	

}
