package mediscreen.noteapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mediscreen.noteapi.dto.NoteDto;
import mediscreen.noteapi.service.NoteService;

@RestController
@RequestMapping(path = "/note")
@CrossOrigin(value = "http://localhost:3000")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@PostMapping(path = "/add")
	public NoteDto add(@RequestBody NoteDto dto) {
		return noteService.add(dto);
	}

	@GetMapping(path = "/get")
	public NoteDto getUser(@RequestParam String firstName, @RequestParam String lastname) {
		return noteService.getByFirstNameAndLastName(firstName, lastname);
	}

	@GetMapping(path = "/all")
	public List<NoteDto> getAllUser() {
		return noteService.getAll();
	}

	@PostMapping(path = "/update")
	public NoteDto update(@RequestBody NoteDto dto) {
		return noteService.update(dto);
	}

	@PostMapping(path = "/delete")
	public void deleteUser(@RequestParam String firstName, @RequestParam String lastName) {
		noteService.delete(firstName, lastName);
	}
}