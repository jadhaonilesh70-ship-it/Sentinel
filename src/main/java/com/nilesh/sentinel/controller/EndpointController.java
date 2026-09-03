package com.nilesh.sentinel.controller;

import com.nilesh.sentinel.domain.MonitoredEndpoint;
import com.nilesh.sentinel.service.EndpointService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/endpoints")
public class EndpointController {
    
    private final EndpointService endpointService;

    public EndpointController(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

    @PostMapping
    public ResponseEntity<MonitoredEndpoint> createEndpoint(@RequestParam String name, @RequestParam String url) {
        MonitoredEndpoint saved = endpointService.addEndpoint(name, url);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<MonitoredEndpoint>> getEndpoints() {
        return ResponseEntity.ok(endpointService.getAllEndpoints());
    }
}
