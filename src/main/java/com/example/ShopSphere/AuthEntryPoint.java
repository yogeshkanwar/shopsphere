package com.example.ShopSphere;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.ShopSphere.util.JwtTokenUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint, Serializable {

	@Autowired
	private JwtTokenUtil tokenUtil;

	private static final long serialVersionUID = -7858869558953243875L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,AuthenticationException authException) throws IOException {
		System.out.println(authException);

		String message="Authorization header must contain Bearer Token.";
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String token = null;
		if (authHeader != null) {
			token = authHeader.replace("Bearer ", "");
			
				try {
				    DecodedJWT decodedJWT = JWT.decode(token);
				    
				    if(decodedJWT.getExpiresAt().before(new Date())) {
				    	System.out.println("refreshing expired token for "+decodedJWT.getSubject());
				    	message="Your session has expired, please login again.";
				    	token=tokenUtil.doGenerateToken(new HashMap<String, Object>(), decodedJWT.getSubject());
				    }
				} catch (JWTDecodeException e) {
					System.out.println("Can't decode this token:: "+token);
			}	
		}

		//setting this attribute in requestFilter
		if(request.getAttribute("status")!=null) {
			message=request.getAttribute("message").toString();
			response.setStatus(Integer.parseInt(request.getAttribute("status").toString()));

			token=null;//suspended account will/can  not refresh session
		} else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
		
		response.setContentType("application/json");
		PrintWriter pw = response.getWriter();
		
		pw.write("{\"message\":\""+message+"\",\"data\":\"[]\""+(token==null ? "" : ",\"token\":\""+token+"\"")+"}");
		pw.close();
	}
}
