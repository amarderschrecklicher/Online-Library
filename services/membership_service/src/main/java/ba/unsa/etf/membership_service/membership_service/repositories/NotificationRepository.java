package ba.unsa.etf.membership_service.membership_service.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ba.unsa.etf.membership_service.membership_service.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

   
}
