package com.patientops.patient_service.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.patientops.patient_service.dto.PatientRequestDTO;
import com.patientops.patient_service.dto.PatientResponseDTO;
import com.patientops.patient_service.exceptions.EmailAlreadyExistsException;
import com.patientops.patient_service.exceptions.PatientNotFoundException;
import com.patientops.patient_service.mapper.PatientMapper;
import com.patientops.patient_service.model.Patient;
import com.patientops.patient_service.repository.PatientRepository;

@Service
public class PatientService {
  private PatientRepository patientRepository;

  public PatientService(
      PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  public List<PatientResponseDTO> getPatients() {
    List<Patient> patients = patientRepository.findAll();
    return patients.stream()
        .map(PatientMapper::toDTO)
        .toList();
  }

  public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
    if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
      throw new EmailAlreadyExistsException("A patient with this email already exists");
    }

    Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
    return PatientMapper.toDTO(newPatient);
  }

  public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
    Patient patient = patientRepository.findById(id)
        .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
    if (patientRepository.existsByEmail(patientRequestDTO.getEmail())
        && !patient.getEmail().equals(patientRequestDTO.getEmail())) {
      throw new EmailAlreadyExistsException("A patient with this email already exists");
    }

    patient.setName(patientRequestDTO.getName());
    patient.setAddress(patientRequestDTO.getAddress());
    patient.setEmail(patientRequestDTO.getEmail());
    patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

    Patient updatedPatient = patientRepository.save(patient);
    return PatientMapper.toDTO(updatedPatient);
  }
}
