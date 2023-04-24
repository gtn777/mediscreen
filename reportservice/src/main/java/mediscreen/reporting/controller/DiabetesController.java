package mediscreen.reporting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mediscreen.reporting.service.DiabetesService;

@RestController
@RequestMapping(path = "/assess")
@CrossOrigin(value = "http://localhost:3000")
public class DiabetesController {

	@Autowired
	DiabetesService service;

	@PostMapping(path = "/id")
	public ResponseEntity<String> getAssessmentByPatientId(@RequestParam Integer patId) {
		return ResponseEntity.ok(service.getAssessmentByPatId(patId));
	}

	@PostMapping(path = "/familyName")
	public ResponseEntity<String> getAssessmentByFamilyName(@RequestParam String familyName) {
		return ResponseEntity.ok(service.getAssessmentByFamilyName(familyName));
	}

}
