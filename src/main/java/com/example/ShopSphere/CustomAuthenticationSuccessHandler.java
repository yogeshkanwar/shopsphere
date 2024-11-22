package com.example.ShopSphere;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

    	OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
    	System.out.println("oauth token is" + oauthToken);
    	
    	OAuth2User oauthUser = oauthToken.getPrincipal();
    	String email = oauthUser.getAttribute("email");
    	System.out.println("User registrarion id : " + oauthUser);

//    	User user = userService.fineByEmail(email);
//    	if(user == null) {
//    		user = new User();
//    		user.setCreatedDate(LocalDateTime.now());
//    		user.setEmail(email);
//    		user.setName(oauthUser.getAttribute("name"));
//    		user.setRegistrationType(oauthToken.getAuthorizedClientRegistrationId());
//    	} else {
//    		user.setRegistrationType(oauthToken.getAuthorizedClientRegistrationId());
//    	}
//    	userService.save(user);
    	


        // Redirect to a success page or any other logic after successful login
    	String frontendUrl = "http://localhost:3001/home";
        response.sendRedirect(frontendUrl);
    }
}
