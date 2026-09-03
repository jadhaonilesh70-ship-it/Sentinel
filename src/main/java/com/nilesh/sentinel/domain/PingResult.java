package com.nilesh.sentinel.domain;

import java.time.LocalDateTime;

/**
 * Using a Java Record for immutable data carrier.
 * This satisfies the Iteration 1 goal of modern Java 17 features.
 */
public record PingResult(String endpointId, LocalDateTime timestamp, int statusCode, long responseTimeMs) {}
