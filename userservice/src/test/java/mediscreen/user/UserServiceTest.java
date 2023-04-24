package mediscreen.user;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.persistence.EntityExistsException;
import mediscreen.user.dto.NewUserDto;
import mediscreen.user.dto.UserDto;
import mediscreen.user.entity.User;
import mediscreen.user.exception.UnknownUserException;
import mediscreen.user.repository.UserRepository;
import mediscreen.user.service.UserService;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

	@InjectMocks
	UserService userService;

	@Mock
	UserRepository userRepository;

	private User user;
	private UserDto dto;
	private NewUserDto newUserDto;

	@BeforeEach
	public void beforeEachInit() {
		dto = new UserDto();
		dto.setFamily("TestFamilyName");
		dto.setGiven("TestGivenName");
		dto.setSex("M");
		dto.setPatId(15);
		newUserDto = new NewUserDto();
		newUserDto.setFamily(dto.getFamily());
		newUserDto.setGiven(dto.getGiven());
		newUserDto.setSex("M");
		user = new User(dto);
	}

	@AfterEach
	public void afterEach() {
		verifyNoMoreInteractions(userRepository);
	}

	@Test
	public void addUser_shouldReturnUserDto() {
		Optional<User> optional = Optional.empty();
		when(userRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(optional);
		when(userRepository.save(any())).thenReturn(user);
		assertTrue(userService.addUser(newUserDto).getFamily() == dto.getFamily());
		verify(userRepository, times(1)).findByFirstNameAndLastName(dto.getGiven(), dto.getFamily());
		verify(userRepository, times(1)).save(user);
	}

	@Test
	public void getUserDtoByLastName_shouldReturnUserDto() {
		List<User> users = new ArrayList<>(1);
		users.add(user);
		when(userRepository.findAllByLastName(anyString())).thenReturn(users);
		assertTrue(userService.getUserDtoByLastName(dto.getFamily()).getFamily() == "TestFamilyName");
		verify(userRepository, times(1)).findAllByLastName(dto.getFamily());
	}

	@Test
	public void getUserDtoByUserId_shouldReturnUserDto() {
		Optional<User> optional = Optional.of(new User(dto));
		when(userRepository.findById(anyInt())).thenReturn(optional);
		assertTrue(userService.getUserDtoByUserId(dto.getPatId()).getFamily() == "TestFamilyName");
		verify(userRepository, times(1)).findById(15);
	}

	@Test
	public void updateUser_shouldReturnUserDto() {
		Optional<User> optional = Optional.of(new User(dto));
		when(userRepository.findById(anyInt())).thenReturn(optional);
		when(userRepository.save(any())).thenReturn(user);
		assertTrue(userService.updateUser(dto).getFamily() == "TestFamilyName");
		verify(userRepository, times(1)).findById(15);
		verify(userRepository, times(1)).save(user);
	}

	@Test
	public void deleteUser() {
		Optional<User> optional = Optional.of(new User(dto));
		when(userRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(optional);
		userService.deleteUser(dto.getGiven(), dto.getFamily());
		verify(userRepository, times(1)).findByFirstNameAndLastName("TestGivenName", "TestFamilyName");
		verify(userRepository, times(1)).delete(user);
	}

	@Test
	public void getAllUser() {
		List<User> allUsersIterable = new ArrayList<User>(1);
		allUsersIterable.add(user);
		when(userRepository.findAll()).thenReturn(allUsersIterable);
		assertTrue(userService.getAllUser().get(0).getFamily() == "TestFamilyName");
		verify(userRepository, times(1)).findAll();
	}

	@Test
	public void addUser_userAlreadyExists_shouldThrowEntityExistsException() {
		Optional<User> optional = Optional.of(new User(dto));
		when(userRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(optional);
		assertThrows(EntityExistsException.class, () -> {
			userService.addUser(newUserDto);
		});
		verify(userRepository, times(1)).findByFirstNameAndLastName(dto.getGiven(), dto.getFamily());
	}

	@Test
	public void deleteUser_userNotExisting_shouldThrowUnknownUserException() {
		Optional<User> optional = Optional.empty();
		when(userRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(optional);
		assertThrows(UnknownUserException.class, () -> {
			userService.deleteUser(dto.getGiven(), dto.getFamily());
		});
		verify(userRepository, times(1)).findByFirstNameAndLastName(dto.getGiven(), dto.getFamily());
	}

	@Test
	public void getUserDtoByLastName_userNotExisting_shouldThrowUnknownUserException() {
		List<User> list = new ArrayList<>(0);
		when(userRepository.findAllByLastName(anyString())).thenReturn(list);
		assertThrows(UnknownUserException.class, () -> {
			userService.getUserDtoByLastName(dto.getFamily());
		});
		verify(userRepository, times(1)).findAllByLastName("TestFamilyName");
	}

	@Test
	public void getUserDtoByUserId_userNotExisting_shouldThrowUnknownUserException() {
		Optional<User> optional = Optional.empty();
		when(userRepository.findById(anyInt())).thenReturn(optional);
		assertThrows(UnknownUserException.class, () -> {
			userService.getUserDtoByUserId(10);
		});
		verify(userRepository, times(1)).findById(10);
	}

}
