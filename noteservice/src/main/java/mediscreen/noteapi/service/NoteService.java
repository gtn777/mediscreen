package mediscreen.noteapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mediscreen.noteapi.dto.NoteDto;
import mediscreen.noteapi.entity.Note;
import mediscreen.noteapi.exception.EntityNotFoundException;
import mediscreen.noteapi.repository.NoteRepository;

@Service
public class NoteService {

	@Autowired
	NoteRepository dao;

	public NoteDto add(Integer patId, String note) {
		return new NoteDto(dao.save(new Note(patId, note)));
	}

	public List<NoteDto> getAllByPatientId(Integer patId) {
		return dao.findAllByPatId(patId).stream().map(note -> new NoteDto(note)).toList();
	}

	public NoteDto update(String id, String note) {
		Note noteToUpdate = this.getEntityById(id);
		noteToUpdate.setNote(note);
		return new NoteDto(dao.save(noteToUpdate));
	}

	public void delete(String id) {
		dao.delete(getEntityById(id));
	}

	private Note getEntityById(String id) {
		Optional<Note> optionalNote = dao.findById(id);
		if (optionalNote.isEmpty()) {
			throw new EntityNotFoundException("Note with id " + id.toString() + " does not exist.");
		} else {
			return optionalNote.get();
		}
	}
}
