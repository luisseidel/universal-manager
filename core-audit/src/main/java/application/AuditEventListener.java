package application;

import com.manager.shared.events.IDomainEvent;
import domain.model.AuditLog;
import domain.repository.IAuditRepository;

public class AuditEventListener {
    private final IAuditRepository repository;

    public AuditEventListener(IAuditRepository repository) {
        this.repository = repository;
    }

    public void onEvent(IDomainEvent event) {
        AuditLog log = new AuditLog(
                event.eventType(),
                "Processamento de evento capturado via EventBus",
                event.toString()
        );

        repository.save(log);
    }
}
