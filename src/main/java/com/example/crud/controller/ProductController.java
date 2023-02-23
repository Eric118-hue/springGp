package com.example.crud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.model.Product;
import com.example.crud.repository.ProductRepo;
import com.mongodb.DuplicateKeyException;


@RestController 
@CrossOrigin(origins = "*")
public class ProductController {

	@Autowired
	private ProductRepo productrepository;
	
	@PostMapping("/addProduct")
	public Product saveProduct(@RequestBody Product prod) {
		try {
			return productrepository.save(prod);
			
		} catch(DuplicateKeyException e){
			throw new IllegalArgumentException("le nom est existe deja");
		}
	}
	
	@GetMapping("/products")
	public ResponseEntity<?> getAllProduct(){
		List<Product>  products = productrepository.findAll();
		
		if(products.size() > 0) {
			return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No product not found", HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody Product prod){
		Optional<Product> prod_opt = productrepository.findById(id);
		if(prod_opt.isPresent()) {
			Product up_prod = prod_opt.get();
			up_prod.setName(prod.getName() != null ? prod.getName() : up_prod.getName());
			up_prod.setPrice(prod.getPrice() == prod.getPrice() ? prod.getPrice() : up_prod.getPrice());
			up_prod.setDesc(prod.getDesc() != null ? prod.getDesc() : up_prod.getDesc());
			productrepository.save(up_prod);
			return new ResponseEntity<>(up_prod, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("product not found with id: " +id, HttpStatus.NOT_FOUND);
			}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id){
		try {
			productrepository.deleteById(id);
			return new ResponseEntity<>("successfully deleted", HttpStatus.OK);
		}catch( Exception e) {
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
}
