package org.example.controller;

import jakarta.validation.Valid;
import org.example.model.Notification;
import org.example.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification = notificationService.getNotificationById(id);
        return notification.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Notification createNotification(@Valid @RequestBody Notification notification) {
        return notificationService.saveNotification(notification);
    }

    // Update a notification
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @Valid @RequestBody Notification notification) {
        Optional<Notification> existing = notificationService.getNotificationById(id);
        if (!existing.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Notification toUpdate = existing.get();
        toUpdate.setMessage(notification.getMessage());
        toUpdate.setRead(notification.isRead());
        toUpdate.setSentAt(notification.getSentAt());
        toUpdate.setRecipientUser(notification.getRecipientUser());
        toUpdate.setAppointment(notification.getAppointment());
        return ResponseEntity.ok(notificationService.saveNotification(toUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        if (!notificationService.getNotificationById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    // Mark a notification as read
    @PatchMapping("/{id}/read")
    public ResponseEntity<Notification> markNotificationAsRead(@PathVariable("id") Long id) {
        Notification updated = notificationService.markAsRead(id);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }
}
