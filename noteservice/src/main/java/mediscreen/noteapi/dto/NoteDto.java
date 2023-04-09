package mediscreen.noteapi.dto;

import lombok.Data;
import mediscreen.noteapi.entity.Note;

@Data
public class NoteDto {

	private String noteContent;

	private String lastName;

	private String firstName;

	public NoteDto() {
		super();
	}

	public NoteDto(Note n) {
		super();
		this.firstName = n.getFirstName();
		this.lastName = n.getLastName();
		this.noteContent = n.getNoteContent();
	}

}
