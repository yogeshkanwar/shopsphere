package com.example.ShopSphere.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class SignupDto {
	
	@NotBlank(message = "Email is mendatory")
	private String email;
	@NotBlank(message = "Password is mendatory")
	private String password;
	@NotBlank(message = "Confirm Password is mendatory")
	private String confirmPassword;
	@NotBlank(message = "Role is mendatory")
	@Pattern(regexp = "USER|ADMIN",message = "Role can be only USER or ADMIN")
	private String role;
	@NotBlank(message = "First Name is mendatory")
	private String firstName;
	
	private String LastName;
	
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
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	
	
	
}
