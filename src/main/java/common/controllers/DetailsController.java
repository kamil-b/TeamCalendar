package common.controllers;

import java.security.Principal;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import common.entities.Board;
import common.entities.User;
import common.repository.BoardService;
import common.repository.UserService;

@Controller
public class DetailsController {

	private final static Logger logger = LoggerFactory.getLogger(AdministrationPageController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "/user/details", method = RequestMethod.GET)
	public ModelAndView showUserDetailsDefault(Principal principal, Model model){
		return showUserDetails(principal.getName(), model, principal) ;
	}
	
	@RequestMapping(value = "/user/{name}/details", method = RequestMethod.GET)
	public ModelAndView showUserDetails(@PathVariable("name") String name, Model model, Principal principal) {

		if (!name.equals(principal.getName())) {
			return new ModelAndView("redirect:/home");
		}

		User user = userService.findByName(name);
		List<Board> list = boardService.findAll();
		list.removeAll(user.getBoards());
		model.addAttribute("list", list);
		model.addAttribute("user", user);
		model.addAttribute("selectedboard", new Board(""));

		return new ModelAndView("details");

	}

	
	@RequestMapping(value = "/user/details", method = RequestMethod.POST)
	public String assigneToBoardDefault(Principal principal,
			@ModelAttribute(value = "selectedboard") Board selectedboard, Model model){
		return assigneToBoard(principal.getName(),  selectedboard,  model) ;
	}
	
	@RequestMapping(value = "/user/{username}/details", method = RequestMethod.POST)
	public String assigneToBoard(@PathVariable("username") String username,
			@ModelAttribute(value = "selectedboard") Board selectedboard, Model model) {

		User user = userService.findByName(username);
		Board board = boardService.findByName(selectedboard.getName());

		if (user == null || board == null) {
			logger.error("Could not find user or board in database");
			return "redirect:home";
		}

		if (board.getMembers().contains(user)) {
			board.removeUserFromBoard(user);
			user.removeBoardFromList(board);
		} else {
			board.addUserToBoard(user);
			user.addBoardToList(board);
		}
		userService.update(user);
		boardService.save(board);

		List<Board> list = boardService.findAll();
		list.removeAll(user.getBoards());
		model.addAttribute("list", list);
		model.addAttribute("user", user);
		return "details";
	}

}
