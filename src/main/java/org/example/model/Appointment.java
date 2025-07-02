package org.example.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Patient is required")
    @ManyToOne
    @JoinColumn(name = "patient_id")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Patient patient;

    @NotNull(message = "Doctor is required")
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @OneToMany(mappedBy = "appointment")
    private java.util.Set<Report> reports;

    @OneToMany(mappedBy = "appointment")
    @JsonManagedReference
    private java.util.Set<Notification> notifications;

    @NotNull(message = "Appointment date and time is required")
    private LocalDateTime appointmentDateTime;
    @NotBlank(message = "Notes are required")
    private String notes;
    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING, COMPLETED, CANCELLED
    }

    public Long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public User getDoctor() {
        return doctor;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public String getNotes() {
        return notes;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // getters and setters

}
