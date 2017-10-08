package common.repository;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import common.entities.Event;

interface EventRepository extends CrudRepository<Event, Long> {

	List<Event> findByDate(LocalDate dates);

	List<Event> findByUsernameOrderByDateAsc(String username);

	void deleteById(Long id);

	Event findById(Long id);

	void deleteByDate(List<LocalDate> dates);

	void deleteByDate(LocalDate date);

	@Query("select e from Event e where e.date <= ?")
	List<Event> findByDateLessThan(LocalDate date);
}
