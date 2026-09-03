package com.nilesh.sentinel.service;

import com.nilesh.sentinel.domain.MonitoredEndpoint;
import com.nilesh.sentinel.domain.PingResult;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class DefaultHttpPingService implements HttpPingService {
    
    // Utilizing core Java 11+ HttpClient instead of third-party libraries
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    @Override
    public PingResult executePing(MonitoredEndpoint endpoint) {
        long start = System.currentTimeMillis();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint.getUrl()))
                    .GET()
                    .build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            long latency = System.currentTimeMillis() - start;
            
            return new PingResult(endpoint.getId(), LocalDateTime.now(), response.statusCode(), latency);
        } catch (Exception e) {
            // In case of timeout or unknown host, return a 500 status with -1 latency
            return new PingResult(endpoint.getId(), LocalDateTime.now(), 500, -1);
        }
    }
}
