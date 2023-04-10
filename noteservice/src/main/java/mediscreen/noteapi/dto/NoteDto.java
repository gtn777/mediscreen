package mediscreen.noteapi.dto;

import lombok.Data;
import mediscreen.noteapi.entity.Note;

@Data
public class NoteDto {

	public NoteDto() {
		super();
	}

	public NoteDto(Note n) {
		super();
		this.id = n.getId();
		this.patId = n.getPatId();
		this.note = n.getNote();
	}

	private String id;

	private Integer patId;

	private String note;
}
