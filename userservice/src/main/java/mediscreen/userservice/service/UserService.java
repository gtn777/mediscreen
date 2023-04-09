package mediscreen.userservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import mediscreen.userservice.dto.UserDto;
import mediscreen.userservice.entity.User;
import mediscreen.userservice.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public UserDto addUser(UserDto dto) {
		if (doesUserExists(dto.getFirstName(), dto.getLastName())) {
			throw new EntityExistsException(
					"User " + dto.getFirstName() + " " + dto.getLastName() + " already exists.");
		} else {
			return new UserDto(userRepository.save(new User(dto)));
		}
	}

	public UserDto getUserDtoByName(String firstName, String lastName) {
		return new UserDto(getUserEntityByName(firstName, lastName));
	}

	public UserDto updateUser(UserDto dto) {
		User userToUpdate = getUserEntityByName(dto.getFirstName(), dto.getLastName());
		User userUpDated = new User(dto);
		userUpDated.setId(userToUpdate.getId());
		return new UserDto(userRepository.save(userUpDated));
	}

	public void deleteUser(String firstName, String lastName) {
		User userToDelete = this.getUserEntityByName(firstName, lastName);
		userRepository.delete(userToDelete);
	}

	public List<UserDto> getAllUser() {
		Iterable<User> allUsersIterable = userRepository.findAll();
		return StreamSupport.stream(allUsersIterable.spliterator(), false).map(u -> new UserDto(u)).toList();

	}

	private User getUserEntityByName(String firstName, String lastName) {
		Optional<User> optionalUser = userRepository.findByFirstNameAndLastName(firstName, lastName);
		if (optionalUser.isEmpty()) {
			throw new EntityNotFoundException("User " + firstName + " " + lastName + " not found.");
		} else {
			return optionalUser.get();
		}
	}

	private Boolean doesUserExists(String firstName, String lastName) {
		Optional<User> optionalUser = userRepository.findByFirstNameAndLastName(firstName, lastName);
		if (optionalUser.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

}
