package common.service;

import java.security.Principal;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

	public boolean isLogged(Principal principal) {
		if (principal != null) {
			return true;
		}
		return false;
	}

	public String getCurrentUserName(Principal principal) {
		if(principal != null){
			return principal.getName();
		}
		return null;
	}
}
