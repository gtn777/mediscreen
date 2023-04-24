package mediscreen.note.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "notes")
public class Note {

	public Note(Integer patId, String note) {
		super();
		this.patId = patId;
		this.note = note;
	}

	@Id
	private String id;

	private Integer patId;

	private String note;
}
