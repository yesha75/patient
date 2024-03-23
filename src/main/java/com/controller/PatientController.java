package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.PatientEntity;
import com.repository.PatientRepo;

@RestController
@RequestMapping("/patient")
public class PatientController 
{
	@Autowired
	PatientRepo patientrepo;
	
	@GetMapping("/new")
	public ResponseEntity<?> newproject()
	{
		System.out.println("New project");
		return ResponseEntity.ok("ok");
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addproject(@RequestBody PatientEntity patientEntity)
	{
		System.out.println(patientEntity.getPatientName());
		patientrepo.save(patientEntity);
		return ResponseEntity.ok(patientEntity);
	}
	
	@GetMapping("/listpatient")
	public ResponseEntity<?> listpatient()
	{
		List<PatientEntity> pe=patientrepo.findAll();
		return ResponseEntity.ok(pe);
	}
	
	@GetMapping("/name/{patientName}")
	public ResponseEntity<?> pateintByName(@PathVariable("patientName") String name)
	{
		patientrepo.findAll();
		return ResponseEntity.ok(patientrepo.findByPatientName(name));
	}
	
	@GetMapping("/id/{patient_id}")
	public ResponseEntity<?> pateintByID(@PathVariable("patient_id") Integer id)
	{
		patientrepo.findAll();
		return ResponseEntity.ok(patientrepo.findById(id));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginPatient(@RequestBody PatientEntity patientEntity) {
	    PatientEntity patient = patientrepo.findByPatientNameAndPassword(patientEntity.getPatientName(), patientEntity.getPassword());
	    if (patient != null) {
	        return ResponseEntity.ok(patient);
	    } else {
	        return ResponseEntity.badRequest().body("Invalid username or password");
	    }
	}
	
	 @PutMapping("/{patientId}")
	    public ResponseEntity<?> updatePatient(@RequestBody PatientEntity patientEntity, @PathVariable("patientId") Integer patientId) {
	        // Retrieve the patient from the repository
	        Optional<PatientEntity> optionalPatient = patientrepo.findById(patientId);
	        
	        if (optionalPatient.isPresent()) {
	            // If the patient exists, update its details
	            PatientEntity patient = optionalPatient.get();
	           
	            if(patientEntity.getMedicines()!=null)
	            {
	            	patient.setMedicines(patientEntity.getMedicines());
	            }
	            if(patientEntity.getDisease()!=null)
	            {
		            patient.setDisease(patientEntity.getDisease());
	            }
	            // Save the updated patient
	            patientrepo.save(patient);
	            	
	            return ResponseEntity.ok("Patient details updated successfully");
	        } else {
	            // If the patient does not exist, return a not found response
	            return ResponseEntity.notFound().build();
	        }
	    }
}
