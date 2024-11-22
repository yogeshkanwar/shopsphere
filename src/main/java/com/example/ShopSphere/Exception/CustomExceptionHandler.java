package com.example.ShopSphere.Exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.ShopSphere.util.Messages;
import com.example.ShopSphere.util.ResponseModel;


@ControllerAdvice
public class CustomExceptionHandler {
	
	@Autowired
	private Messages message;
	
	@ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<?> handleValidationFailedException(ValidationFailedException exception, WebRequest webRequest) {
		exception.printStackTrace();
		
		ResponseModel response = new ResponseModel.ResponseModelBuilder(message.get("validation.failed")).setErrors(exception.getResult()).build();
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        return entity;
    }
	
	@ExceptionHandler(InvalidIDException.class)
    public ResponseEntity<?> handleInvalidIDException(InvalidIDException exception, WebRequest webRequest) {
		exception.printStackTrace();
		
		ResponseModel response = new ResponseModel.ResponseModelBuilder(message.get("id.not.exists")).build();
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        return entity;
    }
	
	@ExceptionHandler(outOfStockException.class)
    public ResponseEntity<?> handleOuyOfStockException(outOfStockException exception, WebRequest webRequest) {
		exception.printStackTrace();
		
		ResponseModel response = new ResponseModel.ResponseModelBuilder(message.get("out.of.stock")).build();
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        return entity;
    }
	   

}
