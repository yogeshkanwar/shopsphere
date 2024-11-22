package com.example.ShopSphere.service;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.ShopSphere.DTO.SignupDto;
import com.example.ShopSphere.entity.User;
import com.example.ShopSphere.repo.UserRepository;

@Service
public class UserService implements UserDetailsService{
	protected final Path root = Paths.get("../uploads/static");
	
	private UserRepository repository;
	
	public UserService(UserRepository repository) {
		this.repository = repository;
	}
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public User whoAmI() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	} 
	
	public boolean authorised() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser";
	}

	public User save(User user) {
		return repository.save(user);		
	}

	public Page<User> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
		return  repository.findAll(pageable);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	} 

	public Optional<User> findByEmail(String email) {
		return repository.findByEmail(email);
	}

	public User register(SignupDto signupDto) {
		User user = new User();
		
		user.setFirstName(signupDto.getFirstName());
		user.setLastName(signupDto.getLastName());
		user.setEmail(signupDto.getEmail());
		user.setRole(signupDto.getRole());
		user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

		return repository.save(user);	
	}

	
	@Override  
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
		Optional<User> userOptional=findByEmail(username);
		
		if (userOptional.isPresent()) 
			return userOptional.get();
		 else 
			throw new UsernameNotFoundException("User not found with usernmae: ROLE_" + username);		
	}

	public User update(User userData) {
		User user = this.whoAmI();
		user.setCountryCode(userData.getCountryCode());
	    user.setPhone(userData.getPhone());
	    user.setFirstName(userData.getFirstName());
	    user.setLastName(userData.getLastName());
	    return save(user);
	}

	public List<User> findAll() {
		return repository.findAll()	;
	}

	    
	
}
