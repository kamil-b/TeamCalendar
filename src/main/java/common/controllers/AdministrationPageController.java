package common.controllers;

import common.entities.dto.BoardAndMemberDto;
import common.repository.BoardService;
import common.repository.SecurityService;
import common.repository.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

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
        model.addAttribute("updated", new BoardAndMemberDto());
        return "adminpanel";

    }

    @RequestMapping(value = "/adminpanel", method = RequestMethod.POST)
    public String updateAdministrationPage(@ModelAttribute("updated") @Valid BoardAndMemberDto updated, Principal principal) {
        System.out.println(updated.toString());


        return "redirect:/adminpanel";
    }
}
