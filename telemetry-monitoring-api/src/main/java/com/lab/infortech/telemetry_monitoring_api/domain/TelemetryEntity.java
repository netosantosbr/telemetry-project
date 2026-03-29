package com.lab.infortech.telemetry_monitoring_api.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.TimeSeries;

import java.time.Instant;

@TimeSeries(collection = "telemetry", timeField = "captureTime", metaField = "patientId")
@Data
public class TelemetryEntity {
    @Id
    private String id;
    private Integer patientId;
    private Integer heartRate;
    private Integer systolicBloodPressure;
    private Integer diastolicBloodPressure;
    private Instant captureTime;
}
