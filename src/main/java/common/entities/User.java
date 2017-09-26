package common.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import common.entities.enums.JobRole;
import common.entities.enums.UserRole;

@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String superior;
	private String name;
	private String password;

	@ManyToMany(targetEntity = Board.class, cascade = CascadeType.ALL)
	private List<Board> boards = new ArrayList<Board>();
	private String email;
	private JobRole role;
	private String remoteWorkLocation;
	private UserRole userRole = UserRole.USER;
	private String phone;
	private String room;

	@Column(columnDefinition = "image")
	private byte[] image;

	// for JPA
	public User() {
	}

	public User(Long id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public List<Board> getBoards() {
		return boards;
	}

	public void setBoards(List<Board> boards) {
		this.boards = boards;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority(userRole.toString()));
		return list;
	}

	@Override
	public String getUsername() {
		return getName();
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
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

	public void addBoardToList(Board board) {
		boards.add(board);
	}

	public void removeBoardFromList(Board board) {
		boards.remove(board);
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getSuperior() {
		return superior;
	}

	public void setSuperior(String superior) {
		this.superior = superior;
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
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", superior=" + superior + ", name=" + name + ", password=" + password + ", email="
				+ email + ", role=" + role + ", remoteWorkLocation=" + remoteWorkLocation + ", userRole=" + userRole;
	}

}