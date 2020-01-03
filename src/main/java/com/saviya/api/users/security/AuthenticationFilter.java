package com.saviya.api.users.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saviya.api.users.dto.UserDto;
import com.saviya.api.users.service.UserService;
import com.saviya.api.users.ui.models.LoginRequestModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private UserService userService;
	private Environment environemnt;

	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String SECRET = "saviya2020";

	@Autowired
	public AuthenticationFilter(UserService userService, Environment environemnt,
			AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.environemnt = environemnt;
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		LoginRequestModel loginRequest;
		try {
			loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);

			return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					loginRequest.getEmail(), loginRequest.getPassword(), new ArrayList<>()));

		} catch (JsonParseException e) {
			throw new RuntimeException(e.getMessage());
		} catch (JsonMappingException e) {
			throw new RuntimeException(e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String userName = ((User) authResult.getPrincipal()).getUsername();
		UserDto userDto = userService.findUserByEmail(userName);

	    //We will sign our JWT with our ApiKey secret
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(environemnt.getProperty("token.secret"));

		String token = Jwts.builder().setSubject(userDto.getUserId())
				.setExpiration(new Date(System.currentTimeMillis() +  Long.parseLong(environemnt.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, apiKeySecretBytes ).compact();
		response.addHeader("token", token);
		response.addHeader("userId", userDto.getUserId());
	}

}
