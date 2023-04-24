package mediscreen.user;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import jakarta.persistence.EntityExistsException;
import mediscreen.user.controller.UserController;
import mediscreen.user.dto.NewUserDto;
import mediscreen.user.dto.UserDto;
import mediscreen.user.exception.UnknownUserException;
import mediscreen.user.service.UserService;
import utility.ObjectToMultiValueMap;

@WebMvcTest(UserController.class)
public class ControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	UserService userService;

	private UserDto dto;
	private NewUserDto newUserDto;

	@BeforeEach
	public void beforeEachInit() {
		dto = new UserDto();
		dto.setFamily("TestFamilyName");
		dto.setGiven("TestGivenName");
		dto.setSex("M");
		newUserDto = new NewUserDto();
		newUserDto.setFamily(dto.getFamily());
		newUserDto.setGiven(dto.getGiven());
	}

	@AfterEach
	public void afterEach() {
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void getUserById_shouldReturnOkAndUserDto() throws Exception {
		when(userService.getUserDtoByUserId(4)).thenReturn(dto);
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/patient/4").accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.family").exists())
				.andReturn();
		verify(userService, times(1)).getUserDtoByUserId(4);
		assertTrue(result.getResponse().getContentAsString().contains("TestFamilyName"));
	}

	@Test
	public void getUserByFamilyName_shouldReturnOkAndUserDto() throws Exception {
		when(userService.getUserDtoByLastName(dto.getFamily())).thenReturn(dto);
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.get("/patient/name/" + dto.getFamily())
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.family").exists())
				.andReturn();
		verify(userService, times(1)).getUserDtoByLastName("TestFamilyName");
		assertTrue(result.getResponse().getContentAsString().contains("TestGivenName"));
	}

	@Test
	public void addNewUser_shouldReturnOkAndUserDto() throws Exception {
		when(userService.addUser(newUserDto)).thenReturn(dto);
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.post("/patient/add")
						.accept(MediaType.APPLICATION_JSON)
						.params(ObjectToMultiValueMap.convert(newUserDto)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.family").exists())
				.andReturn();
		verify(userService, times(1)).addUser(newUserDto);
		assertTrue(result.getResponse().getContentAsString().contains("TestGivenName"));
	}

	@Test
	public void updateUser_shouldReturnOkAndUserDto() throws Exception {
		when(userService.updateUser(dto)).thenReturn(dto);
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.post("/patient/update")
						.accept(MediaType.APPLICATION_JSON)
						.params(ObjectToMultiValueMap.convert(dto)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.family").exists())
				.andReturn();
		verify(userService, times(1)).updateUser(dto);
		assertTrue(result.getResponse().getContentAsString().contains("TestGivenName"));
	}

	@Test
	public void deleteUser_shouldReturnOk() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/patient/delete")
				.accept(MediaType.APPLICATION_JSON)
				.param("given", "TestGivenName")
				.param("family", "TestFamilyName")).andDo(print()).andExpect(status().isOk());
		verify(userService, times(1)).deleteUser("TestGivenName", "TestFamilyName");
	}

	@Test
	public void unknownUserException_shouldReturn404AndMessage() throws Exception {
		when(userService.getAllUser()).thenThrow(new UnknownUserException("message"));
		MvcResult result = mvc.perform(get("/patient/all")).andDo(print()).andExpect(status().isNotFound()).andReturn();
		verify(userService, times(1)).getAllUser();
		assertTrue(result.getResponse().getContentAsString().contains("message"));
	}

	@Test
	public void entityExistsException_shouldReturn409AndMessage() throws Exception {
		when(userService.getAllUser()).thenThrow(new EntityExistsException("message"));
		MvcResult result = mvc.perform(get("/patient/all")).andDo(print()).andExpect(status().isConflict()).andReturn();
		verify(userService, times(1)).getAllUser();
		assertTrue(result.getResponse().getContentAsString().contains("message"));
	}

	@Test
	public void sqlException_shouldReturn400AndMessage() throws Exception {
		Mockito.doAnswer(invocation -> {
			throw new SQLException("message");
		}).when(userService).getAllUser();
		MvcResult result = mvc.perform(get("/patient/all"))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
		verify(userService, times(1)).getAllUser();
		assertTrue(result.getResponse().getContentAsString().contains("message"));
	}
}
