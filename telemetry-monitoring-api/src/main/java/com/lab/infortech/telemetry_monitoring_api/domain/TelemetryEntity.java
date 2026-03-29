package com.lab.infortech.telemetry_monitoring_api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.TimeSeries;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TimeSeries(collection = "telemetry", timeField = "captureTime", metaField = "patientId")
public class TelemetryEntity {
    @Id
    private String id;
    private Integer patientId;
    private Integer heartRate;
    private Integer systolicBloodPressure;
    private Integer diastolicBloodPressure;
    private Instant captureTime;
}
