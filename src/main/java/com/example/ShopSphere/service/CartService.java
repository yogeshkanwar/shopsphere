package com.example.ShopSphere.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ShopSphere.Exception.InvalidIDException;
import com.example.ShopSphere.Exception.outOfStockException;
import com.example.ShopSphere.entity.Cart;
import com.example.ShopSphere.entity.Product;
import com.example.ShopSphere.entity.User;
import com.example.ShopSphere.repo.CartRepository;
import com.example.ShopSphere.util.CrudService;

@Service
public class CartService extends CrudService<Cart>{
    private final CartRepository repository;
    
	public CartService(CartRepository repository) {
		super(repository);
		this.repository = repository;
	}
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;

	public Cart addToCart(Long productId, int quantity) throws outOfStockException, InvalidIDException {
		User user = userService.whoAmI();
		Product product = productService.getById(productId).get();
		
		if(product.getQuantity() < quantity) {
			throw new outOfStockException();
		}
		
		Cart cart = this.getByProductIdAndUserId(productId,user.getId());
		if(quantity == 0) {
			removeFromCart(productId);
			return null;
		}
		
		if(cart == null) {
			cart = new Cart();
			cart.setCreatedDate(LocalDateTime.now());
			cart.setProduct(product);
			cart.setQuantity(quantity);
			cart.setUser(user);
		} else {
			cart.setQuantity(quantity);
		}
		add(cart);
		return cart;
	}

	private Cart getByProductIdAndUserId(Long productId, Long userId) {
		return repository.getByProductIdAndUserId(productId,userId);
	}

	public void removeFromCart(Long productId) throws InvalidIDException {
		User user = userService.whoAmI();
				
		Cart cart = getByProductIdAndUserId(productId,user.getId());
		if(cart == null) {
			throw new InvalidIDException();
		}
		System.out.println("test the " + 		cart.getId());

	    this.delete(cart.getId());
	}

	public List<Cart> myCart() {
		User user = userService.whoAmI();
		return repository.getByUser(user);
	}

}
