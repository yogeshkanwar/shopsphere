package com.example.ShopSphere.controller;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ShopSphere.DTO.SignupDto;
import com.example.ShopSphere.Exception.ValidationFailedException;
import com.example.ShopSphere.entity.User;
import com.example.ShopSphere.service.UserService;
import com.example.ShopSphere.util.JwtTokenUtil;
import com.example.ShopSphere.util.Messages;
import com.example.ShopSphere.util.ResponseModel;

@RestController
@RequestMapping("/sign-up")
public class SignupController {
	
	private final UserService service;
	
	public SignupController(UserService service) {
		this.service = service;
	}
	
	@Autowired 
	private Messages messages;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping
	public ResponseEntity<ResponseModel> signup(@Valid @RequestBody SignupDto signupDto,BindingResult result) throws ValidationFailedException{
		if(result.hasErrors()) {
			System.out.println(result.toString());
			throw new ValidationFailedException(result);
		}
		Optional<User> userOptional = service.findByEmail(signupDto.getEmail());

		if(userOptional.isPresent()) {
            return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("email.exists")).build(), HttpStatus.BAD_REQUEST);
		}
		
		if(!signupDto.getPassword().equals(signupDto.getConfirmPassword())) {
            return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("password.not.match")).build(), HttpStatus.BAD_REQUEST);
		}
		
		User user = service.register(signupDto);
        return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("signup.success"))
        											.setData("user", user)
        											.setData("token", jwtTokenUtil.generateToken(user))
        											.build(), HttpStatus.OK);
					
	}

}
