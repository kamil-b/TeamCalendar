package common.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;

import common.entities.User;
import common.entities.dto.EventDto;
import common.entities.dto.EventsInMonth;
import common.entities.dto.UserDto;

import common.repository.EventService;
import common.repository.SecurityService;
import common.repository.UserService;

// https://stackoverflow.com/questions/19122942/angularjs-sorting-rows-by-table-header
// TODO

@Controller
public class UserOverviewController {

	@Autowired
	private UserService userService;

	@Autowired
	private EventService eventService;

	@Autowired
	private SecurityService securityService;

	private UserDto userDto;

	@RequestMapping(value = "/useroverview/{username}", method = RequestMethod.GET)
	public ModelAndView showUserInfo(@PathVariable("username") String username, Model model, Principal principal) {
		if (userService.userExists(username)) {
			User user = userService.findByName(username);
			userDto = userService.returnUserDto(user);
		} else {
			return new ModelAndView("error").addObject("reason", "No user in database");
		}
		Map<String, EventsInMonth> eventsMap = eventService.getEventsInMonths(userDto.getName());

		List<EventDto> events = eventService.returnListOfEventDto(
				new ArrayList<>(eventService.getAllEventsForUserForNextDays(userDto.getName(), 5).values()));

		return new ModelAndView("useroverview").addObject("user", userDto).addObject("events", events)
				.addObject("name", securityService.getCurrentUserName(principal))
				.addObject("statistics", eventsMap.values()).addObject("eventTypes",
						eventsMap.entrySet().iterator().next().getValue().getEventsCountedByType().keySet());
	}

}
