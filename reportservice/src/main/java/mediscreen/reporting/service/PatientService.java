package mediscreen.reporting.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mediscreen.reporting.domain.PatientDto;

@Service
public class PatientService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ModelMapper modelMapper;

	static final String URL_GET_PATIENT_DTO_BY_ID = "http://localhost:8081/patient/";
	static final String URL_GET_PATIENT_DTO_BY_FAMILY_NAME = "http://localhost:8081/patient/name/";

	public PatientDto getPatientDtoByPatId(Integer patId) {
		return modelMapper.map(restTemplate.getForEntity(URL_GET_PATIENT_DTO_BY_ID + patId, PatientDto.class).getBody(),
				PatientDto.class);
	}

	public PatientDto getPatientDtoByFamilyName(String family) {
		return modelMapper.map(
				restTemplate.getForEntity(URL_GET_PATIENT_DTO_BY_FAMILY_NAME + family, PatientDto.class).getBody(),
				PatientDto.class);
	}
}
