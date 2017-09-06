package common.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import common.entities.User;
import common.repository.UserService;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	UserService service;
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String username = auth.getName();
		String password = (String) auth.getCredentials();
		User user = service.findByName(username);
		
		if(user == null || !user.getName().equalsIgnoreCase(username)){
			throw new BadCredentialsException("username not found");
		}
		if(!user.getPassword().equals(password)){
			throw new BadCredentialsException("wrong password");
		}
		
		return new UsernamePasswordAuthenticationToken(user, password,  null);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}



}
