package com.nilesh.sentinel.service;

import com.nilesh.sentinel.domain.MonitoredEndpoint;
import com.nilesh.sentinel.domain.PingResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DefaultHttpPingServiceTest {

    @Test
    void executePing_ShouldReturnResultWithLatency() {
        // Arrange
        DefaultHttpPingService pingService = new DefaultHttpPingService();
        // Using a reliable external URL for a basic integration/unit test hybrid
        MonitoredEndpoint endpoint = new MonitoredEndpoint("Google", "https://google.com");

        // Act
        PingResult result = pingService.executePing(endpoint);

        // Assert
        assertNotNull(result);
        assertEquals(endpoint.getId(), result.endpointId());
        assertTrue(result.statusCode() > 0, "Status code should be populated");
        assertTrue(result.responseTimeMs() >= 0, "Latency should be non-negative");
    }
}
