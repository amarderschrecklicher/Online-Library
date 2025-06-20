package ba.unsa.etf.systemevents.grpc;

import ba.unsa.etf.systemevents.model.EventLog;
import ba.unsa.etf.systemevents.repository.EventLogRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@GrpcService
public class EventLoggerServiceImpl extends EventLoggerServiceGrpc.EventLoggerServiceImplBase {

    @Autowired
    private EventLogRepository repository;

    @Override
    public void logEvent(EventRequest request, StreamObserver<EventResponse> responseObserver) {
        EventLog log = new EventLog();
        log.setTimestamp(LocalDateTime.parse(request.getTimestamp()));
        log.setMicroservice(request.getMicroservice());
        log.setUsername(request.getUsername());
        log.setActionType(request.getActionType());
        log.setResource(request.getResource());
        log.setResponseStatus(request.getResponseStatus());

        repository.save(log);

        EventResponse response = EventResponse.newBuilder()
                .setMessage("Log saved to database")
                .setSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
