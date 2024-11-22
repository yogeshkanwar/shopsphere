package com.example.ShopSphere.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ShopSphere.entity.User;
import com.example.ShopSphere.entity.UserAddress;
import com.example.ShopSphere.service.UserAddressService;
import com.example.ShopSphere.service.UserService;
import com.example.ShopSphere.util.Messages;
import com.example.ShopSphere.util.ResponseModel;

@RestController
@RequestMapping("/user/address")
public class UserAddressController {
	
	private final UserAddressService service;
	
	public UserAddressController(UserAddressService service) {
		this.service = service;
	}
	
	@Autowired
	private Messages messages;
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<ResponseModel> addAddress(@RequestBody UserAddress newAddress){
		UserAddress address = service.save(newAddress);
		
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("profile.updated"))
				.setData("useraddress",address)  
				.build(), HttpStatus.OK); 
	}
	
	@GetMapping
	public ResponseEntity<ResponseModel> getAddress(){
		User user = userService.whoAmI();
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("profile.updated"))
				.setData("useraddress", service.getUserAddress(user))  
				.build(), HttpStatus.OK); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseModel> makeAddressPrimary(@PathVariable Long id) {
		User user = userService.whoAmI();
		Optional<UserAddress> address = service.getUserAddressById(id);
		
		if(!address.isPresent()) {
			return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder("address not present")
					.build(), HttpStatus.BAD_REQUEST); 
		}
		service.makeAddressPrimary(id,user);
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("profile.updated"))
				.setData("address", address)
				.build(), HttpStatus.OK); 
	}
	
	@Secured("USER")
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseModel> deleteAddress(@PathVariable Long id) {
		service.deleteAddress(id);
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("profile.updated"))
				.build(), HttpStatus.OK); 
	}
	
	
	
	

}
