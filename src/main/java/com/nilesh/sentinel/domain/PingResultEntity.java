package com.nilesh.sentinel.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ping_results")
public class PingResultEntity {
    @Id
    private String id;
    
    @Column(name = "endpoint_id")
    private String endpointId;
    
    private LocalDateTime timestamp;
    private int statusCode;
    private long responseTimeMs;

    protected PingResultEntity() {}

    public PingResultEntity(String endpointId, LocalDateTime timestamp, int statusCode, long responseTimeMs) {
        this.id = UUID.randomUUID().toString();
        this.endpointId = endpointId;
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.responseTimeMs = responseTimeMs;
    }

    public String getId() { return id; }
    public String getEndpointId() { return endpointId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatusCode() { return statusCode; }
    public long getResponseTimeMs() { return responseTimeMs; }
}
