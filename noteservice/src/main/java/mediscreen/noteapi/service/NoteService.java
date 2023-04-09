package mediscreen.noteapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mediscreen.noteapi.dto.NoteDto;
import mediscreen.noteapi.entity.Note;
import mediscreen.noteapi.repository.NoteRepository;

@Service
public class NoteService {

	@Autowired
	NoteRepository noteRepository;

	public NoteDto add(NoteDto dto) {
		return new NoteDto(noteRepository.save(new Note(dto)));
	}

	public NoteDto getByFirstNameAndLastName(String firstName, String lastname) {
		return noteRepository.findByFirstNameAndLastName(firstName, lastname)
				.stream()
				.map(note -> new NoteDto(note))
				.toList().get(0);
	}

	public List<NoteDto> getAll() {
		return noteRepository.findAll().stream().map(note -> new NoteDto(note)).toList();
	}

	public NoteDto update(NoteDto dto) {
		return dto;
	}

	public void delete(String firstName, String lastName) {
		noteRepository.delete(getEntityByFirstNameAndLastName(firstName, lastName));
	}

	private Note getEntityByFirstNameAndLastName(String firstName, String lastName) {
		return noteRepository.findByFirstNameAndLastName(firstName, lastName).get(0);
	}

}
