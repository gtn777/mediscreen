package mediscreen.reporting.domain;

import lombok.Data;

@Data
public class NoteDto {

	public NoteDto() {
		super();
	}

	private String id;

	private Integer patId;

	private String note;

}
