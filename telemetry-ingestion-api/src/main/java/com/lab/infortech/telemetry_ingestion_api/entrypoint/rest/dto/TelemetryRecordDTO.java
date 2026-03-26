package com.lab.infortech.telemetry_ingestion_api.entrypoint.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Instant;

public record TelemetryRecordDTO(
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
) {}
