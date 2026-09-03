package com.nilesh.sentinel.repository;

import com.nilesh.sentinel.domain.MonitoredEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MonitoredEndpointRepository extends JpaRepository<MonitoredEndpoint, String> {
    List<MonitoredEndpoint> findByActiveTrue();
}
