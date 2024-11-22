package com.example.ShopSphere.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class LoginModel {
	@NotBlank(message = "Email is mendatory")
	private String email;
	@NotBlank(message = "Password is mendatory")
	private String password;
	@NotBlank(message = "Role is mendatory")
	@Pattern(regexp = "USER|ADMIN",message = "Role can be only USER or ADMIN")
	private String role;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	

}
