package mediscreen.note;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import mediscreen.note.controller.NoteController;
import mediscreen.note.dto.NoteDto;
import mediscreen.note.dto.PatientAllNoteDto;
import mediscreen.note.entity.Note;
import mediscreen.note.exception.EntityNotFoundException;
import mediscreen.note.service.NoteService;

@WebMvcTest(NoteController.class)
public class NoteControllerTest {
	@Autowired
	MockMvc mvc;

	@MockBean
	NoteService noteService;

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
		verifyNoMoreInteractions(noteService);
	}

	@Test
	public void getAllByPatIdUser_shouldReturnOkAndAllNoteDto() throws Exception {
		when(noteService.getAllByPatientId(15)).thenReturn(allNoteDto);
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/patHistory/all").param("patId", "15"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.noteDtos").exists())
				.andReturn();
		verify(noteService, times(1)).getAllByPatientId(15);
		assertTrue(result.getResponse().getContentAsString().contains("contenu"));
	}

	@Test
	public void add_shouldReturnOkAndNoteDto() throws Exception {
		when(noteService.add(anyInt(), anyString())).thenReturn(dto);
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.post("/patHistory/add").param("patId", "15").param("e", "contenu"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.note").exists())
				.andReturn();
		verify(noteService, times(1)).add(15, "contenu");
		assertTrue(result.getResponse().getContentAsString().contains("contenu"));
	}

	@Test
	public void update_shouldReturnOkAndNoteDto() throws Exception {
		when(noteService.update(anyString(), anyString())).thenReturn(dto);
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.post("/patHistory/update").param("id", "idtest").param("note", "contenu"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.note").exists())
				.andReturn();
		verify(noteService, times(1)).update("idtest", "contenu");
		assertTrue(result.getResponse().getContentAsString().contains("contenu"));
	}

	@Test
	public void delete_shouldReturnOk() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/patHistory/delete").param("id", "idtest"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		verify(noteService, times(1)).delete("idtest");
		assertTrue(result.getResponse().getContentLength() == 0);
	}

	@Test
	public void entityNotFoundException_shouldReturn400AndMessage() throws Exception {
		Mockito.doAnswer(invocation -> {
			throw new EntityNotFoundException("message");
		}).when(noteService).delete(anyString());
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/patHistory/delete").param("id", "idtest"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andReturn();
		verify(noteService, times(1)).delete("idtest");
		assertTrue(result.getResponse().getContentAsString().contains("message"));
	}

	@Test
	public void sqlException_shouldReturn400AndMessage() throws Exception {
		Mockito.doAnswer(invocation -> {
			throw new SQLException("message");
		}).when(noteService).delete(anyString());
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/patHistory/delete").param("id", "idtest"))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
		verify(noteService, times(1)).delete("idtest");
		assertTrue(result.getResponse().getContentAsString().contains("message"));
	}

}
