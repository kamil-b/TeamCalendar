package common.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import common.entities.User;
import common.entities.enums.JobRole;

interface UserRepository extends CrudRepository<User, Long>{

	User findByName(String name);

	List<User> findBySuperior(String superior);
	
	List<User> findByRole(JobRole role);
	
	List<User> findAll();
	
	User findById(Long id);
	
}
