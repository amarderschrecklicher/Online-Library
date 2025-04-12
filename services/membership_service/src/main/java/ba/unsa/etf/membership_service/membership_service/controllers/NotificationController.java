package ba.unsa.etf.membership_service.membership_service.controllers;

import ba.unsa.etf.membership_service.membership_service.models.Notification;
import ba.unsa.etf.membership_service.membership_service.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@CrossOrigin
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/member/{memberId}")
    public List<Notification> getNotificationsForMember(@PathVariable Long memberId) {
        return notificationRepository.findByMemberId(memberId);
    }

    @PostMapping
    public ResponseEntity<?> sendNotification(@RequestBody Notification notification) {
        notification.setSentAt(LocalDateTime.now());
        return ResponseEntity.ok(notificationRepository.save(notification));
    }
}
