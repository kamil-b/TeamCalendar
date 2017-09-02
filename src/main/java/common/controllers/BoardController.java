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
@RequestMapping(value = "/board")
public class BoardController {

	private static final int DEFAULT_NUMBER_OF_DAYS = 5;
	private static final int EXTENDED_NUMBER_OF_DAYS = 10;

	@Autowired
	BoardService boardService;

	@Autowired
	EventRepository eventRepository;

	List<LocalDate> week;
	List<Event> eventsInThisWeek;
	String boardName;
	Board board;

	@RequestMapping(value = "/{boardname}", method = RequestMethod.GET)
	public String getBoardByDefault(@PathVariable("boardname") String boardname, Model model, Principal principal) {
		return getBoard(boardname, DEFAULT_NUMBER_OF_DAYS, model, principal);
	}

	@RequestMapping(value = "/{boardname}/{days}", method = RequestMethod.GET)
	public String getBoard(@PathVariable("boardname") String boardname, @PathVariable("days") int days, Model model,
			Principal principal) {
		DateTimeFormatter formatter;
		int nextDays = DEFAULT_NUMBER_OF_DAYS;

		if (days == EXTENDED_NUMBER_OF_DAYS) {
			nextDays = EXTENDED_NUMBER_OF_DAYS;
			formatter = DateTimeFormat.forPattern("EE dd.MM");
		} else {
			formatter = DateTimeFormat.forPattern("EEEE dd.MM");
		}
		week = new ArrayList<LocalDate>(CalenderHelper.returnNextFiveDays(nextDays));
		eventsInThisWeek = new ArrayList<Event>();
		List<String> weekDays = new ArrayList<String>();

		for (LocalDate day : week) {
			weekDays.add(formatter.print(day));
			eventsInThisWeek.addAll(eventRepository.findByDate(day));
		}
		BoardWeeklyEvents events = new BoardWeeklyEvents(boardService.findByName(boardname), eventsInThisWeek, week);

		model.addAttribute("week", weekDays);
		model.addAttribute("events", events);
		model.addAttribute("name", principal.getName());

		if (days == EXTENDED_NUMBER_OF_DAYS) {
			return "board10";
		} else {
			return "board";
		}

	}
}
