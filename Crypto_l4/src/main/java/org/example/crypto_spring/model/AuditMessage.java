package org.example.crypto_spring.model;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AuditMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String eventType;
    private String entityName;
    private Long entityId;
    private String entityJson;
    private LocalDateTime timestamp;

    public AuditMessage() {
        this.timestamp = LocalDateTime.now();
    }

    public AuditMessage(String eventType, String entityName, Long entityId, String entityJson) {
        this();
        this.eventType = eventType;
        this.entityName = entityName;
        this.entityId = entityId;
        this.entityJson = entityJson;
    }
}