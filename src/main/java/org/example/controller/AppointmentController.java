package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.AppointmentDTO;
import org.example.dto.PatientDTO;
import org.example.dto.UserDTO;
import org.example.model.Appointment;
import org.example.model.Patient;
import org.example.model.User;
import org.example.service.AppointmentService;
import org.example.service.PatientService;
import org.example.service.UserService;
import org.example.service.NotificationService;
import org.example.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.getAllAppointments().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        return appointment.map(a -> ResponseEntity.ok(toDTO(a))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AppointmentDTO createAppointment(@Valid @RequestBody Appointment appointment) {
        // Fetch full Patient and User objects from DB
        if (appointment.getPatient() != null && appointment.getPatient().getId() != null) {
            patientService.getPatientById(appointment.getPatient().getId()).ifPresent(appointment::setPatient);
        }
        if (appointment.getDoctor() != null && appointment.getDoctor().getId() != null) {
            userService.getUserById(appointment.getDoctor().getId()).ifPresent(appointment::setDoctor);
        }
        Appointment savedAppointment = appointmentService.saveAppointment(appointment);
        // Notify patient (only if patient is also a user)
        User patientUser = null;
        if (savedAppointment.getPatient() != null && savedAppointment.getPatient().getContactNumber() != null) {
            String patientUsername = savedAppointment.getPatient().getContactNumber();
            patientUser = userService.getAllUsers().stream()
                .filter(u -> u.getUsername() != null && u.getUsername().equals(patientUsername))
                .findFirst().orElse(null);
        }
        if (patientUser != null) {
            Notification patientNotification = new Notification();
            patientNotification.setMessage("Your appointment is booked for " + savedAppointment.getAppointmentDateTime());
            patientNotification.setRead(false);
            patientNotification.setSentAt(LocalDateTime.now());
            patientNotification.setRecipientUser(patientUser);
            patientNotification.setAppointment(savedAppointment);
            notificationService.saveNotification(patientNotification);
        }
        // Notify doctor
        Notification doctorNotification = new Notification();
        doctorNotification.setMessage("تم حجز موعد لك  في ميعاد " + savedAppointment.getAppointmentDateTime());
        doctorNotification.setRead(false);
        doctorNotification.setSentAt(LocalDateTime.now());
        doctorNotification.setRecipientUser(savedAppointment.getDoctor());
        doctorNotification.setAppointment(savedAppointment);
        notificationService.saveNotification(doctorNotification);
        return toDTO(savedAppointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Long id, @Valid @RequestBody Appointment appointment) {
        if (!appointmentService.getAppointmentById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        appointment.setId(id);
        return ResponseEntity.ok(toDTO(appointmentService.saveAppointment(appointment)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        if (!appointmentService.getAppointmentById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    private AppointmentDTO toDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setAppointmentDateTime(appointment.getAppointmentDateTime());
        dto.setNotes(appointment.getNotes());
        dto.setStatus(appointment.getStatus().name());
        dto.setPatient(toPatientDTO(appointment.getPatient()));
        dto.setDoctor(toUserDTO(appointment.getDoctor()));
        return dto;
    }

    private PatientDTO toPatientDTO(Patient patient) {
        if (patient == null) return null;
        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setFirstName(patient.getFirstName());
        dto.setLastName(patient.getLastName());
        dto.setDateOfBirth(patient.getDateOfBirth() != null ? patient.getDateOfBirth().toString() : null);
        dto.setGender(patient.getGender());
        dto.setContactNumber(patient.getContactNumber());
        dto.setAddress(patient.getAddress());
        dto.setMedicalHistory(patient.getMedicalHistory());
        return dto;
    }

    private UserDTO toUserDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getFullName());
        // Roles mapping omitted for brevity
        return dto;
    }
}
