package common.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import common.entities.Board;
import common.entities.User;
import common.entities.dto.BoardDto;
import common.repository.BoardService;
import common.repository.SecurityService;
import common.repository.UserService;

@Controller
public class AddBoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/board/addboard", method = RequestMethod.GET)
	public String showAddBoardForm(Model model, Principal principal) {

		BoardDto newBoardDto = new BoardDto();
		model.addAttribute("owner", userService.findByName(principal.getName()).getUsername());
		model.addAttribute("name", securityService.getCurrentUserName(principal));
		model.addAttribute("board", newBoardDto);

		return "addboard";
	}

	@RequestMapping(value = "/board/addboard", method = RequestMethod.POST)
	public ModelAndView addBoard(@ModelAttribute("board") @Valid BoardDto boardDto, BindingResult bindingResult) {
		Board createdBoard;
		System.out.println("board:" + boardDto.toString());
		if (!bindingResult.hasErrors()) {
			createdBoard = boardService.getBoardFromBoardDto(boardDto);
			System.out.println(createdBoard.toString());
			boardService.save(createdBoard);
			User user = userService.findByName(createdBoard.getOwner());
			user.addBoardToList(createdBoard);
			userService.update(user);
		}
		if (bindingResult.hasErrors()) {
			return new ModelAndView("addboard", "board", boardDto);
		} else {
			return new ModelAndView("redirect:/home");
		}
	}
}
