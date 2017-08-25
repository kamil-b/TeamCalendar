package common.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import common.service.BoardService;
import common.service.SecurityService;

@Controller
public class HomeController {

	@Autowired
	BoardService boardService;
	
	@Autowired
	SecurityService securityService;

	@RequestMapping(value = { "", "/", "/home" })
	public String showHome(final Principal principal, Model model) {
		model.addAttribute("logged", securityService.isLogged(principal));
		model.addAttribute("name", securityService.getCurrentUserName(principal));
		model.addAttribute("boards",boardService.findAll());
		return "home";

	}
}
