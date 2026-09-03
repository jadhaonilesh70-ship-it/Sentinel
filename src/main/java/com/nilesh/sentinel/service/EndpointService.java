package com.nilesh.sentinel.service;

import com.nilesh.sentinel.domain.MonitoredEndpoint;
import com.nilesh.sentinel.domain.PingResult;
import com.nilesh.sentinel.domain.PingResultEntity;
import com.nilesh.sentinel.repository.MonitoredEndpointRepository;
import com.nilesh.sentinel.repository.PingResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class EndpointService {
    private static final Logger log = LoggerFactory.getLogger(EndpointService.class);

    private final MonitoredEndpointRepository endpointRepository;
    private final PingResultRepository pingResultRepository;
    private final HttpPingService pingService;

    public EndpointService(MonitoredEndpointRepository endpointRepository, 
                           PingResultRepository pingResultRepository, 
                           HttpPingService pingService) {
        this.endpointRepository = endpointRepository;
        this.pingResultRepository = pingResultRepository;
        this.pingService = pingService;
    }

    // Evict the cache whenever a new endpoint is added so the next GET fetches fresh DB data
    @CacheEvict(value = "endpoints", allEntries = true)
    public MonitoredEndpoint addEndpoint(String name, String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL format");
        }
        
        log.info("Adding new endpoint: {} [{}]", name, url);
        return endpointRepository.save(new MonitoredEndpoint(name, url));
    }

    // Cache this highly accessed list to avoid hitting the DB unnecessarily
    @Cacheable(value = "endpoints")
    public List<MonitoredEndpoint> getAllEndpoints() {
        log.info("Fetching endpoints from Database (Cache miss)");
        return endpointRepository.findAll();
    }

    @Scheduled(fixedRate = 60000)
    public void monitorEndpoints() {
        List<MonitoredEndpoint> endpoints = endpointRepository.findByActiveTrue();
        if(!endpoints.isEmpty()) {
            log.info("Running scheduled monitor for {} active endpoints", endpoints.size());
        }
        
        for (MonitoredEndpoint endpoint : endpoints) {
            PingResult result = pingService.executePing(endpoint);
            log.debug("Pinged {} - Status: {} - Latency: {}ms", endpoint.getUrl(), result.statusCode(), result.responseTimeMs());
            
            PingResultEntity entity = new PingResultEntity(
                result.endpointId(), 
                result.timestamp(), 
                result.statusCode(), 
                result.responseTimeMs()
            );
            pingResultRepository.save(entity);
        }
    }
}
