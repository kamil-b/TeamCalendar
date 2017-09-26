package common.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
	private SecurityService securityService;

/*	@Autowired
	private BoardService boardService;*/

	@RequestMapping(value = "/adminpanel", method = RequestMethod.GET)
	public String loadAdministrationPage(Principal principal, RedirectAttributes redirectAttributes, Model model) {

		logger.info("User:" + principal.getName() + " with role: "
				+ userService.findByName(principal.getName()).getUserRole() + " logged in administration panel");
/*		List<BoardAdminPanelDto> boardDtos = new ArrayList<>();
		for (Board b : boardService.findAll()) {
			boardDtos.add(new BoardAdminPanelDto(b.getName()));
		}*/
		model.addAttribute("userList", userService.findAll());
/*		model.addAttribute("boardList", boardDtos);*/
		model.addAttribute("name", securityService.getCurrentUserName(principal));
/*		model.addAttribute("removedBoard", new BoardAdminPanelDto(""));*/
		model.addAttribute("removedUser", new UserDto());

		return "adminpanel";

	}

	@RequestMapping(value = "/adminpanel", method = RequestMethod.POST)
	public String updateAdministrationPage(@ModelAttribute("removedUser") UserDto removedUser,
			@ModelAttribute("removedBoard") BoardAdminPanelDto removedBoard, Principal principal) {
/*		Board board = boardService.findByName(removedBoard.getName());
		System.out.println(board.getName());
		if (board != null) {
			boardService.deleteBoard(board);
		}*/

		if (removedUser.getId() != null) {
			User user = userService.findById(removedUser.getId());
			userService.deleteUser(user);
			logger.info("User: " + user.getName() + "delated by user: " + principal.getName());
		}
		return "redirect:/adminpanel";
	}
}
