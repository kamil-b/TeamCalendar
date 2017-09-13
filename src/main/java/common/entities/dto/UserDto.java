package common.entities.dto;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import common.entities.Board;

import common.entities.enums.JobRole;
import common.entities.enums.UserRole;
import common.helpers.customannotations.PasswordMatches;

@PasswordMatches
public class UserDto {

	@NotNull
	@NotEmpty
	@NotBlank
	@Length(min = 5, message = "The field must be at least 5 characters")
	private String name;
	@NotNull
	@NotEmpty
	@NotBlank
	private String password;
	@NotNull
	@NotEmpty
	@NotBlank
	private String matchingPassword;
	@NotNull
	@NotEmpty
	@NotBlank
	private String email;
	private Long id;
	@Enumerated(EnumType.STRING)
	private JobRole role;
	private String superior;
	private String remoteWorkLocation;
	@Enumerated(EnumType.STRING)
	private UserRole userRole = UserRole.USER;

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

	public String getRemoteWorkLocation() {
		return remoteWorkLocation;
	}

	public void setRemoteWorkLocation(String remoteWorkLocation) {
		this.remoteWorkLocation = remoteWorkLocation;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "UserDto [name=" + name + ", password=" + password + ", matchingPassword=" + matchingPassword
				+ ", email=" + email + ", id=" + id + ", role=" + role + ", superior=" + superior
				+ ", remoteWorkLocation=" + remoteWorkLocation + ", userRole=" + userRole + ", boards=" + boards + "]";
	}

}
