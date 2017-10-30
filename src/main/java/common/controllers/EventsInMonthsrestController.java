package common.controllers;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import common.entities.dto.EventsInMonth;
import common.repository.EventService;
@RestController
public class EventsInMonthsrestController {

	@Autowired
	private EventService eventService;

	@RequestMapping(value = "/geteventsinmonth", method = RequestMethod.GET)
	public Collection<EventsInMonth> getEvents(Principal principal) {
		return eventService.getEventsInMonths(principal.getName()).values();

	}

}
