package common.entities.wraper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.joda.time.LocalDate;

import common.entities.Event;

/**
 * Class Wrapper for: <br>
 * {@code
 * Map<LocalDate, Event>}
 */
public class EventsForNext30DaysForm {

	private Map<LocalDate, Event> events = new HashMap<LocalDate, Event>();

	public EventsForNext30DaysForm() {
	}

	public boolean isWeekend(LocalDate date) {
		if (date.getDayOfWeek() == 6 || date.getDayOfWeek() == 7) {
			return true;
		}
		return false;

	}

	public int getNumber(LocalDate date) {
		Set<LocalDate> set = events.keySet();
		LocalDate[] array = new LocalDate[30];
		set.toArray(array);
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(date)) {
				return i;
			}
		}
		throw new IllegalArgumentException("Couldnt find date in next 30 days date array");
	}

	public Map<LocalDate, Event> getEvents() {
		return events;
	}

	public void setEvents(Map<LocalDate, Event> events) {
		this.events = events;
	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this
	 * map contains no mapping for the key
	 * 
	 * @param LocalDate
	 *            date
	 * @return
	 */
	public Event get(LocalDate date) {
		return events.get(date);
	}

	public Set<LocalDate> keySet() {
		return events.keySet();
	}
}
