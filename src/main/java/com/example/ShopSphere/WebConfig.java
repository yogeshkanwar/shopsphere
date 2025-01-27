package com.example.ShopSphere;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableAsync
@EnableCaching
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private AuthEntryPoint authEntryPoint;
	
	@Autowired
	private RequestFilter requestFilter;
	
	@Autowired
    private CustomAuthenticationSuccessHandler successHandler;

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	  CorsConfiguration configuration = new CorsConfiguration();
	  
	  configuration.setAllowedOrigins(Arrays.asList("*"));
	  configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH", "DELETE", "OPTIONS", "HEAD"));
	  configuration.setAllowedHeaders(Arrays.asList("*"));
	  configuration.setMaxAge(3600L);
	  
	  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	  source.registerCorsConfiguration("/**", configuration);
	  return source;
	}
    
    @Bean
  	public PasswordEncoder passwordEncoder() {
  	    return new BCryptPasswordEncoder(); 
  	 }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
				httpSecurity.httpBasic().disable()
								 .csrf().disable()
								 .cors().and() 
				.authorizeRequests()
				.antMatchers(HttpMethod.POST,"/login","/sign-up").permitAll()
				.antMatchers(HttpMethod.GET, "/oauth2/authorization/github","/boot-stomp").permitAll()
				
				.anyRequest().authenticated().and().
				
		
				 exceptionHandling().authenticationEntryPoint(authEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.oauth2Login(oauth2 -> oauth2
		                .successHandler(successHandler)  
		            );
		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
    }
    
}
