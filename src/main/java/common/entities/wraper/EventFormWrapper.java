package common.entities.wraper;

import common.entities.Event;

public class EventFormWrapper {

	private Event[] events;
	
	public EventFormWrapper(){
		events = new Event[30];
	}

	public Event[] getEvents() {
		return events;
	}

	public void setEvents(Event[] events) {
		this.events = events;
	}
	
	public Event getElement(int index){
		return events[index];
		
	}
}
