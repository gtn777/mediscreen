package mediscreen.reporting;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import mediscreen.reporting.domain.NoteDto;
import mediscreen.reporting.domain.PatientAllNoteDto;
import mediscreen.reporting.domain.PatientDto;
import mediscreen.reporting.service.DiabetesService;
import mediscreen.reporting.service.HistoryService;
import mediscreen.reporting.service.PatientService;

@ExtendWith(SpringExtension.class)
public class DiabetesServiceTest {

	@InjectMocks
	DiabetesService service;

	@Mock
	PatientService patientService;

	@Mock
	HistoryService historyService;

	private PatientDto patientDto;
	private NoteDto noteDto;
	private PatientAllNoteDto patientAllNoteDto;

	@BeforeEach
	public void beforeEachInit() {
		patientDto = new PatientDto();
		patientDto.setPatId(15);
		patientDto.setFamily("Test");
		patientDto.setGiven("Judith");
		LocalDate date = LocalDate.now().minusYears(16);
		patientDto.setDob(date);
		patientDto.setSex("F");
		noteDto = new NoteDto();
		noteDto.setNote("cholesterol, smoke, smoke, hemoglobin A1C, abnormal, anormal, fume, reaction");
		patientAllNoteDto = new PatientAllNoteDto();
		patientAllNoteDto.getNoteDtos().add(noteDto);
	}

	@AfterEach
	public void afterEach() {
		verifyNoMoreInteractions(patientService);
		verifyNoMoreInteractions(historyService);
	}

	@Test
	public void getAssessmentByPatId_shouldReturnAssessmentString() {
		when(patientService.getPatientDtoByPatId(anyInt())).thenReturn(patientDto);
		when(historyService.getPatientAllNoteDtoByPatId(anyInt())).thenReturn(patientAllNoteDto);

		assertTrue(service.getAssessmentByPatId(15)
				.equalsIgnoreCase("Patient: Judith Test (age 16) diabetes assessment is: Early onset"));
		verify(patientService, times(1)).getPatientDtoByPatId(15);
		verify(historyService, times(1)).getPatientAllNoteDtoByPatId(15);
	}

	@Test
	public void getAssessmentByFamilyName_shouldReturnAssessmentString() {
		patientDto.setSex("M");
		patientDto.setGiven("Franck");
		noteDto.setNote("cholesterol, smoke, smoke, hemoglobin A1C, abnormal");
		patientAllNoteDto = new PatientAllNoteDto();
		patientAllNoteDto.getNoteDtos().add(noteDto);
		
		when(patientService.getPatientDtoByFamilyName(anyString())).thenReturn(patientDto);
		when(historyService.getPatientAllNoteDtoByPatId(anyInt())).thenReturn(patientAllNoteDto);
		
		assertTrue(service.getAssessmentByFamilyName("Test")
				.equalsIgnoreCase("Patient: Franck Test (age 16) diabetes assessment is: Early onset"));
		verify(patientService, times(1)).getPatientDtoByFamilyName("Test");
		verify(historyService, times(1)).getPatientAllNoteDtoByPatId(15);
	}

}
