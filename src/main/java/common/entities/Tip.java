package common.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tip {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String section;
	private String content;
	private String title;
	private String hint;

	// for JPA only
	public Tip() {
	}

	public Tip(String section, String content, String title, String hint) {
		this.section = section;
		this.content = content;
		this.title = title;
		this.hint = hint;
	}

	@Override
	public String toString() {
		return "Tip [id=" + id + ", section=" + section + ", content=" + content + ", title=" + title + ", hint=" + hint + "]";
	}

	public String getSection() {
		return section;
	}

	public String getContent() {
		return content;
	}

	public String getTitle() {
		return title;
	}

	public Long getId() {
		return id;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

}
