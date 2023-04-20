package mediscreen.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityExistsException;
import mediscreen.user.dto.NewUserDto;
import mediscreen.user.dto.UserDto;
import mediscreen.user.entity.User;
import mediscreen.user.exception.MultipleUserException;
import mediscreen.user.exception.UnknownUserException;
import mediscreen.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public UserDto addUser(NewUserDto dto) {
		if (doesUserExists(dto.getGiven(), dto.getFamily())) {
			throw new EntityExistsException("Patient \"" + dto.getGiven() + " " + dto.getFamily() + "\" already exists in database.");
		} else {
			return new UserDto(userRepository.save(new User(dto)));
		}
	}

	public UserDto getUserDtoByFirstNameAndLastName(String firstName, String lastName) {
		return new UserDto(this.getUserEntityByFirstNameAndLastName(firstName, lastName));
	}

	public UserDto getUserDtoByLastName(String lastName) {
		return new UserDto(this.getUserEntityByLastName(lastName));
	}

	public UserDto getUserDtoByUserId(Integer userId) {
		return new UserDto(this.getUserEntityByUserId(userId));
	}

	public UserDto updateUser(UserDto dto) {
		System.out.println(dto.toString());
		System.out.println(dto.getAddress());
		User userToUpdate = this.getUserEntityByUserId(dto.getPatId());
		User userUpDated = new User(dto);
		userUpDated.setId(userToUpdate.getId());
		return new UserDto(userRepository.save(userUpDated));
	}

	public void deleteUser(String firstName, String lastName) {
		User userToDelete = this.getUserEntityByFirstNameAndLastName(firstName, lastName);
		userRepository.delete(userToDelete);
	}

	public List<UserDto> getAllUser() {
		Iterable<User> allUsersIterable = userRepository.findAll();
		return StreamSupport.stream(allUsersIterable.spliterator(), false).map(u -> new UserDto(u)).toList();
	}

	private User getUserEntityByFirstNameAndLastName(String firstName, String lastName) {
		Optional<User> optionalUser = userRepository.findByFirstNameAndLastName(firstName, lastName);
		if (optionalUser.isEmpty()) {
			throw new UnknownUserException("Patient " + firstName + " " + lastName + " not found.");
		} else {
			return optionalUser.get();
		}
	}

	private User getUserEntityByLastName(String lastName) {
		List<User> userList = userRepository.findAllByLastName(lastName);
		if (userList.isEmpty()) {
			throw new UnknownUserException("Patient with last name \"" + lastName + "\" not found.");
		} else if (userList.size() > 1) {
			throw new MultipleUserException("There are several patient with last name \"" + lastName
					+ "\", please specify your search by entering a first name.");
		} else {
			return userList.get(0);
		}
	}

	private User getUserEntityByUserId(Integer userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isEmpty()) {
			throw new UnknownUserException("Patient with id " + userId + " not found.");
		} else {
			return optionalUser.get();
		}
	}

	private Boolean doesUserExists(String firstName, String familyName) {
		Optional<User> optionalUser = userRepository.findByFirstNameAndLastName(firstName, familyName);
		if (optionalUser.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

}
