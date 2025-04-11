package ba.unsa.etf.membership_service.membership_service;

import ba.unsa.etf.membership_service.membership_service.controllers.NotificationController;
import ba.unsa.etf.membership_service.membership_service.models.Notification;
import ba.unsa.etf.membership_service.membership_service.repositories.NotificationRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationControllerTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void shouldReturnNotificationsForMember() {
        Notification notification = new Notification(1L, 10L, "Message", LocalDateTime.now(), "INFO");

        when(notificationRepository.findByMemberId(10L)).thenReturn(List.of(notification));

        List<Notification> result = notificationController.getNotificationsForMember(10L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMessage()).isEqualTo("Message");
    }

    @Test
    void shouldSendNotification() {
        Notification notification = new Notification(null, 10L, "New message", null, "INFO");

        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        var response = notificationController.sendNotification(notification);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        verify(notificationRepository).save(any(Notification.class));
    }
}
