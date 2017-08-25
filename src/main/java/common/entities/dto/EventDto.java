package common.entities.dto;

import javax.validation.constraints.NotNull;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import common.entities.enums.EventStatus;
import common.entities.enums.EventType;

public class EventDto {

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	private Long Id;

	@NotNull
	private String username;

	@NotNull
	private EventType eventType;
	
	private EventStatus eventStatus = EventStatus.NEW;

	public EventDto() {
	}

	public EventDto(String username, LocalDate date, EventType eventType) {
		this.date = date;
		this.username = username;
		this.eventType = eventType;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate localDate) {
		this.date = localDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public EventType getEventType() {
		return eventType;
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
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	@Override
	public String toString() {
		return "EventDto [date=" + date + ", Id=" + Id + ", username=" + username + ", eventType=" + eventType
				+ ", eventStatus=" + eventStatus + "]";
	}


}
