package com.example.ShopSphere.util;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ShopSphere.Exception.InvalidIDException;
import com.example.ShopSphere.param.ProductSearchParam;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class CrudService<T> {
	
	@Autowired
	protected JpaRepository<T, Long> repository;
	
	public CrudService(JpaRepository<T, Long> repository) {
		this.repository=repository;
	}
	
	public Optional<T> getById(Long id) {
		return repository.findById(id);
	}
	
	public T expect(Long id) throws InvalidIDException {
		Optional<T> optional=getById(id);
		if(!optional.isPresent())
			throw new InvalidIDException();
		
		return optional.get(); 
				
	}
	
	public T add(T type) {
		return repository.save(type);
	}
	
	public List<T> addAll(List<T> entities) {
		return repository.saveAll(entities);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public List<T> getAll() {
		return repository.findAll();
	}
	
	public Page<T> getAll(Pageable pageable) {
		
		return repository.findAll(pageable);
	}

	
}

