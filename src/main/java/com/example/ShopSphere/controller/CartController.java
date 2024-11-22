package com.example.ShopSphere.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ShopSphere.Exception.InvalidIDException;
import com.example.ShopSphere.Exception.outOfStockException;
import com.example.ShopSphere.entity.Cart;
import com.example.ShopSphere.service.CartService;
import com.example.ShopSphere.util.Messages;
import com.example.ShopSphere.util.ResponseModel;

@RestController
@RequestMapping("/cart")
public class CartController {

	private final CartService service;
	public CartController(CartService service) {
		this.service = service;
	}
	
	@Autowired
	private Messages messages;
	
	@PostMapping
	public ResponseEntity<ResponseModel> addToCart(@RequestParam Long productId , @RequestParam int quantity) throws outOfStockException, InvalidIDException {	
		Cart cart = service.addToCart(productId,quantity);
	    return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("cart.added")).setData("cart", cart).build(), HttpStatus.OK);      
	}
	
	@GetMapping
	public ResponseEntity<ResponseModel> myCart() {	
		List<Cart> cart = service.myCart();
	    return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("cart.added")).setData("list", cart).build(), HttpStatus.OK);      
	}
	
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<ResponseModel> DeleteCart(@PathVariable Long productId ) throws InvalidIDException  {	
		service.removeFromCart(productId);
	    return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("cart.deleted")).build(), HttpStatus.OK);      
	}
	
}

