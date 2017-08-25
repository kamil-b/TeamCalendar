package common.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import common.entities.enums.EventType;

public class BoardWeeklyEvents {

	private Map<LocalDate, List<Event>> eventsByDay;
	private List<Event> eventsInThisWeek = new ArrayList<Event>();
	private Board board;
	private List<LocalDate> week;
	private EventType NO_EVENT = EventType.NO_EVENT;

	public BoardWeeklyEvents(Board board, List<Event> eventsInThisWeek, List<LocalDate> week) {
		this.board = board;
		this.eventsInThisWeek = eventsInThisWeek;
		this.week = week;
		sortEventsByDays();
	}

	private void sortEventsByDays() {
		eventsByDay = new HashMap<LocalDate, List<Event>>();
		for (LocalDate day : week) {
			ArrayList<Event> eventsInSpecificDay = new ArrayList<Event>();
			for (Event event : eventsInThisWeek) {
				if (event.getDate().getDayOfWeek() == day.getDayOfWeek()) {
					eventsInSpecificDay.add(event);
				}
			}
			eventsByDay.put(day, eventsInSpecificDay);
		}
	}

	public List<User> getBoardMembers() {
		return board.getMembers();
	}

	public List<Event> getEventsInThisWeek() {
		return eventsInThisWeek;
	}

	public boolean isEventToday(LocalDate date1, LocalDate date2) {
		if (date1.isEqual(date2)) {
			return true;
		} else {
			return false;
		}
	}

	public List<LocalDate> getWeek() {
		return week;
	}

	public EventType getNO_EVENT() {
		return NO_EVENT;
	}

	public Map<LocalDate, List<Event>> getEventsByDays() {
		return eventsByDay;
	}

	public String getBoardName() {
		return board.getName();
	}

	public List<Event> getEventsBySpecificDate(LocalDate date) {
		if (eventsByDay.containsKey(date)) {
			List<Event> tmp = eventsByDay.get(date);
			return tmp;
		} else {
			throw new IllegalArgumentException("No such date in events");
		}
	}

}
