package mediscreen.user.dto;

import java.time.LocalDate;

import lombok.Data;
import mediscreen.user.entity.User;

@Data
public class UserDto {

	public UserDto() {
		super();
	}

	public UserDto(User u) {
		super();
		this.family = u.getLastName();
		this.given = u.getFirstName();
		this.dob = u.getDateOfBirth();
		this.sex = u.getGenre();
		this.address = u.getPostalAddress();
		this.phone = u.getTelephone();
	}

	private String family;

	private String given;

	private LocalDate dob;

	private String sex;

	private String address;

	private String phone;

}
