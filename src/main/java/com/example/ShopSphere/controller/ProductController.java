package com.example.ShopSphere.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.ShopSphere.entity.Product;
import com.example.ShopSphere.file.FileService;
import com.example.ShopSphere.param.ProductSearchParam;
import com.example.ShopSphere.service.ProductService;
import com.example.ShopSphere.util.CrudController;
import com.example.ShopSphere.util.ResponseModel;

@RestController
@RequestMapping("/products")
public class ProductController extends CrudController<Product>{
    private ProductService service;
	public ProductController(ProductService service) {
		super(service);
		this.service = service;
	}
	
	@Autowired
	private FileService fileService;
	
	@PostMapping("/image/{id}")
	public ResponseEntity<ResponseModel> uploadImage(@PathVariable Long id , @RequestParam MultipartFile file) throws IOException{
		Product product = service.getById(id).get();
		product.setImagePath(fileService.save(file, "product/"+id));
		service.add(product);
		
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("profile.updated"))
				.setData("product",product)  
				.build(), HttpStatus.OK); 
		
	}
	
	@PostMapping("/filter")
	public ResponseEntity<ResponseModel> filter(@RequestBody ProductSearchParam param) throws IOException{
		System.out.println("Page +++++++ "  +param.getPage());
		System.out.println("Size +++++++ "  +param.getSize());
		System.out.println("Search +++++++ "  +param.getSearch());

		
		Page<Product> products = service.filter(param);
		
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("profile.updated"))
				.setData("list",products.toList())
				.setData("total", products.getTotalElements())
				.setData("totalPages", products.getTotalPages())
				.build(), HttpStatus.OK); 
		
	}

}
