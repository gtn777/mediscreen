package mediscreen.user.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class NewUserDto {

	public NewUserDto() {
		super();
	}

	private String family;

	private String given;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;

	private String sex;

	private String address;

	private String phone;

}
