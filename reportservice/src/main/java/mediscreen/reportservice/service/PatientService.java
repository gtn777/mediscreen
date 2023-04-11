package mediscreen.reportservice.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mediscreen.reportservice.domain.PatientDto;

@Service
public class PatientService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ModelMapper modelMapper;

	static final String URL_GET_PATIENT_DTO_BY_ID = "http://localhost:8081/patient/";

	public PatientDto getPatientDtoByPatId(Integer patId) {

		ResponseEntity<PatientDto> result = restTemplate.getForEntity(URL_GET_PATIENT_DTO_BY_ID + patId, PatientDto.class);

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);

		PatientDto dto = modelMapper.map(result.getBody(), PatientDto.class);
		return dto;

	}

}
