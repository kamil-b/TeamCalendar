package common.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import common.entities.Board;
import common.entities.User;
import common.entities.dto.UserDto;
import common.entities.enums.UserRole;
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

		UserRole role = userService.findByName(securityService.getCurrentUserName(principal)).getUserRole();

		if (role != null && !role.equals(UserRole.ADMIN)) {
			redirectAttributes.addFlashAttribute("reason", "You dont have permision to visit this site !!");
			return "redirect:/error";
		}
		model.addAttribute("userList", userService.findAll());
		model.addAttribute("boardList", boardService.findAll());
		model.addAttribute("logged", securityService.isLogged(principal));
		model.addAttribute("name", securityService.getCurrentUserName(principal));
		model.addAttribute("removedBoard", new String());
		model.addAttribute("removedUser", new UserDto());
		return "adminpanel";

	}

	@RequestMapping(value = "/adminpanel", method = RequestMethod.POST)
	public String updateAdministrationPage(@ModelAttribute("removedUser") UserDto removedUser) {

		if (removedUser.getId() != null) {
			User user = userService.findById(removedUser.getId());
			userService.deleteUser(user);
			System.out.println("removed:" + user.toString());
		}

		/*
		 * if (boardService.findByName(removedBoard) != null) { Board board =
		 * boardService.findByName(removedBoard);
		 * boardService.deleteBoard(board); System.out.println("removed:" +
		 * board.toString()); }
		 */
		return "redirect:/adminpanel";
	}
}
