package common.controllers;

import java.security.Principal;
import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import common.entities.dto.UserDto;
import common.entities.enums.JobRole;

import common.service.UserService;

@Controller
public class RegistrationController {

	@Autowired
	UserService service;

	@RequestMapping(value = "/user/registration", method = RequestMethod.GET)
	public String showRegistrationForm(WebRequest request, Model model, Principal principal) {
		UserDto userDto = new UserDto();
		boolean logged = false;
		if (principal != null && principal.getName() != null) {
			logged = true;
		}
		model.addAttribute("allRoles", Arrays.asList(JobRole.values()));
		model.addAttribute("logged", logged);
		model.addAttribute("user", userDto);
		return "registration";
	}

	// TODO
	// http://www.baeldung.com/registration-with-spring-mvc-and-spring-security
	@RequestMapping(value = "/user/registration", method = RequestMethod.POST)
	public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result,
			RedirectAttributes redirectAttributes, Errors errors) {

		if (!result.hasErrors()) {
			service.createUserAccount(userDto, result);
		}

		if (result.hasErrors()) {

			return new ModelAndView("registration", "user", userDto).addObject("allRoles",
					Arrays.asList(JobRole.values()));
		} else {
			return new ModelAndView("successRegister", "user", userDto);
		}
	}

}