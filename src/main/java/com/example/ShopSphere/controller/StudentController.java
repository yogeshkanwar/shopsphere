package com.example.ShopSphere.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ShopSphere.entity.Student;
import com.example.ShopSphere.service.StudentService;
import com.example.ShopSphere.util.CrudController;

@RestController
@RequestMapping("/student")
public class StudentController extends CrudController<Student>{
    private StudentService service;
	public StudentController(StudentService service) {
		super(service);
		this.service = service;
	}
	
	

}
