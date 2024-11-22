package com.example.ShopSphere.controller;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ShopSphere.Exception.ValidationFailedException;
import com.example.ShopSphere.entity.User;
import com.example.ShopSphere.model.LoginModel;
import com.example.ShopSphere.service.UserService;
import com.example.ShopSphere.util.JwtTokenUtil;
import com.example.ShopSphere.util.Messages;
import com.example.ShopSphere.util.ResponseModel;

@RestController
@RequestMapping("/login")
public class AuthenticatonController {
	
	@Autowired
	private Messages messages;
	@Autowired
	private UserService userService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping
	public ResponseEntity<ResponseModel> login(@Valid @RequestBody LoginModel loginModel,BindingResult result) throws ValidationFailedException{
		if(result.hasErrors()) {
			throw new ValidationFailedException(result);
		}
		Optional<User> userOptional = userService.findByEmail(loginModel.getEmail());
		
		if(!userOptional.isPresent()) {
            return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("email.not.exist")).build(), HttpStatus.BAD_REQUEST);
		}
		User user = userOptional.get();
		
		boolean match = bcryptEncoder.matches(loginModel.getPassword(), user.getPassword());
		if(!match) {
            return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder(messages.get("invalid.credential")).build(), HttpStatus.BAD_REQUEST);
		}
		
//		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginModel.getEmail(), user.getPassword()));

		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder("Authorization Bearer")
				.setData("token", jwtTokenUtil.generateToken(user))
				.setData("user", user)
				.build(),HttpStatus.OK);
	}
	

}
