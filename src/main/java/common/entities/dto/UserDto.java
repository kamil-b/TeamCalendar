package common.entities.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import common.entities.Board;

import common.entities.enums.JobRole;
import common.helpers.customannotations.PasswordMatches;

@PasswordMatches
public class UserDto {

	@NotNull
	@NotEmpty
	@Column(unique = true)
	private String name;

	@NotNull
	@NotEmpty
	private String password;

	@NotNull
	@NotEmpty
	private String matchingPassword;

	@NotNull
	@NotEmpty
	private String email;

	private Long id;

	@Enumerated(EnumType.STRING)
	private JobRole role;

	private String superior;

	private List<Board> boards = new ArrayList<Board>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public List<Board> getBoards() {
		return boards;
	}

	public void setBoards(List<Board> boards) {
		this.boards = boards;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public JobRole getRole() {
		return role;
	}

	public void setRole(JobRole role) {
		this.role = role;
	}

	public String getSuperior() {
		return superior;
	}

	public void setSuperior(String superior) {
		this.superior = superior;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserDto [name=" + name + ", password=" + password + ", matchingPassword=" + matchingPassword
				+ ", email=" + email + ", id=" + id + ", role=" + role + ", superior=" + superior + ", boards=" + boards
				+ "]";
	}

}
