package mediscreen.noteservice.entity;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Note {

	public Note(String noteContent, String firstName, String lastName) {
		this.noteContent = noteContent;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Id
	private String id;

	private String noteContent;

	private String firstName;

	private String lastName;
}
