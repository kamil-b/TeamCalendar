package common.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import common.entities.Event;
import common.entities.User;
import common.entities.dto.EventDto;
import common.entities.enums.EventType;

@Service
public class EventService {

	private final static Logger logger = LoggerFactory.getLogger(EventService.class);
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserService userService;

	public EventService() {
	}

	public List<Event> findByDate(LocalDate date) {
		return eventRepository.findByDate(date);
	}

	public List<Event> getAllEventsForUser(User user) {
		return getAllEventsForUser(user.getUsername());
	}

	/**
	 * Return list of events for specific user. Sorted from the oldest to newest.
	 * @param username
	 * @return list of events 
	 */
	public List<Event> getAllEventsForUser(String username) {
		return eventRepository.findByUsernameOrderByDateAsc(userService.findByName(username).getUsername());
	}

	public boolean isWeekend(LocalDate date) {
		if (date.getDayOfWeek() == 6 || date.getDayOfWeek() == 7) {
			return true;
		}
		return false;

	}

	public List<EventDto> returnListOfEventDto(Map<LocalDate, Event> events) {
		List<EventDto> eventsDtoList = new ArrayList<>();
		for (Event event : events.values()) {
			EventDto eventDto = returnEventDto(event);
			eventsDtoList.add(eventDto);
		}
		return eventsDtoList;
	}

	public List<EventDto> returnListOfEventDto(List<Event> events) {
		List<EventDto> eventsDtoList = new ArrayList<>();
		for (Event event : events) {
			EventDto eventDto = returnEventDto(event);
			eventsDtoList.add(eventDto);
		}
		return eventsDtoList;
	}

	public Map<LocalDate, Event> getAllEventsForUserForNextDays(String username, int numberOfDays) {
		LocalDate date = new LocalDate(LocalDate.now());
		Map<LocalDate, Event> eventsInNextDays = new LinkedHashMap<LocalDate, Event>();
		List<Event> events;
		IntStream.range(0, numberOfDays).forEach(i -> eventsInNextDays.put(date.plusDays(i),
				new Event(username, date.plusDays(i), EventType.NO_EVENT)));

		events = eventRepository.findByUsernameOrderByDateAsc(userService.findByName(username).getUsername());

		for (Event event : events) {
			LocalDate day = event.getDate();
			if (eventsInNextDays.containsKey(day)) {
				eventsInNextDays.put(day, event);
			}
		}

		return eventsInNextDays;

	}

	public Event getEventById(Long id) {
		return eventRepository.findById(id);
	}

	public void save(List<Event> events) {
		eventRepository.save(events);
		logger.info("Multiple events added to database:");
		for (Event ev : events) {
			logger.info(ev.toString());
		}

	}

	public void save(Event event) {
		eventRepository.save(event);
		logger.info("Event: " + event.toString() + " added to database");

	}

	/**
	 * Use save instead.
	 * 
	 * @deprecated
	 * @param event
	 */
	@Deprecated
	public void update(Event event) {
		eventRepository.save(event);
	}

	public Event updateAndReturnUpdated(Event event) {

		Event updatedEvent = eventRepository.save(event);
		return updatedEvent;
	}

	public ArrayList<EventDto> getChangedEventsDto(List<EventDto> all, List<EventDto> changed) {
		ArrayList<EventDto> changedEvents = new ArrayList<>();
		for (int i = 0; i < changed.size(); i++) {
			if (!changed.get(i).getEventType().equals(all.get(i).getEventType())) {
				changedEvents.add(changed.get(i));
			}
		}
		return changedEvents;
	}

	/**
	 * Convert Event to EventDto
	 * 
	 * @param event
	 * @return EventDto
	 */
	public EventDto returnEventDto(Event event) {
		EventDto eventDto = new EventDto();
		eventDto.setUsername(event.getUsername());
		eventDto.setDate(event.getDate());
		eventDto.setEventType(event.getEventType());
		eventDto.setId(event.getId());
		eventDto.setEventStatus(event.getEventStatus());
		return eventDto;

	}

	/**
	 * Convert EventDto to Event
	 * 
	 * @param eventDto
	 * @return Event
	 */
	public Event returnEvent(EventDto eventDto) {
		Event event = new Event();
		event.setUsername(eventDto.getUsername());
		event.setEventType(eventDto.getEventType());
		event.setDate(eventDto.getDate());
		event.setId(eventDto.getId());
		event.setEventStatus(eventDto.getEventStatus());
		if (event.getUsername() != null || event.getDate() != null || event.getEventType() != null) {
			return event;
		} else {
			throw new IllegalArgumentException("One of the variables isnt set. Object:  " + event.toString());
		}
	}

	/**
	 * 
	 * @param superior
	 * @return list of events for users belonged to specific superior
	 */
	public List<Event> getAllEventsBelongedToSuperior(String superior) {
		List<User> users = userService.getAllUsersForSuperior(superior);
		List<Event> events = new ArrayList<>();

		for (User user : users) {
			events.addAll(eventRepository.findByUsernameOrderByDateAsc(user.getName()));
		}
		return events;
	}

}
