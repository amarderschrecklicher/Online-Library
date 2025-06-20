package ba.unsa.etf.systemevents.repository;

import ba.unsa.etf.systemevents.model.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {
}
