package common.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import common.entities.Board;
import common.entities.User;
import common.entities.dto.BoardDto;
import common.entities.dto.UserDto;
import common.service.BoardService;
import common.service.SecurityService;
import common.service.UserService;

@Controller
public class AdministrationPageController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "/adminpanel", method = RequestMethod.GET)
	public String loadAdministrationPage(Principal principal, RedirectAttributes redirectAttributes, Model model) {

		//just temporary TODO:
		if (securityService.isLogged(principal) && !"kamil".equals(securityService.getCurrentUserName(principal))) {
			redirectAttributes.addFlashAttribute("reason", "You dont have permision to visit this site !!");
			return "redirect:/error";
		}

		model.addAttribute("userList", userService.findAll());
		model.addAttribute("boardList", boardService.findAll());
		model.addAttribute("logged", securityService.isLogged(principal));
		model.addAttribute("name", securityService.getCurrentUserName(principal));
		model.addAttribute("removedBoard", new BoardDto());
		model.addAttribute("removedUser", new UserDto());
		return "adminpanel";
	}

	@RequestMapping(value = "/adminpanel", method = RequestMethod.POST)
	public String updateAdministrationPage(@ModelAttribute("removedUser") UserDto removedUser,
			@ModelAttribute("removedBoard") BoardDto removedBoard) {

		System.out.println(removedUser.toString());
		System.out.println(removedBoard.toString());

		if (removedUser.getId() != null && userService.userExists(removedUser.getName())) {
			User user = userService.findById(removedUser.getId());
			userService.deleteUser(user);
			System.out.println("removed:" + user.toString());
		}

		if (removedBoard != null && boardService.findByName(removedBoard.getName()) != null) {
			Board board = boardService.findByName(removedBoard.getName());
			boardService.deleteBoard(board);
			System.out.println("removed:" + board.toString());
		}
		return "redirect:/adminpanel";
	}
}
