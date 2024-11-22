package com.example.ShopSphere.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import com.example.ShopSphere.entity.Category;
import com.example.ShopSphere.repo.CategoryRepository;
import com.example.ShopSphere.util.CrudService;

@Service
public class CategoryService extends CrudService<Category>{

	public CategoryService(CategoryRepository repository) {
		super(repository);
		this.repository= repository;
	}
	
	@Override
	public Category add(Category type) {
		type.setCreatedDate(LocalDateTime.now());
		return repository.save(type);
	}	

}
