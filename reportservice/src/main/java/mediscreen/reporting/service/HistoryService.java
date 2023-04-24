package mediscreen.reporting.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mediscreen.reporting.domain.PatientAllNoteDto;

@Service
public class HistoryService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ModelMapper modelMapper;

	static final String URL_GET_PATIENT_ALL_NOTE_DTO_BY_ID = "http://localhost:8082/patHistory/all?patId=";

	public PatientAllNoteDto getPatientAllNoteDtoByPatId(Integer patId) {

		ResponseEntity<PatientAllNoteDto> result = restTemplate.getForEntity(URL_GET_PATIENT_ALL_NOTE_DTO_BY_ID + patId,
				PatientAllNoteDto.class);

		ModelMapper modelMapper = new ModelMapper();

		PatientAllNoteDto dto = modelMapper.map(result.getBody(), PatientAllNoteDto.class);

		return dto;

	}
}
