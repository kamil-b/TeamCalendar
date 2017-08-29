package common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import common.entities.User;
import common.entities.dto.UserDto;
import common.entities.enums.JobRole;
import common.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	public List<User> findAllByRole(JobRole role) {
		return repository.findByRole(role);
	}

	public User findByName(String name) {
		return repository.findByName(name);
	}

	public void deleteById(Long id) {
		repository.delete(id);
	}

	public void deleteUser(User user) {
		repository.delete(user);
	}

	public User findById(Long id) {
		return repository.findById(id);
	}

	public List<User> findAll() {
		return repository.findAll();
	}

	public void save(User registered) {
		repository.save(registered);
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		return repository.findByName(name);
	}

	public User createUserAccount(UserDto userDto, BindingResult result) {

		if (userExists(userDto.getName())) {
			throw new IllegalArgumentException("Username allready exists in base");
		}

		User registered = new User();
		registered.setName(userDto.getName());
		registered.setPassword(userDto.getPassword());
		registered.setBoards(userDto.getBoards());
		registered.setEmail(userDto.getEmail());
		registered.setRole(userDto.getRole());
		registered.setSuperior(userDto.getSuperior());
		repository.save(registered);
		return registered;
	}

	public UserDto returnUserDto(User user) {
		UserDto userDto = new UserDto();

		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setPassword(user.getPassword());
		userDto.setBoards(user.getBoards());
		userDto.setEmail(user.getEmail());
		userDto.setRole(user.getRole());
		userDto.setSuperior(user.getSuperior());
		userDto.setRemoteWorkLocation(user.getRemoteWorkLocation());
		return userDto;
	}

	public User returnUser(UserDto userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setBoards(userDto.getBoards());
		user.setEmail(userDto.getEmail());
		user.setRole(userDto.getRole());
		user.setSuperior(userDto.getSuperior());
		user.setRemoteWorkLocation(userDto.getRemoteWorkLocation());
		return user;
	}

	public boolean userExists(String name) {
		User user = repository.findByName(name);
		if (user != null) {
			return true;
		}
		return false;
	}

	public List<User> getAllUsersForSuperior(String superior) {
		return repository.findBySuperior(superior);
	}
}
