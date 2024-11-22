package com.example.ShopSphere.service;


import org.springframework.stereotype.Service;

import com.example.ShopSphere.entity.Student;
import com.example.ShopSphere.repo.StudentRepository;
import com.example.ShopSphere.util.CrudService;

@Service
public class StudentService extends CrudService<Student>{
    private StudentRepository repository;
	public StudentService(StudentRepository repository) {
		super(repository);
		this.repository = repository;
	}

}
