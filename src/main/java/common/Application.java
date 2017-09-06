package common;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import common.entities.User;
import common.entities.enums.JobRole;
import common.entities.enums.UserRole;
import common.repository.UserService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	public CommandLineRunner createUserWithAdminRights(UserService service) {
		return (args) -> {
			
				User kamil = new User(1l, "kamil", "123");
				kamil.setUserRole(UserRole.ADMIN);
				kamil.setRole(JobRole.DEVELOPER);
				kamil.setEmail("kamil.bednarczyk@ericsson.com");
				service.createUserAccount(service.returnUserDto(kamil), null);
		
		};
	}
	/*
	 * @Bean public CommandLineRunner demoEvent(UserRepository repository) {
	 * return (args) -> { User kamil = new User("kamil", "123");
	 * kamil.setSuperior("monika"); kamil.setRole(JobRole.DEVELOPER);
	 * kamil.setEmail("kamil.bednarczyk@ericsson.com");
	 * board1.addUserToBoard(kamil); repository.save(kamil); User kamil2 = new
	 * User("kamil2", "123"); kamil2.addBoardToList(board1);
	 * repository.save(kamil2);
	 * 
	 * User monika = new User("monika" , "123");
	 * monika.setRole(JobRole.LINE_MANAGER); repository.save(monika); };
	 * 
	 * }
	 */
	/*
	 * @Bean public CommandLineRunner demo(TipRepository repository) { return
	 * (args) -> {
	 * 
	 * repository.save(new Tip("Linux", "content", "tralalaa", "hint"));
	 * repository.save(new Tip("Maven", "cotamdfasdfsf", "rdghjkjhgfg", ""));
	 * repository.save(new Tip("Git", "asfasfasf", "asdf as  aasdd", ""));
	 * repository.save(new Tip("Git", "sfsaf asfs afas f", "asdfas a  asfas ",
	 * "hint lorem ipsum dipsum cupa"));
	 * 
	 * };
	 * 
	 * }
	 */
	/*
	 * @Bean public CommandLineRunner boardDemo(BoardRepository repository) {
	 * return (args) -> { // Board board1 = new Board("neutrino");
	 * 
	 * board1.setDescription("BID TAO Neutrino team board. MJE 2G/3G");
	 * 
	 * repository.save(board1);
	 * 
	 * Board board2 = new Board("no problem"); board2.addUserToBoard(new
	 * User("arek", "password"));
	 * board2.setDescription("BID TAO TN No problem team board. TN");
	 * 
	 * repository.save(board2); };
	 * 
	 * }
	 */

}
