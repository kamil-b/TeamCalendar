package common.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import common.entities.Board;
import common.entities.User;
import common.repository.BoardService;
import common.repository.UserService;

@RestController
public class RestTestController {

	@Autowired
	private UserService userService;

	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "/rest")
	public Board returnUser() {
		return boardService.findByName("neutrino");

	}

	@RequestMapping(value = "/rest2")
	public User returnUser1() {
		User user = userService.findByName("kamil");
		return user;
	}

	class Response {
		private int id;
		private String response;

		public Response(int id, String s) {
			this.response = s;
			this.id = id;
		}

		public String getResponse() {
			return response;
		}

		public void setResponse(String response) {
			this.response = response;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	}
}
