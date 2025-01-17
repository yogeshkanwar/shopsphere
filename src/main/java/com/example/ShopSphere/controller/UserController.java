package com.example.ShopSphere.controller;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcel;
import com.example.ShopSphere.Exception.ValidationFailedException;
import com.example.ShopSphere.entity.User;
import com.example.ShopSphere.file.FileService;
import com.example.ShopSphere.service.UserService;
import com.example.ShopSphere.util.Messages;
import com.example.ShopSphere.util.ResponseModel;

@RestController
@RequestMapping("/users")
public class UserController {
	private UserService service;
	
	public UserController(UserService service) {
		this.service = service;
	}
	
	@Autowired
	private FileService fileService;
	@Autowired
	private Messages messages;
	
	@GetMapping("/who/am/i") 
	public ResponseEntity<ResponseModel> whoAmI() { 
		
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("resource.fetched"))
				.setData("user", service.whoAmI()) 
				.build(), HttpStatus.OK); 
	}
	
	@PutMapping
	public ResponseEntity<ResponseModel> updateDetail(@RequestBody User userData) {	
		User user =  service.update(userData);
	    return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("profile.updated")).setData("user", user).build(), HttpStatus.OK);      
	}
	
	@PostMapping("/upload/image")
	public ResponseEntity<ResponseModel> uploadProfileImage(@RequestParam MultipartFile image) throws IOException {	
		User user = service.whoAmI();
		user.setImagePath(fileService.save(image,"user/" + user.getId()));
		
		service.save(user);
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("profile.updated"))
				.setData("user",user)  
				.build(), HttpStatus.OK); 
	}
    
	@GetMapping("/generate-excel")
	public ResponseEntity<String> generateExcel() {
	    List<User> users = service.findAll();
	    String filename = "/home/yogeshkanwar/Documents/Balance.xlsx";
	    
	    try (HSSFWorkbook workbook = new HSSFWorkbook();
	         FileOutputStream fileOut = new FileOutputStream(filename)) {

	        HSSFSheet sheet = workbook.createSheet("User");
	        List<String> headers = Arrays.asList("Id","Created Date","Image Path","Role","First Name","Last Name");
	        
	        // Creating header row
	        HSSFRow headerRow = sheet.createRow(0);
	        for(int i=0; i<headers.size();i++) {
		        headerRow.createCell(i).setCellValue(headers.get(i));
	        }
	        
	        // Creating data rows
	        int rowNum = 1;
	        for (User user : users) {
	            HSSFRow row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(user.getId());
	            row.createCell(1).setCellValue(user.getCreatedDate().toLocalDate());
	            row.createCell(2).setCellValue(user.getImagePath());
	            row.createCell(3).setCellValue(user.getRole());
	            row.createCell(4).setCellValue(user.getFirstName());
	            row.createCell(5).setCellValue(user.getLastName());
	        }

	        // Writing to file
	        workbook.write(fileOut);

	        return ResponseEntity.ok("Excel file has been generated successfully at " + filename);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Failed to generate Excel file: " + e.getMessage());
	    }
	}
	
	public static void main(String[] args) {
//		List<String> fruits =  Arrays.asList("apple","banana","mango","pears","orange");
//		
//		Map<Integer, List<String>> groupedByLength = fruits.stream()
//                .collect(Collectors.groupingBy(s -> s.length()));
//		
//		groupedByLength.forEach((length, count) -> {
//            System.out.println("Words of length " + length + ": " + count);
//        });	
		
		List<Integer> fruits =  Arrays.asList(1,4,5,6,74,5,6,7,8);
		
//		Map<Object, List<Integer>> groupedByLength = fruits.stream()
//                .collect(Collectors.groupingBy(s -> System.out.println(s % 2 == 0))); 
//		
//		groupedByLength.forEach((length, count) -> {
//            System.out.println("Words of length " + length + ": " + count);
//        });	
	}
	
	    
	
	    


}
