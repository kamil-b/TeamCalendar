package common;

import org.joda.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import common.entities.Event;
import common.entities.User;
import common.entities.enums.EventType;
import common.entities.enums.JobRole;
import common.entities.enums.UserRole;
import common.repository.EventService;
import common.repository.UserService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	public CommandLineRunner createUserWithAdminRights(UserService service, EventService eventService) {
		return (args) -> {

			User kamil = new User(1l, "kamil", "123");
			kamil.setUserRole(UserRole.ADMIN);
			kamil.setRole(JobRole.DEVELOPER);
			kamil.setEmail("kamil.bednarczyk@ericsson.com");
			service.createUserAccount(service.returnUserDto(kamil));
			
			//eventService.save(new Event("kamil",new LocalDate().minusDays(5),EventType.TRAINING));
		};
	}

}
