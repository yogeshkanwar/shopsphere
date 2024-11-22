package com.example.ShopSphere.DTO;

import java.util.List;

import com.example.ShopSphere.entity.User;

public class UserDto {
	
	int totaPage;
	List<User> user;
	
	public int getTotaPage() {
		return totaPage;
	}
	public void setTotaPage(int totaPage) {
		this.totaPage = totaPage;
	}
	public List<User> getUser() {
		return user;
	}
	public void setUser(List<User> user) {
		this.user = user;
	}
	
	
	
	
	
	
	
}
