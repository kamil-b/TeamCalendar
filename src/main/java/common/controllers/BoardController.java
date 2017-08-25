package common.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import common.entities.Board;
import common.entities.BoardWeeklyEvents;
import common.entities.Event;

import common.helpers.CalenderHelper;

import common.repository.EventRepository;
import common.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	BoardService boardService;

	@Autowired
	EventRepository eventRepository;

	List<LocalDate> week;
	List<Event> eventsInThisWeek;
	String boardName;
	Board board;

	@RequestMapping(value = "/board/{boardname}", method = RequestMethod.GET)
	public String getBoard(@PathVariable("boardname") String boardname, Model model, Principal principal) {

		// week = new ArrayList<LocalDate>(CalenderHelper.returnCurrentWeek());
		week = new ArrayList<LocalDate>(CalenderHelper.returnNextFiveDays());
		
		eventsInThisWeek = new ArrayList<Event>();
		List<String> weekDays = new ArrayList<String>();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("EEEE dd.MM");

		for (LocalDate day : week) {
			weekDays.add(formatter.print(day));
			eventsInThisWeek.addAll(eventRepository.findByDate(day));
		}
		BoardWeeklyEvents events = new BoardWeeklyEvents(boardService.findByName(boardname), eventsInThisWeek, week);

		model.addAttribute("week", weekDays);
		model.addAttribute("events", events);
		model.addAttribute("name", principal.getName());

		return "board";

	}
}
