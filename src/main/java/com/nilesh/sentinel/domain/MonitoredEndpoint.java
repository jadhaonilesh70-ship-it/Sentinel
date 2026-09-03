package com.nilesh.sentinel.domain;

import java.util.UUID;

public class MonitoredEndpoint {
    private final String id;
    private String name;
    private String url;
    private boolean active;

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
