package com.example.ShopSphere.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "student") 
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDate;
	
	private String name;
	private String course;
	private Float fees;
    @Column(columnDefinition = "float default 0.0")
	private Float pending;
    @Column(columnDefinition = "float default 0.0")
	private Float advance;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public Float getFees() {
		return fees;
	}
	public void setFees(Float fees) {
		this.fees = fees;
	}
	public Float getPending() {
		return pending;
	}
	public void setPending(Float pending) {
		this.pending = pending;
	}
	public Float getAdvance() {
		return advance;
	}
	public void setAdvance(Float advance) {
		this.advance = advance;
	}
	
	

}
