package mediscreen.reportservice.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mediscreen.reportservice.domain.DiabetesRiskLevel;
import mediscreen.reportservice.domain.NoteDto;
import mediscreen.reportservice.domain.PatientDto;

@Service
public class DiabetesAssessmentService {

	@Autowired
	PatientService patientService;

	@Autowired
	HistoryService historyService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ModelMapper modelMapper;

	static final List<String> triggers = Arrays.asList("Hémoglobine A1C", "Hemoglobin A1C", "Microalbumine",
			"Microalbumin", "Taille", "Height", "Poids", "Weight", "Fume", "smoke", "Abnormal", "Cholestérol",
			"Cholesterol", "Vertige", "Dizziness", "Rechute", "relapse", "Réaction", "Reaction", "Anticorps",
			"antibodies"

	);

	public String getAssessmentByPatId(Integer patId) {
		PatientDto patientDto = patientService.getPatientDtoByPatId(patId);
		List<NoteDto> noteDtos = historyService.getPatientAllNoteDtoByPatId(patId).getNoteDtos();

		String sex = patientDto.getSex();
		Integer age = this.calculateAge(patientDto.getDob(), LocalDate.now());
		Integer triggerOccurence = this.getTriggerOccurence(noteDtos);
		DiabetesRiskLevel level = this.getDiabeteRiskLevel(age, triggerOccurence, sex);

		System.out.println("-- age: " + age);
		System.out.println("-- occurence: " + triggerOccurence.toString());
		System.out.println("-- name: " + patientDto.getFamily() + "  level: " + level.getLevelString());
//		return level.getLevelString();
		return "--name:" + patientDto.getFamily() + " -age:" + age + "sex:" + patientDto.getSex() + "-occurence:"
				+ triggerOccurence + " -level:" + level.getLevelString();
	}

	private DiabetesRiskLevel getDiabeteRiskLevel(Integer age, Integer triggerOccurence, String sex) {
		if (age < 30) {
			if (sex.equals("M")) {
				if (triggerOccurence < 3) {
					return DiabetesRiskLevel.NONE;
				} else if (triggerOccurence < 5) {
					return DiabetesRiskLevel.INDANGER;
				} else {
					return DiabetesRiskLevel.EARLYONSET;
				}
			} else {
				if (triggerOccurence < 4) {
					return DiabetesRiskLevel.NONE;
				} else if (triggerOccurence < 7) {
					return DiabetesRiskLevel.INDANGER;
				} else {
					return DiabetesRiskLevel.EARLYONSET;
				}
			}
		} else {
			if (triggerOccurence < 2) {
				return DiabetesRiskLevel.NONE;
			} else if (triggerOccurence < 6) {
				return DiabetesRiskLevel.BORDERLINE;
			} else if (triggerOccurence < 8) {
				return DiabetesRiskLevel.INDANGER;
			} else {
				return DiabetesRiskLevel.EARLYONSET;
			}
		}

	}

	private Integer getTriggerOccurence(List<NoteDto> noteDtos) {
		Integer result = 0;
		String notes = " ";
		for (NoteDto n : noteDtos) {
			notes += n.getNote() + " ";
		}
		for (String trigger : triggers) {
			if (notes.toUpperCase().contains(trigger.toUpperCase())) {
				result++;
			} else {
				continue;
			}
		}

		return result;
	}

	private int calculateAge(LocalDate birthDate, LocalDate currentDate) {
		return Period.between(birthDate, currentDate).getYears();
	}

	public Object getAssessmentByFamilyName(String familyName) {
		// TODO Auto-generated method stub
		return null;
	}

}
