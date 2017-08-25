package common.entities.wraper;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import common.entities.dto.EventDto;

public class EventDtoListForm {

	private ArrayList<EventDto> eventslist;

	public EventDtoListForm(){
		
	}

	public List<EventDto> getEventslist() {
		return eventslist;
	}

	public void setEventslist(ArrayList<EventDto> eventslist) {
		this.eventslist = eventslist;
	}
	

	public EventDto get(int index){
		return eventslist.get(index);
	}
	
	
	public boolean isWeekend(LocalDate date) {
		if (date.getDayOfWeek() == 6 || date.getDayOfWeek() == 7) {
			return true;
		}
		return false;

	}

	
}
