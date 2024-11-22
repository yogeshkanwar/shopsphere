package com.example.ShopSphere.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.ShopSphere.entity.Category;
import com.example.ShopSphere.entity.Product;
import com.example.ShopSphere.param.ProductSearchParam;
import com.example.ShopSphere.repo.ProductRepository;
import com.example.ShopSphere.specification.ProductSpecification;
import com.example.ShopSphere.util.CrudService;
import org.springframework.data.domain.Pageable;


@Service
public class ProductService extends CrudService<Product> implements ProductSpecification{
    private final ProductRepository repository;
    
	public ProductService(ProductRepository repository) {
		super(repository);
		this.repository = repository;
	}
	
	@Autowired
	private CategoryService categoryService;
	
	@Override
	public Product add(Product type) {
		if(type.getId() == null) {
			type.setCreatedDate(LocalDateTime.now());
			type.setEnabled(true);
		}
		Category category = categoryService.getById(type.getCategory().getId()).get();
		type.setCategory(category);
		return repository.save(type);
	}	
	
	public Page<Product> filter(ProductSearchParam param) {
		Sort sort = Sort.by("id").descending();
		Pageable pageable  = PageRequest.of(param.getPage(), param.getSize(), sort);
		
		Specification<Product> specification = filterProduct(param);
		return repository.findAll(specification,pageable);
	}

}
