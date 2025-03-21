package ba.unsa.etf.laon_reservation_service.loan_reservation_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ba.unsa.etf.laon_reservation_service.loan_reservation_service.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

   
}