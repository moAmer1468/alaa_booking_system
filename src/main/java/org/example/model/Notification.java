package org.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Message is required")
    private String message;
    @NotNull(message = "Read status is required")
    @Column(name = "is_read")
    private boolean read;
    @NotNull(message = "SentAt is required")
    private LocalDateTime sentAt;
    @NotNull(message = "Recipient user is required")
    @ManyToOne
    @JoinColumn(name = "recipient_user_id")
    @JsonBackReference
    private User recipientUser;
    @NotNull(message = "Appointment is required")
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    @JsonBackReference
    private Appointment appointment;
    // getters and setters


    public Appointment getAppointment() {
        return appointment;
    }

    public User getRecipientUser() {
        return recipientUser;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public boolean isRead() {
        return read;
    }

    public String getMessage() {
        return message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public void setRecipientUser(User recipientUser) {
        this.recipientUser = recipientUser;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
