package com.nilesh.sentinel.repository;

import com.nilesh.sentinel.domain.PingResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PingResultRepository extends JpaRepository<PingResultEntity, String> {
}
