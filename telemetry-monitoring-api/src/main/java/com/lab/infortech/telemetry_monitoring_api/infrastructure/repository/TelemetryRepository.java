package com.lab.infortech.telemetry_monitoring_api.infrastructure.repository;

import com.lab.infortech.telemetry_monitoring_api.domain.TelemetryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TelemetryRepository extends MongoRepository<TelemetryEntity, String> {
}
