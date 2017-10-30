package common.entities.dto;


import org.hibernate.validator.constraints.NotEmpty;

public class BoardAndMemberDto {

	@NotEmpty
	private String boardName;
	@NotEmpty
	private String memberName;

	private Long boardId;

	private Long memberId;

	public BoardAndMemberDto() {

	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String userName) {
		this.memberName = userName;
	}

	public Long getBoardId() {
		return boardId;
	}

	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long userId) {
		this.memberId = userId;
	}

	@Override
	public String toString() {
		return "BoardAndUserDto [boardName=" + boardName + ", userName=" + memberName + ", boardId=" + boardId
				+ ", userId=" + memberId + "]";
	}

}
