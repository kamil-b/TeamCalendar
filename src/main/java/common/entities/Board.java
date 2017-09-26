package common.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String description;

	private String owner;

	@ManyToMany(targetEntity = User.class, cascade = CascadeType.ALL)
	//@JsonIgnore
	List<User> members = new ArrayList<User>();

	public Board(String name) {
		this.name = name;
	}

	public Board() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public void addUserToBoard(User user) {
		members.add(user);

	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void removeUserFromBoard(User user) {
		members.remove(user);
	}

	@Override
	public String toString() {
		return "Board [id=" + id + ", name=" + name + ", description=" + description + ", owner=" + owner + ", members="
				+ members + "]";
	}

}
