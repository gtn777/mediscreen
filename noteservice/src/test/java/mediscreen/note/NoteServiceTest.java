package mediscreen.note;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import mediscreen.note.dto.NoteDto;
import mediscreen.note.dto.PatientAllNoteDto;
import mediscreen.note.entity.Note;
import mediscreen.note.exception.EntityNotFoundException;
import mediscreen.note.repository.NoteRepository;
import mediscreen.note.service.NoteService;

@ExtendWith(SpringExtension.class)
public class NoteServiceTest {
	@InjectMocks
	NoteService noteService;

	@Mock
	NoteRepository noteRepository;

	private Note note;
	private NoteDto dto;
	private PatientAllNoteDto allNoteDto;
	List<Note> allNote;

	@BeforeEach
	public void beforeEachInit() {
		dto = new NoteDto();
		dto.setId("idtest");
		dto.setNote("contenu");
		dto.setPatId(15);
		allNoteDto = new PatientAllNoteDto();
		allNoteDto.getNoteDtos().add(dto);
		note = new Note(15, "contenu");
		note.setId("idtest");
		allNote = new ArrayList<>(1);
		allNote.add(note);
	}

	@AfterEach
	public void afterEach() {
		verifyNoMoreInteractions(noteRepository);
	}

	@Test
	public void addNote_shouldReturnRightNoteDto() {
		when(noteRepository.save(any(Note.class))).thenReturn(note);
		assertThat(noteService.add(15, "contenu").getNote() == "contenu");
		verify(noteRepository, times(1)).save(any(Note.class));
	}

	@Test
	public void getAllByPatientId_shouldReturnListOfNoteDto() {
		when(noteRepository.findAllByPatId(anyInt())).thenReturn(allNote);
		assertThat(noteService.getAllByPatientId(15).getNoteDtos().get(0).getNote() == "contenu");
		verify(noteRepository, times(1)).findAllByPatId(15);
	}

	@Test
	public void update_shouldReturnRightNoteDto() {
		when(noteRepository.findById(anyString())).thenReturn(Optional.of(note));
		when(noteRepository.save(any(Note.class))).thenReturn(note);
		assertThat(noteService.update(dto.getId(), dto.getNote()).getNote() == "contenu");
		verify(noteRepository, times(1)).findById("idtest");
		verify(noteRepository, times(1)).save(note);
	}

	@Test
	public void update_unknownUser_shouldThrowEntityNotFoundException() {
		when(noteRepository.findById(anyString())).thenReturn(Optional.empty());
		assertThrows(EntityNotFoundException.class, () -> noteService.update(dto.getId(), dto.getNote()));
		verify(noteRepository, times(1)).findById("idtest");
	}

	@Test
	public void delete_shouldBeOk() {	
		when(noteRepository.findById(anyString())).thenReturn(Optional.of(note));
		noteService.delete("idtest");
		verify(noteRepository, times(1)).findById("idtest");
		verify(noteRepository, times(1)).delete(note);
	}

}
