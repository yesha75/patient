package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.entity.PatientEntity;

public interface PatientRepo extends JpaRepository<PatientEntity, Integer> {
    PatientEntity findByPatientNameAndPassword(String patientName, String password);
    List<PatientEntity> findByPatientName(String patientName);
}
