package common.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import common.entities.User;
import common.entities.dto.EventDto;
import common.entities.dto.UserDto;
import common.repository.EventService;
import common.repository.UserService;
import groovyjarjarantlr.debug.Event;

@Controller
public class UserOverviewController {

	@Autowired
	private UserService userService;

	@Autowired
	private EventService eventService;

	private UserDto userDto;

	@RequestMapping(value = "/useroverview/{username}", method = RequestMethod.GET)
	public ModelAndView showUserInfo(@PathVariable("username") String username, Model model) {

		if (userService.userExists(username)) {
			User user = userService.findByName(username);
			userDto = userService.returnUserDto(user);
		} else {
			return new ModelAndView("error").addObject("reason", "No user in database");
		}
		List<EventDto> events = eventService.returnListOfEventDto(
				new ArrayList(eventService.getAllEventsForUserForNextDays(userDto.getName(), 5).values()));
		for(EventDto e : events){
			e.toString();
		}
		return new ModelAndView("useroverview").addObject("user", userDto).addObject("events", events);

	}
}
