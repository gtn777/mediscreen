package mediscreen.reporting;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
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
public class DiabetesRiskLevelCalculationTest {

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
		patientDto.setFamily("TestName");
		patientDto.setGiven("Test");
		LocalDate date = LocalDate.now().minusYears(16);
		patientDto.setDob(date);
		patientDto.setSex("M");
		noteDto = new NoteDto();
		patientAllNoteDto = new PatientAllNoteDto();
	}

	@AfterEach
	public void afterEach() {
		verifyNoMoreInteractions(patientService);
		verifyNoMoreInteractions(historyService);
	}

	@Test
	public void getAssessment_menUnder30YearsOld2Triggers_shouldReturnNone() {
		noteDto.setNote("cholesterol smoke");
		patientAllNoteDto.getNoteDtos().add(noteDto);
		when(patientService.getPatientDtoByPatId(anyInt())).thenReturn(patientDto);
		when(historyService.getPatientAllNoteDtoByPatId(anyInt())).thenReturn(patientAllNoteDto);
		assertTrue(service.getAssessmentByPatId(15)
				.equalsIgnoreCase("Patient: Test TestName (age 16) diabetes assessment is: None"));
		verify(patientService, times(1)).getPatientDtoByPatId(15);
		verify(historyService, times(1)).getPatientAllNoteDtoByPatId(15);
	}

	@Test
	public void getAssessment_menUnder30YearsOld4Triggers_shouldReturnInDanger() {
		noteDto.setNote("cholesterol smoke hemoglobin A1C abnormal");
		patientAllNoteDto.getNoteDtos().add(noteDto);
		when(patientService.getPatientDtoByPatId(anyInt())).thenReturn(patientDto);
		when(historyService.getPatientAllNoteDtoByPatId(anyInt())).thenReturn(patientAllNoteDto);
		assertTrue(service.getAssessmentByPatId(15)
				.equalsIgnoreCase("Patient: Test TestName (age 16) diabetes assessment is: In danger"));
		verify(patientService, times(1)).getPatientDtoByPatId(15);
		verify(historyService, times(1)).getPatientAllNoteDtoByPatId(15);
	}

	@Test
	public void getAssessment_menUnder30YearsOld5Triggers_shouldReturnEarlyOnSet() {
		noteDto.setNote("cholesterol smoke smoke hemoglobin A1C abnormal");
		patientAllNoteDto.getNoteDtos().add(noteDto);
		when(patientService.getPatientDtoByPatId(anyInt())).thenReturn(patientDto);
		when(historyService.getPatientAllNoteDtoByPatId(anyInt())).thenReturn(patientAllNoteDto);
		assertTrue(service.getAssessmentByPatId(15)
				.equalsIgnoreCase("Patient: Test TestName (age 16) diabetes assessment is: Early onset"));
		verify(patientService, times(1)).getPatientDtoByPatId(15);
		verify(historyService, times(1)).getPatientAllNoteDtoByPatId(15);
	}

	@Test
	public void getAssessment_womenUnder30YearsOld3Triggers_shouldReturnNone() {
		patientDto.setSex("F");
		noteDto.setNote("cholesterol smoke hemoglobin A1C");
		patientAllNoteDto.getNoteDtos().add(noteDto);
		when(patientService.getPatientDtoByPatId(anyInt())).thenReturn(patientDto);
		when(historyService.getPatientAllNoteDtoByPatId(anyInt())).thenReturn(patientAllNoteDto);
		assertTrue(service.getAssessmentByPatId(15)
				.equalsIgnoreCase("Patient: Test TestName (age 16) diabetes assessment is: None"));
		verify(patientService, times(1)).getPatientDtoByPatId(15);
		verify(historyService, times(1)).getPatientAllNoteDtoByPatId(15);
	}

	@Test
	public void getAssessment_womenUnder30YearsOld6Triggers_shouldReturnInDanger() {
		patientDto.setSex("F");
		noteDto.setNote("cholesterol smoke hemoglobin A1C abnormal smoke cholesterol");
		patientAllNoteDto.getNoteDtos().add(noteDto);
		when(patientService.getPatientDtoByPatId(anyInt())).thenReturn(patientDto);
		when(historyService.getPatientAllNoteDtoByPatId(anyInt())).thenReturn(patientAllNoteDto);
		assertTrue(service.getAssessmentByPatId(15)
				.equalsIgnoreCase("Patient: Test TestName (age 16) diabetes assessment is: In danger"));
		verify(patientService, times(1)).getPatientDtoByPatId(15);
		verify(historyService, times(1)).getPatientAllNoteDtoByPatId(15);
	}

	@Test
	public void getAssessment_womenUnder30YearsOld8Triggers_shouldReturnEarlyOnSet() {
		patientDto.setSex("F");
		noteDto.setNote("cholesterol smoke abnormal cholesterol smoke hemoglobin A1C abnormal smoke");
		patientAllNoteDto.getNoteDtos().add(noteDto);
		when(patientService.getPatientDtoByPatId(anyInt())).thenReturn(patientDto);
		when(historyService.getPatientAllNoteDtoByPatId(anyInt())).thenReturn(patientAllNoteDto);
		assertTrue(service.getAssessmentByPatId(15)
				.equalsIgnoreCase("Patient: Test TestName (age 16) diabetes assessment is: Early onset"));
		verify(patientService, times(1)).getPatientDtoByPatId(15);
		verify(historyService, times(1)).getPatientAllNoteDtoByPatId(15);
	}
	
	@Test
	public void getAssessment_over30YearsOld1Triggers_shouldReturnNone() {
		LocalDate date = LocalDate.now().minusYears(40);
		patientDto.setDob(date);
		noteDto.setNote("cholesterol");
		patientAllNoteDto.getNoteDtos().add(noteDto);
		when(patientService.getPatientDtoByPatId(anyInt())).thenReturn(patientDto);
		when(historyService.getPatientAllNoteDtoByPatId(anyInt())).thenReturn(patientAllNoteDto);
		assertTrue(service.getAssessmentByPatId(15)
				.equalsIgnoreCase("Patient: Test TestName (age 40) diabetes assessment is: None"));
		verify(patientService, times(1)).getPatientDtoByPatId(15);
		verify(historyService, times(1)).getPatientAllNoteDtoByPatId(15);
	}
	@Test
	public void getAssessment_over30YearsOld5Triggers_shouldReturnBorderline() {
		LocalDate date = LocalDate.now().minusYears(40);
		patientDto.setDob(date);
		noteDto.setNote("cholesterol smoke hemoglobin A1C abnormal smoke");
		patientAllNoteDto.getNoteDtos().add(noteDto);
		when(patientService.getPatientDtoByPatId(anyInt())).thenReturn(patientDto);
		when(historyService.getPatientAllNoteDtoByPatId(anyInt())).thenReturn(patientAllNoteDto);
		assertTrue(service.getAssessmentByPatId(15)
				.equalsIgnoreCase("Patient: Test TestName (age 40) diabetes assessment is: Borderline"));
		verify(patientService, times(1)).getPatientDtoByPatId(15);
		verify(historyService, times(1)).getPatientAllNoteDtoByPatId(15);
	}
	@Test
	public void getAssessment_over30YearsOld6Triggers_shouldReturnInDanger() {
		patientDto.setSex("F");
		LocalDate date = LocalDate.now().minusYears(40);
		patientDto.setDob(date);
		noteDto.setNote("cholesterol smoke hemoglobin A1C abnormal smoke cholesterol");
		patientAllNoteDto.getNoteDtos().add(noteDto);
		when(patientService.getPatientDtoByPatId(anyInt())).thenReturn(patientDto);
		when(historyService.getPatientAllNoteDtoByPatId(anyInt())).thenReturn(patientAllNoteDto);
		assertTrue(service.getAssessmentByPatId(15)
				.equalsIgnoreCase("Patient: Test TestName (age 40) diabetes assessment is: In danger"));
		verify(patientService, times(1)).getPatientDtoByPatId(15);
		verify(historyService, times(1)).getPatientAllNoteDtoByPatId(15);
	}

	@Test
	public void getAssessment_over30YearsOld8Triggers_shouldReturnEarlyOnSet() {
		patientDto.setSex("F");
		LocalDate date = LocalDate.now().minusYears(40);
		patientDto.setDob(date);
		noteDto.setNote("cholesterol smoke abnormal cholesterol smoke hemoglobin A1C abnormal smoke");
		patientAllNoteDto.getNoteDtos().add(noteDto);
		when(patientService.getPatientDtoByPatId(anyInt())).thenReturn(patientDto);
		when(historyService.getPatientAllNoteDtoByPatId(anyInt())).thenReturn(patientAllNoteDto);
		assertTrue(service.getAssessmentByPatId(15)
				.equalsIgnoreCase("Patient: Test TestName (age 40) diabetes assessment is: Early onset"));
		verify(patientService, times(1)).getPatientDtoByPatId(15);
		verify(historyService, times(1)).getPatientAllNoteDtoByPatId(15);
	}

}
