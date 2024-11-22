package com.example.ShopSphere.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ShopSphere.entity.User;
import com.example.ShopSphere.entity.UserAddress;
import com.example.ShopSphere.repo.UserAddressRepository;

@Service
public class UserAddressService {
	private final UserAddressRepository repository;
	
	public UserAddressService(UserAddressRepository repository) {
		this.repository  = repository;
	}
	
	@Autowired
	private UserService userService;

	public UserAddress save(UserAddress newAddress) {
		User user = userService.whoAmI();
		List<UserAddress> addresses = this.getUserAddress(user);
		
		if(addresses.isEmpty()) {
			newAddress.setPrimaryA(true);
		}
		newAddress.setUser(user);
		return repository.save(newAddress);
	}

	public List<UserAddress> getUserAddress(User user) {
		return repository.getByUser(user);
	}

	public void deleteAddress(Long id) {
		repository.deleteById(id);
	}

	public void makeAddressPrimary(Long address_id,User user) {
	    List<UserAddress> myAddresses=this.getUserAddress(user);
		
		myAddresses.forEach(address -> {
			if(address.getId()==address_id)
				address.setPrimaryA(true);
			else
				address.setPrimaryA(false);
		});
		
		repository.saveAll(myAddresses);
	}

	public Optional<UserAddress> getUserAddressById(Long id) {
		return repository.findById(id);
	}
	
	

}
