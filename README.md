# Sentinel - Uptime & Health Monitoring API

## Overview
Sentinel is a backend service designed to monitor the health and uptime of external web services. Instead of a generic CRUD app, this project solves a real-world infrastructure problem by scheduling background HTTP pings and logging latency and status codes.

## Features
- **Dynamic Target Registration:** Register URLs to monitor via REST API.
- **Scheduled Background Pings:** Uses Spring @Scheduled to execute concurrent health checks.
- **Metrics Aggregation:** Calculates uptime percentages and average latency.

## Tech Stack
- Java 17, Spring Boot, Spring Scheduling
- PostgreSQL (for metrics persistence)
- RestTemplate / WebClient
