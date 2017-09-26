package common.entities.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class BoardAdminPanelDto {

	@NotNull
	@NotEmpty
	private String name;

	public BoardAdminPanelDto() {
	}
	
	public BoardAdminPanelDto(String boardName) {
		this.name = boardName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
