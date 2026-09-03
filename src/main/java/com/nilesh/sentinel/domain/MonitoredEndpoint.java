package com.nilesh.sentinel.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "monitored_endpoints")
public class MonitoredEndpoint {
    @Id
    private String id;
    private String name;
    private String url;
    private boolean active;

    // Default constructor required by JPA
    protected MonitoredEndpoint() {}

    public MonitoredEndpoint(String name, String url) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.url = url;
        this.active = true;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
