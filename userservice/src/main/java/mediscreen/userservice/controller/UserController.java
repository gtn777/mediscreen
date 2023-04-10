package mediscreen.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mediscreen.userservice.dto.UserDto;
import mediscreen.userservice.service.UserService;

@RestController
@RequestMapping(path = "/patient")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(path = "/add")
	public UserDto addNewUser(UserDto dto) {
		return userService.addUser(dto);
	}

	@GetMapping(path = "/get")
	public UserDto getUser(@RequestParam String given, @RequestParam String family) {
		return userService.getUserDtoByName(given, family);
	}

	@GetMapping(path = "/all")
	public List<UserDto> getAllUser() {
		return userService.getAllUser();
	}

	@PostMapping(path = "/update")
	public void updateUser(UserDto dto) {
		userService.updateUser(dto);
	}

	@PostMapping(path = "/delete")
	public void deleteUser(@RequestParam String given, @RequestParam String family) {
		userService.deleteUser(given, family);
	}
}
