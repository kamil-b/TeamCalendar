package common.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import common.entities.Event;
import common.entities.dto.EventDto;
import common.entities.enums.EventType;
import common.entities.wraper.EventDtoListForm;
import common.repository.EventService;

@Controller
public class ShowEventsController {

	@Autowired
	private EventService eventService;

	private EventDtoListForm eventForm;
	private List<EventType> eventtypes = Arrays.asList(EventType.values());

	@RequestMapping(value = "/user/events", method = RequestMethod.GET)
	public ModelAndView showCalenderDefault(@PathVariable("name") String name, Model model, Principal principal) {
		return showCalender(principal.getName(), model, principal);
	}

	@RequestMapping(value = "/user/{name}/events", method = RequestMethod.GET)
	public ModelAndView showCalender(@PathVariable("name") String name, Model model, Principal principal) {

		if (!name.equals(principal.getName())) {
			return new ModelAndView("redirect:/home");
		}

		DateTimeFormatter formatter = DateTimeFormat.forPattern("EEEE\n dd.MM");
		Map<LocalDate, Event> events = eventService.getAllEventsForUserForNextDays(name, 30);
		eventForm = new EventDtoListForm();
		eventForm.setEventslist((ArrayList<EventDto>) eventService.returnListOfEventDto(events));

		model.addAttribute("eventtypes", eventtypes);
		model.addAttribute("name", name);
		model.addAttribute("eventForm", eventForm);
		model.addAttribute("formatter", formatter);
		return new ModelAndView("events");
	}

	@RequestMapping(value = "/user/{name}/events", method = RequestMethod.POST)
	public String updateCalender(@PathVariable("name") String name,
			@ModelAttribute("eventFormWrapper") EventDtoListForm eventForm, RedirectAttributes redirectAttributes) {

		EventDtoListForm changedEventForm = new EventDtoListForm();
		changedEventForm.setEventslist(
				eventService.getChangedEventsDto(this.eventForm.getEventslist(), eventForm.getEventslist()));
		
		if (changedEventForm.getEventslist().isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "Please mark any event first");
			return "redirect:/user/" + name + "/events";
		}
		redirectAttributes.addFlashAttribute("eventForm", changedEventForm);
		return "redirect:addevents";

	}
}
