package mediscreen.note.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mediscreen.note.dto.NoteDto;
import mediscreen.note.dto.PatientAllNoteDto;
import mediscreen.note.entity.Note;
import mediscreen.note.exception.EntityNotFoundException;
import mediscreen.note.repository.NoteRepository;

@Service
public class NoteService {

	@Autowired
	NoteRepository noteRepository;

	public NoteDto add(Integer patId, String note) {
		return new NoteDto(noteRepository.save(new Note(patId, note)));
	}

	public PatientAllNoteDto getAllByPatientId(Integer patId) {
		PatientAllNoteDto dto = new PatientAllNoteDto();
		dto.setNoteDtos(noteRepository.findAllByPatId(patId).stream().map(note -> new NoteDto(note)).toList());
		return dto;
	}

	public NoteDto update(String id, String note) {
		Note noteToUpdate = this.getEntityById(id);
		noteToUpdate.setNote(note);
		return new NoteDto(noteRepository.save(noteToUpdate));
	}

	public void delete(String id) {
		noteRepository.delete(getEntityById(id));
	}

	private Note getEntityById(String id) {
		Optional<Note> optionalNote = noteRepository.findById(id);
		if (optionalNote.isEmpty()) {
			throw new EntityNotFoundException("Note with id " + id.toString() + " does not exist.");
		} else {
			return optionalNote.get();
		}
	}
}
