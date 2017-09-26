package common.repository;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import common.entities.Board;
import common.entities.User;
import common.entities.dto.BoardDto;

@Service
public class BoardService {
	
	private final static Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	@Autowired
	private BoardRepository repository;

	@Autowired
	private UserService userService;

	public Board findByName(String name) {
		return repository.findByName(name);
	}

	public List<Board> findAll() {
		Iterable<Board> boardlist = repository.findAll();
		List<Board> list = new ArrayList<Board>();
		boardlist.forEach(list::add);
		return list;
	}

	public void save(Board board) {
		repository.save(board);
	}

	public void deleteBoard(Board board) {
		repository.delete(board);
	}

	/**
	 * Creates Board from BoardDto. Owner is also added to the members list.
	 * 
	 * @param boardDto
	 * @return
	 */
	public Board getBoardFromBoardDto(BoardDto boardDto) {
		Board board = new Board();
		User user = userService.findByName(boardDto.getOwnerName());
		board.setOwner(user.getName());
		board.addUserToBoard(user);
		board.setName(boardDto.getName());
		board.setDescription(boardDto.getDescription());
		logger.debug("Board: " + board.toString() + " has been created");
		return board;
	}
}
