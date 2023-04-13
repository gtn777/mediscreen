package mediscreen.note.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientAllNoteDto {

	public PatientAllNoteDto() {
		super();
	}

	private List<NoteDto> noteDtos = new ArrayList<>();

}
