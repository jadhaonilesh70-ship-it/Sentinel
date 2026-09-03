package com.nilesh.sentinel.service;

import com.nilesh.sentinel.domain.MonitoredEndpoint;
import com.nilesh.sentinel.domain.PingResult;

public interface HttpPingService {
    PingResult executePing(MonitoredEndpoint endpoint);
}
