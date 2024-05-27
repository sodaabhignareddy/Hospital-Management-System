package com.codegnan.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codegnan.entity.Patients;
import com.codegnan.exception.InvalidPatientIdException;
import com.codegnan.service.PatientService;

@RestController
@RequestMapping("/patient")
@CrossOrigin(origins = "http://localhost:3000/")
public class PatientController {
	PatientService patientService;

	public PatientController(PatientService patientSerice) {
		super();
		this.patientService = patientSerice;
	}

	public PatientController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public void setPatientService(PatientService patientService) {
		this.patientService = patientService;
	}

	// Add a new patient
	@PostMapping
	public ResponseEntity<Patients> savePatient(@RequestBody Patients patient) {
		Patients savedPatient = patientService.savePatient(patient);
		ResponseEntity<Patients> responseEntity = new ResponseEntity<Patients>(savedPatient, HttpStatus.ACCEPTED);
		return responseEntity;
	}

	// Retrieve all patients
	@GetMapping
	public ResponseEntity<List<Patients>> getAllPatients() {
		List<Patients> patients = patientService.findAllPatients();
		return new ResponseEntity<>(patients, HttpStatus.OK);
	}

	// Retrieve patient by ID
	@GetMapping("/{id}")
	public ResponseEntity<Patients> getPatientById(@PathVariable int id) throws InvalidPatientIdException {
		Patients patient = patientService.findPatientById(id);
		return new ResponseEntity<>(patient, HttpStatus.OK);
	}

	// Edit a patient's details
	@PutMapping("/{id}")
	public ResponseEntity<Patients> editPatient(@PathVariable("id") int id, @RequestBody Patients patient)
			throws InvalidPatientIdException {
		if (id != patient.getId()) {
			throw new InvalidPatientIdException("No patient exists with the id: " + id);
		}
		Patients editedPatient = patientService.editPatient(patient);
		return new ResponseEntity<>(editedPatient, HttpStatus.ACCEPTED);
	}

	// Delete a patient by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Patients> deletePatient(@PathVariable int id) throws InvalidPatientIdException {
		Patients patient = patientService.deletePatient(id);
		return new ResponseEntity<>(patient, HttpStatus.ACCEPTED);
	}
}
