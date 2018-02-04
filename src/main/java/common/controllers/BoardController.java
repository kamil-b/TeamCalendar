package common.controllers;

import common.entities.BoardEvents;
import common.entities.Event;
import common.helpers.CalenderHelper;
import common.repository.BoardService;
import common.repository.EventService;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

    private static final int DEFAULT_NUMBER_OF_DAYS = 5;
    private static final int EXTENDED_NUMBER_OF_DAYS = 10;

    @Autowired
    private BoardService boardService;

    @Autowired
    private EventService eventService;

    private List<LocalDate> daysList;
    private List<Event> eventsInThisWeek;

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
        daysList = new ArrayList<>(CalenderHelper.getNextDays(nextDays));
        eventsInThisWeek = new ArrayList<>();
        List<String> weekDays = new ArrayList<>();

        for (LocalDate day : daysList) {
            weekDays.add(formatter.print(day));
            eventsInThisWeek.addAll(eventService.findByDate(day));
        }
        BoardEvents events = new BoardEvents(boardService.findByName(boardname), eventsInThisWeek, daysList);
        model.addAttribute("week", weekDays);
        model.addAttribute("events", events);
        model.addAttribute("logged", principal != null);
        if (principal != null) {
            model.addAttribute("name", principal.getName());
        }
        model.addAttribute("boardname", boardname);
        model.addAttribute("weekNumber", CalenderHelper.getCurrentWeekNumber());

        if (days == EXTENDED_NUMBER_OF_DAYS) {
            return "board10";
        } else {
            return "board";
        }

    }
}
