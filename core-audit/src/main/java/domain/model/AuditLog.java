package domain.model;

import java.time.Instant;
import java.util.UUID;

public class AuditLog {

    private final String id;
    private final String eventType;
    private final String description;
    private final Instant timestamp;
    private final String metadata; // Detalhes extras em JSON ou String

    public AuditLog(String eventType, String description, String metadata) {
        this.id = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.description = description;
        this.metadata = metadata;
        this.timestamp = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getEventType() {
        return eventType;
    }

    public String getDescription() {
        return description;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getMetadata() {
        return metadata;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s | Detalhes: %s",
                timestamp, eventType, description, metadata);
    }

}
