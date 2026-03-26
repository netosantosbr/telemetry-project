package com.lab.infortech.telemetry_ingestion_api.domain;

import lombok.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Telemetry {
    private Integer patientId;
    private Integer heartRate;
    private Integer systolicBloodPressure;
    private Integer diastolicBloodPressure;
    private Instant captureTime;

    public Boolean isCritical() {
        if(heartRate == null) return false;
        return heartRate < 50 || heartRate > 120;
    }
}
