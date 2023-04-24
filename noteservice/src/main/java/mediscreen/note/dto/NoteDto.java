package mediscreen.note.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mediscreen.note.entity.Note;

@Getter
@Setter
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
