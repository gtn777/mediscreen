package mediscreen.reportservice;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/assess")
@CrossOrigin(value = "http://localhost:3000")
public class DiabetesRiskAssessmentController {

	@PostMapping(path = "/id")
	public Object getByPatientId(@RequestParam Integer patId) {
		return "diabetes";
	}

	@PostMapping(path = "/familyName")
	public Object getByPatientFamilyName(@RequestParam String familyName) {
		return "diabetes";
	}
}
