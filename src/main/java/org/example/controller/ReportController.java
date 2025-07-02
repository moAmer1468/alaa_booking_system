package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.ReportDTO;
import org.example.model.Report;
import org.example.model.Patient;
import org.example.model.Appointment;
import org.example.repository.PatientRepository;
import org.example.repository.AppointmentRepository;
import org.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        Optional<Report> report = reportService.getReportById(id);
        return report.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Report> createReport(@Valid @RequestBody ReportDTO reportDTO) {
        Patient patient = patientRepository.findById(reportDTO.getPatientId()).orElse(null);
        Appointment appointment = appointmentRepository.findById(reportDTO.getAppointmentId()).orElse(null);
        if (patient == null || appointment == null) {
            return ResponseEntity.badRequest().build();
        }
        Report report = new Report();
        report.setReportDate(LocalDate.parse(reportDTO.getReportDate()));
        report.setGeneratedBy(reportDTO.getGeneratedBy());
        report.setFilePath(reportDTO.getFilePath());
        report.setType(reportDTO.getType());
        report.setPatient(patient);
        report.setAppointment(appointment);
        return ResponseEntity.ok(reportService.saveReport(report));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @Valid @RequestBody ReportDTO reportDTO) {
        if (!reportService.getReportById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Patient patient = patientRepository.findById(reportDTO.getPatientId()).orElse(null);
        Appointment appointment = appointmentRepository.findById(reportDTO.getAppointmentId()).orElse(null);
        if (patient == null || appointment == null) {
            return ResponseEntity.badRequest().build();
        }
        Report report = new Report();
        report.setId(id);
        report.setReportDate(LocalDate.parse(reportDTO.getReportDate()));
        report.setGeneratedBy(reportDTO.getGeneratedBy());
        report.setFilePath(reportDTO.getFilePath());
        report.setType(reportDTO.getType());
        report.setPatient(patient);
        report.setAppointment(appointment);
        return ResponseEntity.ok(reportService.saveReport(report));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        if (!reportService.getReportById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}
