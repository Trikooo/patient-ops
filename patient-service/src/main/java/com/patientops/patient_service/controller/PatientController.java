package com.patientops.patient_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patientops.patient_service.dto.PatientRequestDTO;
import com.patientops.patient_service.dto.PatientResponseDTO;
import com.patientops.patient_service.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patients", description = "Operations related to patient management")
public class PatientController {
  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @Operation(summary = "Retrieve all patients", description = "Fetches a list of all registered patients in the system.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successfully retrieved patient list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientResponseDTO.class)))
  })
  @GetMapping
  public ResponseEntity<List<PatientResponseDTO>> getPatients() {
    List<PatientResponseDTO> patients = patientService.getPatients();
    return ResponseEntity.ok().body(patients);
  }

  @Operation(summary = "Create a new patient record", description = "Registers a new patient in the system based on the provided details.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Patient created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
  })
  @PostMapping
  public ResponseEntity<PatientResponseDTO> createPatient(
      @Valid @RequestBody PatientRequestDTO patientRequestDTO) {
    PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
    return ResponseEntity.ok().body(patientResponseDTO);
  }

  @Operation(summary = "Update an existing patient record", description = "Updates patient information based on the provided ID and request body.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Patient updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content)
  })
  @PutMapping("/{id}")
  public ResponseEntity<PatientResponseDTO> updatePatient(
      @Parameter(description = "Unique identifier of the patient to update", required = true) @PathVariable UUID id,
      @Valid @RequestBody PatientRequestDTO patientRequestDTO) {
    PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);
    return ResponseEntity.ok().body(patientResponseDTO);
  }

  @Operation(summary = "Delete a patient record", description = "Removes a patient from the system using their unique identifier.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Patient deleted successfully", content = @Content),
      @ApiResponse(responseCode = "404", description = "Patient not found", content = @Content)
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePatient(
      @Parameter(description = "Unique identifier of the patient to delete", required = true) @PathVariable UUID id) {
    patientService.deletePatient(id);
    return ResponseEntity.noContent().build();
  }
}
