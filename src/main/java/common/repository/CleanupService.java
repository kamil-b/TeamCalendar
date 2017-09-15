package common.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import common.entities.Event;

@Service
@EnableScheduling
public class CleanupService {
	private static final int WEEK_BEFORE = 7;
	private static final int ONE_DAY_BEFORE = 1;
	private final static Logger logger = LoggerFactory.getLogger(CleanupService.class);
	@Autowired
	private EventRepository eventRepository;

	 //@Scheduled(cron = "*/60 * * * * *") // each minute : for testing
	@Scheduled(cron = "0 0 * * 7 *") // each Sunday at midnight
	private void cleanupOldEvents() {
		logger.info("Starting cleaning process for old events...");
		LocalDate now = new LocalDate();
		List<Event> oldEvents = new ArrayList<Event>();
		/*
		 * for (int i = 1; i < WEEK_BEFORE; i++) {
		 * oldEvents.addAll(eventRepository.findByDate(now.minusDays(i))); }
		 */
		IntStream.range(ONE_DAY_BEFORE, WEEK_BEFORE).forEach(x -> oldEvents.addAll(eventRepository.findByDate(now.minusDays(x))));
		
		for (Event ev : oldEvents) {
			logger.info("Removing event: " + ev);
			eventRepository.delete(ev.getId());
		}
		logger.info("Old events Removed.");
	}

}
