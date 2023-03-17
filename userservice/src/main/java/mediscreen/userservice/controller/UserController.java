package mediscreen.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mediscreen.userservice.dto.UserCreationDto;
import mediscreen.userservice.dto.UserDto;
import mediscreen.userservice.service.UserService;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(path = "/add")
	public UserDto addNewUser(@RequestBody UserCreationDto dto) {
		return userService.addUser(dto);
	}

	@GetMapping(path = "/get")
	public String getUser(@RequestParam String firstName, @RequestParam String lastname) {
		return "done";
	}

	@GetMapping(path = "/all")
	public List<UserDto> getAllUser() {
		return userService.getAllUser();
	}

	@PostMapping(path = "/update")
	public void updateUser(@RequestBody UserCreationDto dto) {
		userService.updateUser(dto);
	}

	@PostMapping(path = "/delete")
	public void deleteUser(@RequestParam String firstName, @RequestParam String lastName) {
		userService.deleteUser(firstName, lastName);
	}
}
