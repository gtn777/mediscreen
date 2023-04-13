package mediscreen.reporting.domain;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PatientDto {

	public PatientDto() {
		super();
	}

	private String family;

	private String given;

	private LocalDate dob;

	private String sex;

	private String address;

	private String phone;

}
