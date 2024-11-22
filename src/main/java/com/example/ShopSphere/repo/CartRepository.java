package com.example.ShopSphere.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ShopSphere.entity.Cart;
import com.example.ShopSphere.entity.User;

public interface CartRepository extends JpaRepository<Cart , Long>{

	@Query("SELECT c FROM Cart c WHERE c.product.id = :productId AND c.user.id = :userId")
	Cart getByProductIdAndUserId(Long productId, Long userId);

	List<Cart> getByUser(User user);

}
