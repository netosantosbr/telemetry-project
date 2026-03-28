package com.lab.infortech.telemetry_monitoring_api.entrypoint.messaging.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Instant;

public record TelemetryMessage (
        @NotNull
        Integer patientId,
        @NotNull @Positive
        Integer heartRate,
        @NotNull @Positive
        Integer systolicBloodPressure,
        @NotNull @Positive
        Integer diastolicBloodPressure,
        @NotNull
        Instant captureTime
){}
