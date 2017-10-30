package common.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String addBoard(@ModelAttribute("board") @Valid BoardDto boardDto, BindingResult bindingResult, Model model,
			RedirectAttributes attributes) {

		if (boardService.findByName(boardDto.getName()) != null) {
			attributes.addFlashAttribute("error", "Board name allready taken");
			return "redirect:/board/addboard";
		}

		if (!bindingResult.hasErrors()) {
			Board createdBoard = boardService.getBoardFromBoardDto(boardDto);
			boardService.save(createdBoard);
			User user = userService.findByName(createdBoard.getOwner());
			user.addBoardToList(createdBoard);
			userService.update(user);
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("board", boardDto);
			return "redirect:/board/addboard";
		} else {
			return "redirect:/home";
		}
	}
}
