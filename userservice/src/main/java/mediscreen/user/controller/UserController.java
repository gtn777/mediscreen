package mediscreen.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mediscreen.user.dto.NewUserDto;
import mediscreen.user.dto.UserDto;
import mediscreen.user.service.UserService;

@RestController
@RequestMapping(path = "/patient")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(path = "/add")
	public ResponseEntity<UserDto> addNewUser(NewUserDto dto) {
		return ResponseEntity.ok(userService.addUser(dto));
	}

	@GetMapping(path = "/{userId}")
	public ResponseEntity<UserDto> getUserByUserId(@PathVariable Integer userId) {
		return ResponseEntity.ok(userService.getUserDtoByUserId(userId));
	}

	@GetMapping(path = "/name/{family}")
	public ResponseEntity<UserDto> getUserByGivenAndFamilyName(@RequestParam(required = false) String given,
			@PathVariable String family) {
			System.out.println(given);
		if (given.length() < 1) {
			return ResponseEntity.ok(userService.getUserDtoByLastName(family));
		} else {
			return ResponseEntity.ok(userService.getUserDtoByFirstNameAndLastName(given, family));
		}
	}

	@PostMapping(path = "/update")
	public ResponseEntity<UserDto> updateUser(UserDto dto) {
		return ResponseEntity.ok(userService.updateUser(dto));
	}

	@PostMapping(path = "/delete")
	public void deleteUser(@RequestParam String given, @RequestParam String family) {
		userService.deleteUser(given, family);
	}

	@GetMapping(path = "/all")
	public List<UserDto> getAllUser() {
		return userService.getAllUser();
	}

}
