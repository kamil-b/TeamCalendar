package common.controllers;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import common.entities.Event;
import common.entities.User;
import common.entities.dto.EventDto;
import common.entities.enums.EventStatus;
import common.entities.enums.JobRole;
import common.entities.wraper.EventDtoListForm;
import common.repository.EventService;
import common.repository.UserService;

@Controller
public class EventsStatusController {

	@Autowired
	private EventService eventService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/eventstatus/{username}", method = RequestMethod.GET)
	public String showAllEventsStatus(@PathVariable("username") String username, Model model,
			RedirectAttributes redirectAttributes, Principal principal) {

		if (!username.equals(principal.getName())) {
			redirectAttributes.addFlashAttribute("reason", "You dont have permision to visit this site !!");
			return "redirect:/error";
		}

		User user = userService.findByName(username);
		if (user == null) {
			redirectAttributes.addFlashAttribute("reason", "User: " + username + " was not found in database!");
			return "redirect:/error";
		}

		EventDtoListForm myEventsList = new EventDtoListForm();
		EventDto updated = new EventDto();
		myEventsList.setEventslist(
				(ArrayList<EventDto>) eventService.returnListOfEventDto(eventService.getAllEventsForUser(user)));

		if (user.getRole() == JobRole.LINE_MANAGER) {
			EventDtoListForm eventList = new EventDtoListForm();
			eventList.setEventslist((ArrayList<EventDto>) eventService
					.returnListOfEventDto(eventService.getAllEventsBelongedToSuperior(user.getName())));
			model.addAttribute("events", eventList);
		}
		model.addAttribute("userRole", user.getRole().toString());
		model.addAttribute("updated", updated);
		model.addAttribute("accepted", EventStatus.ACCEPTED);
		model.addAttribute("rejected", EventStatus.REJECTED);
		model.addAttribute("myEvents", myEventsList);

		return "eventstatus";
	}

	@RequestMapping(value = "/eventstatus/{username}", method = RequestMethod.POST)
	public String updateEventStatus(@PathVariable("username") String username,
			@ModelAttribute("updated") EventDto updatedDto, Model model) {

		System.out.println("event: " + updatedDto.toString());
		Event event = eventService.getEventById(updatedDto.getId());
		event.setEventStatus(updatedDto.getEventStatus());
		eventService.save(event);
		return "redirect:/eventstatus/{username}";
	}
}
