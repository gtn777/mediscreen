package mediscreen.reporting.domain;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PatientDto {

	public PatientDto() {
		super();
	}

	private int patId;
	
	private String family;

	private String given;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;

	private String sex;

	private String address;

	private String phone;


}
