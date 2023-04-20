package mediscreen.user.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import mediscreen.user.entity.User;

@Data
public class UserDto {

	public UserDto() {
		super();
	}

	public UserDto(User u) {
		super();
		this.patId= u.getId();
		this.family = u.getLastName();
		this.given = u.getFirstName();
		this.dob = u.getDateOfBirth();
		this.sex = u.getGenre();
		this.address = u.getPostalAddress();
		this.phone = u.getTelephone();
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
