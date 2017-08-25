package common.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import common.entities.enums.EventStatus;
import common.entities.enums.EventType;

@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	
	private String username;
	private EventType eventType = EventType.NO_EVENT;
	private EventStatus eventStatus = EventStatus.NEW;

	public Event(String username, LocalDate date, EventType eventType) {
		this.username = username;
		this.date = date;
		this.eventType = eventType;
	}

	public Event() {
		super();
	}

	public LocalDate getDate() {
		return date;
	}

	public String getUsername() {
		return username;
	}

	public EventType getEventType() {
		return eventType;
	}

	@Override
	public String toString() {
		return "Event [date=" + date + ", username=" + username + ", eventType=" + eventType + "]";
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public EventStatus getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(EventStatus eventStatus) {
		this.eventStatus = eventStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
