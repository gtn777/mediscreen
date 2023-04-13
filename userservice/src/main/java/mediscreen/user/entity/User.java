package mediscreen.user.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import mediscreen.user.dto.UserDto;

@Data
@Entity
@Table(name = "user")
public class User {

	public User() {
		super();
	}

	public User(UserDto dto) {
		super();
		this.lastName = dto.getFamily();
		this.firstName = dto.getGiven();
		this.dateOfBirth = dto.getDob();
		this.genre = dto.getSex();
		this.postalAddress = dto.getAddress();
		this.telephone = dto.getPhone();
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
