package common.repository;

import org.springframework.data.repository.CrudRepository;
import common.entities.Board;

public interface BoardRepository extends  CrudRepository<Board, Long>{

	Board findByName(String name);
	
}
