package mediscreen.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mediscreen.note.dto.NoteDto;
import mediscreen.note.dto.PatientAllNoteDto;
import mediscreen.note.service.NoteService;

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
	public ResponseEntity<PatientAllNoteDto> getUser(@RequestParam Integer patId) {
		return ResponseEntity.ok(noteService.getAllByPatientId(patId));
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