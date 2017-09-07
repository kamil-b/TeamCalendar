package common.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import common.entities.User;
import common.entities.dto.UserDto;
import common.entities.enums.JobRole;
import common.repository.UserService;

@Controller
public class UpdateProfileController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user/{username}/profile", method = RequestMethod.GET)
	public String showUserPageDetails(@PathVariable("username") String username, Model model, Principal principal,
			RedirectAttributes redirectAttributes) {

		if (!username.equals(principal.getName())) {
			redirectAttributes.addFlashAttribute("reason", "You dont have permision to visit this site !!");
			return "redirect:/error";
		}

		UserDto userDto = userService.returnUserDto(userService.findByName(username));
		List<JobRole> allRoles = new ArrayList<JobRole>(Arrays.asList(JobRole.values()));
		List<User> superiorsList = userService.findAllByRole(JobRole.LINE_MANAGER);

		model.addAttribute("allSuperiors", superiorsList);
		model.addAttribute("user", userDto);
		model.addAttribute("allRoles", allRoles);

		return "profile";
	}

	@RequestMapping(value = "/user/{username}/profile", method = RequestMethod.POST)
	public String updateUserProfile(@PathVariable("username") String username, @ModelAttribute("user") UserDto userDto,
			RedirectAttributes redirectAttributes, BindingResult bindingResult) {

		User updated = userService.returnUser(userDto);
		User user = userService.findById(updated.getId());
		user.setEmail(updated.getEmail());
		user.setRole(updated.getRole());
		user.setSuperior(updated.getSuperior());
		user.setRemoteWorkLocation(updated.getRemoteWorkLocation());

		userService.update(user);

		redirectAttributes.addFlashAttribute("reason", "Profile updated succesfully !");
		return "redirect:/user/{username}/details";
	}
}
