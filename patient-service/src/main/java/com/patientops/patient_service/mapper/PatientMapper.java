package com.patientops.patient_service.mapper;

import java.time.LocalDate;

import com.patientops.patient_service.dto.PatientRequestDTO;
import com.patientops.patient_service.dto.PatientResponseDTO;
import com.patientops.patient_service.model.Patient;

public class PatientMapper {
  public static PatientResponseDTO toDTO(Patient patient) {
    PatientResponseDTO patientDTO = new PatientResponseDTO();
    patientDTO.setId(patient.getId().toString());
    patientDTO.setName(patient.getName().toString());
    patientDTO.setAddress(patient.getAddress().toString());
    patientDTO.setEmail(patient.getEmail());
    patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());
    return patientDTO;
  }

  public static Patient toModel(PatientRequestDTO patientRequestDTO) {
    Patient patient = new Patient();
    patient.setName(patientRequestDTO.getName());
    patient.setAddress(patientRequestDTO.getAddress());
    patient.setEmail(patientRequestDTO.getEmail());
    patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
    patient.setRegisteredDate(LocalDate.now());
    return patient;
  }
}
