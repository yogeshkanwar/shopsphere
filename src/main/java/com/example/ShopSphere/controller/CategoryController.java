package com.example.ShopSphere.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ShopSphere.entity.Category;
import com.example.ShopSphere.service.CategoryService;
import com.example.ShopSphere.util.CrudController;

@RestController
@RequestMapping("/category")
public class CategoryController extends CrudController<Category>{

	public CategoryController(CategoryService service) {
		super(service);
		this.service = service;
	}
	

}
