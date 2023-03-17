package mediscreen.userservice.dto;

import java.time.LocalDate;

import lombok.Data;
import mediscreen.userservice.entity.User;

@Data
public class UserDto {

	public UserDto() {
		super();
	}

	public UserDto(User u) {
		super();
		this.lastName = u.getLastName();
		this.firstName = u.getFirstName();
		this.dateOfBirth = u.getDateOfBirth();
		this.genre = u.getGenre();
		this.postalAddress = u.getPostalAddress();
		this.telephone = u.getTelephone();
	}

	private String lastName;

	private String firstName;

	private LocalDate dateOfBirth;

	private String genre;

	private String postalAddress;

	private String telephone;

}
