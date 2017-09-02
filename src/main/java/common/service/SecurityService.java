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

	/**
	 * Returns name of logged user or null
	 * 
	 * @param principal
	 * @return name or null if there is no logged user. 
	 */
	public String getCurrentUserName(Principal principal) {
		if(principal != null){
			return principal.getName();
		}
		return null;
	}
}
