package mediscreen.reportservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import mediscreen.reportservice.service.DiabetesAssessmentService;

@RestController
@RequestMapping(path = "/assess")
@CrossOrigin(value = "http://localhost:3000")
public class DiabetesAssessmentController {

	@Autowired
	DiabetesAssessmentService service;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping(path = "/id")
	public ResponseEntity<String> trygetAssessmentByPatientId(@RequestParam Integer patId) {
		return ResponseEntity.ok(service.getAssessmentByPatId(patId));
	}

	// ---------------------------------------------------------

	@PostMapping(path = "/id")
	public ResponseEntity<String> getAssessmentByPatientId(@RequestParam Integer patId) {
		return ResponseEntity.ok(service.getAssessmentByPatId(patId));
	}

	@PostMapping(path = "/familyName")
	public Object getAssessmentByPatientFamilyName(@RequestParam String familyName) {
		return service.getAssessmentByFamilyName(familyName);
	}

}
