package com.nilesh.sentinel.service;

import com.nilesh.sentinel.domain.MonitoredEndpoint;
import com.nilesh.sentinel.domain.PingResult;
import com.nilesh.sentinel.domain.PingResultEntity;
import com.nilesh.sentinel.repository.MonitoredEndpointRepository;
import com.nilesh.sentinel.repository.PingResultRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EndpointService {
    private final MonitoredEndpointRepository endpointRepository;
    private final PingResultRepository pingResultRepository;
    private final HttpPingService pingService;

    // Constructor Injection (Best Practice for Dependency Injection)
    public EndpointService(MonitoredEndpointRepository endpointRepository, 
                           PingResultRepository pingResultRepository, 
                           HttpPingService pingService) {
        this.endpointRepository = endpointRepository;
        this.pingResultRepository = pingResultRepository;
        this.pingService = pingService;
    }

    public MonitoredEndpoint addEndpoint(String name, String url) {
        return endpointRepository.save(new MonitoredEndpoint(name, url));
    }

    public List<MonitoredEndpoint> getAllEndpoints() {
        return endpointRepository.findAll();
    }

    /**
     * Scheduled job that runs every 60 seconds.
     * It finds all active endpoints, pings them, and saves the results to PostgreSQL.
     */
    @Scheduled(fixedRate = 60000)
    public void monitorEndpoints() {
        List<MonitoredEndpoint> endpoints = endpointRepository.findByActiveTrue();
        
        for (MonitoredEndpoint endpoint : endpoints) {
            PingResult result = pingService.executePing(endpoint);
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
