package com.example.ShopSphere.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ShopSphere.entity.User;
import com.example.ShopSphere.entity.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress,Long>{

	List<UserAddress> getByUser(User user);

}
