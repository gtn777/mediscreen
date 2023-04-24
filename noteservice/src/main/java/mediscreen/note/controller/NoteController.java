package mediscreen.note.controller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mediscreen.note.dto.NoteDto;
import mediscreen.note.dto.PatientAllNoteDto;
import mediscreen.note.exception.EntityNotFoundException;
import mediscreen.note.service.NoteService;

@RestController
@RequestMapping(path = "/patHistory")
@CrossOrigin(value = "http://localhost:3000")
public class NoteController {

	private static Logger logger = LoggerFactory.getLogger(NoteController.class);

	@Autowired
	private NoteService noteService;

	@PostMapping(path = "/add")
	public ResponseEntity<NoteDto> add(@RequestParam Integer patId, @RequestParam String e) {
		return ResponseEntity.ok(noteService.add(patId, e));
	}

	@GetMapping(path = "/all")
	public ResponseEntity<PatientAllNoteDto> getAllByPatIdUser(@RequestParam Integer patId) {
		return ResponseEntity.ok(noteService.getAllByPatientId(patId));
	}

	@PostMapping(path = "/update")
	public ResponseEntity<NoteDto> update(@RequestParam String id, @RequestParam String note) {
		return ResponseEntity.ok(noteService.update(id, note));
	}

	@PostMapping(path = "/delete")
	public ResponseEntity<Void> delete(@RequestParam String id) {
		noteService.delete(id);
		return ResponseEntity.ok(null);
	}

	@ExceptionHandler(value = { SQLException.class })
	public ResponseEntity<String> handleSQLException(SQLException e) {
		logger.error(e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() + e.getCause());
	}

	@ExceptionHandler(value = { EntityNotFoundException.class })
	public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
		logger.error(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() + e.getCause());
	}

}