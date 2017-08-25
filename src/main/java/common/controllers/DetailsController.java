package common.controllers;

import java.security.Principal;

import java.util.List;

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
import common.service.BoardService;
import common.service.UserService;

@Controller
public class DetailsController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BoardService boardService;

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

	@RequestMapping(value = "/user/{username}/details", method = RequestMethod.POST)
	public String assigneToBoard(@PathVariable("username") String username,
			@ModelAttribute(value = "selectedboard") Board selectedboard, Model model) {
		
		User user = userService.findByName(username);
		Board board = boardService.findByName(selectedboard.getName());

		if (user == null) {
			throw new IllegalArgumentException("Couldnt find user in database");
		}
		if (board == null) {
			throw new IllegalArgumentException("Couldnt find board in database");
		}
		System.out.println(user.getName());
		for(User u: board.getMembers())
		System.out.println("member: " + u.getName());
		
		
		if (board.getMembers().contains(user)) {
			board.removeUserFromBoard(user);
			user.removeBoardFromList(board);
		} else {
			board.addUserToBoard(user);
			user.addBoardToList(board);
		}
		userService.save(user);
		boardService.save(board);

		List<Board> list = boardService.findAll();
		list.removeAll(user.getBoards());
		model.addAttribute("list", list);
		model.addAttribute("user", user);
		return "details";
	}

}