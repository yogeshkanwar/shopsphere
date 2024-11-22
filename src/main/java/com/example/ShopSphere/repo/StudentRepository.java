package com.example.ShopSphere.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ShopSphere.entity.Student;

public interface StudentRepository extends JpaRepository<Student,Long>{

}
