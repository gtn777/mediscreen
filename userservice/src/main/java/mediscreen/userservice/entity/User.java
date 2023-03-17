package mediscreen.userservice.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import mediscreen.userservice.dto.UserCreationDto;

@Data
@Entity
@Table(name = "user")
public class User {

	public User() {
		super();
	}

	public User(UserCreationDto dto) {
		super();
		this.lastName = dto.getLastName();
		this.firstName = dto.getFirstName();
		this.dateOfBirth = dto.getDateOfBirth();
		this.genre = dto.getGenre();
		this.postalAddress = dto.getPostalAddress();
		this.telephone = dto.getTelephone();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false, unique = true)
	private int id;

	private String lastName;

	private String firstName;

	private LocalDate dateOfBirth;

	private String genre;

	private String postalAddress;

	private String telephone;

}
