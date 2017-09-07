package common.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import common.entities.enums.UserRole;
import common.repository.BoardService;
import common.repository.SecurityService;
import common.repository.UserService;

@Controller
public class HomeController {

	@Autowired
	BoardService boardService;
	@Autowired
	UserService userService;
	@Autowired
	SecurityService securityService;

	@RequestMapping(value = { "", "/", "/home" }, method = RequestMethod.GET)
	public String showHome(Principal principal, Model model) {
		boolean logged = securityService.isLogged(principal);
		model.addAttribute("logged", logged);
		if (logged) {
			model.addAttribute("isAdmin", userService.findByName(securityService.getCurrentUserName(principal))
					.getUserRole().equals(UserRole.ADMIN));
		}
		model.addAttribute("name", securityService.getCurrentUserName(principal));
		model.addAttribute("boards", boardService.findAll());
		return "home";

	}
}
