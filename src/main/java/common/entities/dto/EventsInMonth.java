package common.entities.dto;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.joda.time.LocalDate;

import common.entities.Event;
import common.entities.enums.EventType;

public class EventsInMonth {

	private final int month;
	private final int year;
	private Locale locale = Locale.ENGLISH;
	private List<Event> events;
	private Map<EventType, Integer> eventsCountedByType;

	public EventsInMonth(LocalDate date, Event event) {
		this.month = date.getMonthOfYear();
		this.year = date.getYear();
		this.events = new ArrayList<>();
		this.events.add(event);
		this.eventsCountedByType = new HashMap<>();
	}

	public void countEventsByType() {
		Map<EventType, Integer> eventsByTypes = new HashMap<>();
		for (EventType type : EventType.values()) {
			eventsByTypes.put(type, 0);
		}
		for (Event e : events) {
			eventsByTypes.compute(e.getEventType(), (k, v) -> (v == 0) ? 1 : (v = v + 1));
		}
		eventsCountedByType.putAll(eventsByTypes);

	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public EventsInMonth addEvent(Event event) {
		events.add(event);
		return this;
	}

	public EventsInMonth addEvents(List<Event> events) {
		events.addAll(events);
		return this;
	}

	public Map<EventType, Integer> getEventsCountedByType() {
		return eventsCountedByType;
	}

	@Override
	public String toString() {
		return "EventsInMonth [month=" + month + ", year=" + year + ", locale=" + locale + ", events=" + events
				+ ", eventsCountedByType=" + eventsCountedByType + "]";
	}

	public String getMonth(int month) {
		return new DateFormatSymbols(locale).getShortMonths()[month - 1];
	}
}
