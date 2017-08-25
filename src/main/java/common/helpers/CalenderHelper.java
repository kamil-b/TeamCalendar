package common.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class CalenderHelper {

	private static final int NUMBER_OF_DAYS_FORWARD = 5;

	/**
	 * Returns List containing days of the actual week.
	 * 
	 * @return currentWeek
	 */
	public static List<LocalDate> returnCurrentWeek() {
		List<LocalDate> currentWeek = new ArrayList<LocalDate>();

		LocalDate now = new LocalDate();
		LocalDate[] week = new LocalDate[NUMBER_OF_DAYS_FORWARD];
		for (int i = 0; i < NUMBER_OF_DAYS_FORWARD; i++) {
			week[i] = now.withDayOfWeek(DateTimeConstants.MONDAY + i);
		}
		currentWeek.addAll(Arrays.asList(week));

		return currentWeek;

	}

	/**
	 * Returns next five days from today. Skips weekend days.
	 * 
	 * @return nexFiveDays
	 */
	public static List<LocalDate> returnNextFiveDays() {
		List<LocalDate> nexFiveDays = new ArrayList<LocalDate>();
		LocalDate now = new LocalDate();
		int i = 0;
		while (nexFiveDays.size() < NUMBER_OF_DAYS_FORWARD) {
			if (now.plusDays(i).getDayOfWeek() <= 5) {
				nexFiveDays.add(now.plusDays(i));

			}
			i++;
		}
		return nexFiveDays;

	}
}
