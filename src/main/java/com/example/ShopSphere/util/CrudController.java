package com.example.ShopSphere.util;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public abstract class CrudController<E> extends EntityRDController<E> {
	
	public CrudController(CrudService<E> service) {
		super(service); 
	}
	
	protected void validateResource(E body,BindingResult result) throws Exception {
		//override this to validate 
	}
	
	@PostMapping
	public ResponseEntity<ResponseModel> add(@Valid @RequestBody E body,BindingResult result) throws Exception {
		this.validateResource(body, result);
		
		if(result.hasErrors()) { 
			return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("validation.failed")).setErrors(result).build(),HttpStatus.BAD_REQUEST);
		}
		
		E resource=service.add(body);
		
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("resource.created"))
				.setData("resource", resource)
				.build(), HttpStatus.CREATED);
	}
}