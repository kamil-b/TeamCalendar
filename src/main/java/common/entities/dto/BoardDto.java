package common.entities.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import common.entities.User;

public class BoardDto {

	@NotNull
	@NotEmpty
	@Column(unique=true)
	private String name;
	
	private Long id;
	private List<User> members = new ArrayList<User>();
	
	@NotEmpty
	@NotNull
	private String description;
	
	@NotNull
	private String ownerName;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "BoardDto [name=" + name + ", members=" + members + ", description=" + description + ", ownerName="
				+ ownerName + "]";
	}
	
	
}
