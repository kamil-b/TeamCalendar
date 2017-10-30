package common.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import common.entities.Board;
import common.entities.User;
import common.entities.dto.BoardAdminPanelDto;
import common.entities.dto.BoardAndMemberDto;
import common.entities.dto.BoardDto;
import common.entities.dto.UserDto;
import common.entities.enums.UserRole;
import common.repository.BoardService;
import common.repository.EventService;
import common.repository.SecurityService;
import common.repository.UserService;

@Controller
public class AdministrationPageController {

	private final static Logger logger = LoggerFactory.getLogger(AdministrationPageController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/adminpanel", method = RequestMethod.GET)
	public String loadAdministrationPage(Principal principal, RedirectAttributes redirectAttributes, Model model) {

		logger.info("User:" + principal.getName() + " with role: "
				+ userService.findByName(principal.getName()).getUserRole() + " logged in administration panel");
		model.addAttribute("name", securityService.getCurrentUserName(principal));
		model.addAttribute("boardList", boardService.findAll());
		model.addAttribute("updated",new BoardAndMemberDto());
		return "adminpanel";

	}

	@RequestMapping(value = "/adminpanel", method = RequestMethod.POST)
	public String updateAdministrationPage(@ModelAttribute("updated") @Valid BoardAndMemberDto updated, Principal principal) {
		System.out.println(updated.toString());

		
		return "redirect:/adminpanel";
	}
}
