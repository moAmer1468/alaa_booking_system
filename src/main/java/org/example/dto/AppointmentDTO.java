package org.example.dto;

import java.time.LocalDateTime;

public class AppointmentDTO {
    private Long id;
    private PatientDTO patient;
    private UserDTO doctor;
    private LocalDateTime appointmentDateTime;
    private String notes;
    private String status;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public PatientDTO getPatient() { return patient; }
    public void setPatient(PatientDTO patient) { this.patient = patient; }
    public UserDTO getDoctor() { return doctor; }
    public void setDoctor(UserDTO doctor) { this.doctor = doctor; }
    public LocalDateTime getAppointmentDateTime() { return appointmentDateTime; }
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) { this.appointmentDateTime = appointmentDateTime; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

