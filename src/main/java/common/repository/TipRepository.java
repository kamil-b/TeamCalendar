package common.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import common.entities.Tip;

public interface TipRepository extends CrudRepository<Tip, Long>{
	
	List<Tip> findBySection(String section);

}
