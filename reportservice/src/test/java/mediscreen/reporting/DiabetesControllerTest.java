package mediscreen.reporting;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import mediscreen.reporting.controller.DiabetesController;
import mediscreen.reporting.service.DiabetesService;

@WebMvcTest(DiabetesController.class)
public class DiabetesControllerTest {
	@Autowired
	MockMvc mvc;

	@MockBean
	DiabetesService service;

	private String assessment = "assessment";

	@AfterEach
	public void afterEach() {
		verifyNoMoreInteractions(service);
	}

	@Test
	public void getAssessmentByPatientId_shouldReturnOkAndAssessmentString() throws Exception {
		when(service.getAssessmentByPatId(anyInt())).thenReturn(assessment);
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/assess/id").param("patId", "15"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		verify(service, times(1)).getAssessmentByPatId(15);
		assertTrue(result.getResponse().getContentAsString().contains("assessment"));
	}
	
	@Test
	public void getAssessmentByFamilyName_shouldReturnOkAndAssessmentString() throws Exception {
		when(service.getAssessmentByFamilyName(anyString())).thenReturn(assessment);
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/assess/familyName").param("familyName", "testName"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		verify(service, times(1)).getAssessmentByFamilyName("testName");
		assertTrue(result.getResponse().getContentAsString().contains("assessment"));
	}
	
}
