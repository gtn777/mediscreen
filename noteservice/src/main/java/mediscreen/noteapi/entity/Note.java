package mediscreen.noteapi.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import mediscreen.noteapi.dto.NoteDto;

@Data
@Document
public class Note {

	public Note(String noteContent, String firstName, String lastName) {
		this.noteContent = noteContent;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Note(NoteDto dto) {
		this.noteContent = dto.getNoteContent();
		this.firstName = dto.getFirstName();
		this.lastName = dto.getLastName();
	}

	@Id
	private String id;

	private String noteContent;

	private String firstName;

	private String lastName;
}
