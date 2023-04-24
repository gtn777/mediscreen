package mediscreen.reporting.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import mediscreen.reporting.domain.DiabetesRiskLevel;
import mediscreen.reporting.domain.NoteDto;
import mediscreen.reporting.domain.PatientDto;

@Service
public class DiabetesService {

	@Autowired
	PatientService patientService;

	@Autowired
	HistoryService historyService;

	@Autowired
	ModelMapper modelMapper;

	static final List<String> triggers = Arrays.asList("Hémoglobine A1C", "Hemoglobin A1C", "Microalbumine",
			"Microalbumin", "Taille", "Height", "Poids", "Weight", "Fume", "smoke", "Abnormal", "Anormal",
			"Cholestérol", "Cholesterol", "Vertige", "Dizziness", "Rechute", "relapse", "Réaction", "Reaction",
			"Anticorps", "antibodies");

	public String getAssessmentByPatId(Integer patId) {
		PatientDto patientDto = patientService.getPatientDtoByPatId(patId);
		List<NoteDto> noteDtos = historyService.getPatientAllNoteDtoByPatId(patId).getNoteDtos();
		return this.getAssessmentReport(patientDto, noteDtos);
	};

	public String getAssessmentByFamilyName(String familyName) {
		PatientDto patientDto = patientService.getPatientDtoByFamilyName(familyName);
		List<NoteDto> noteDtos = historyService.getPatientAllNoteDtoByPatId(patientDto.getPatId()).getNoteDtos();
		return this.getAssessmentReport(patientDto, noteDtos);
	};

	private String getAssessmentReport(PatientDto patientDto, List<NoteDto> noteDtos) {
		String sex = patientDto.getSex();
		Integer age = this.calculateAge(patientDto.getDob(), LocalDate.now());
		Integer triggerOccurence = this.getTriggerOccurence(noteDtos);
		DiabetesRiskLevel level = this.getDiabeteRiskLevel(age, triggerOccurence, sex);
		System.out.println("--------------" + "Patient: " + patientDto.getGiven() + " " + patientDto.getFamily()
				+ " (age " + age + ") diabetes assessment is: " + level.getLevelString());
		return "Patient: " + patientDto.getGiven() + " " + patientDto.getFamily() + " (age " + age
				+ ") diabetes assessment is: " + level.getLevelString();
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
	};

	private Integer getTriggerOccurence(List<NoteDto> noteDtos) {
		Integer result = 0;
		for (NoteDto n : noteDtos) {
			for (String trigger : triggers) {
				result = result + StringUtils.countOccurrencesOf(n.getNote().toUpperCase(), trigger.toUpperCase());
			}
		}
		return result;
	};

	private int calculateAge(LocalDate birthDate, LocalDate currentDate) {
		return Period.between(birthDate, currentDate).getYears();
	};

}
