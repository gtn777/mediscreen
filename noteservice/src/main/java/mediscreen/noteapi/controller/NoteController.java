package mediscreen.noteapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mediscreen.noteapi.dto.NoteDto;
import mediscreen.noteapi.service.NoteService;

@RestController
@RequestMapping(path = "/patHistory")
@CrossOrigin(value = "http://localhost:3000")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@PostMapping(path = "/add")
	public NoteDto add(@RequestParam Integer patId, @RequestParam String e) {
		return noteService.add(patId, e);
	}

	@GetMapping(path = "/all")
	public List<NoteDto> getUser(@RequestParam Integer patId) {
		return noteService.getAllByPatientId(patId);
	}

	@PostMapping(path = "/update")
	public NoteDto update(@RequestParam String id, @RequestParam String note) {
		return noteService.update(id, note);
	}

	@PostMapping(path = "/delete")
	public void delete(@RequestParam String id) {
		noteService.delete(id);
	}
}