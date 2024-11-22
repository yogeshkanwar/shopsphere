package com.example.ShopSphere.util;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


public class EntityRDController<E> {

	protected CrudService<E> service;
	
	@Autowired
	protected Messages messages;
	
	
	public EntityRDController(CrudService<E> service) {
		this.service=service;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseModel> get(@PathVariable Long id) throws Exception {
		Optional<E> resourceOptional=service.getById(id);
		if(resourceOptional.isPresent()) {
			
			return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("resource.fetched"))
					.setData("resource", resourceOptional.get())
					.build(), HttpStatus.OK);
		}
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("resource.not.exist")).build(), HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<ResponseModel> delete(@PathVariable Long id) throws Exception {
		Optional<E> resourceOptional=service.getById(id);
		if(resourceOptional.isPresent()) {
			service.delete(id);
			
			return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("resource.deleted"))
					.setData("resource", resourceOptional.get())
					.build(), HttpStatus.OK);
		} 
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("resource.not.exist")).build(), HttpStatus.BAD_REQUEST);
		
	}

	@GetMapping
	public ResponseEntity<ResponseModel> getAll() {
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("resource.fetched"))
				.setData("list", service.getAll())
				.build(), HttpStatus.OK); 
	}
	
	@GetMapping("page/{page}/size/{size}")
	public ResponseEntity<ResponseModel> getAll(@PathVariable Integer page,@PathVariable Integer size) {
		Page<E> pageable=service.getAll(PageRequest.of(page, size));
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("resource.fetched"))
				.setData("list", pageable.getContent())
				.setData("total", pageable.getTotalElements())
				.build(), HttpStatus.OK);
	}
}


