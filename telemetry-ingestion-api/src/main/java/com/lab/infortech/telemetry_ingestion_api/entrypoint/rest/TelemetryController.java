package com.lab.infortech.telemetry_ingestion_api.entrypoint.rest;

import com.lab.infortech.telemetry_ingestion_api.application.IngestTelemetryUseCase;
import com.lab.infortech.telemetry_ingestion_api.entrypoint.rest.dto.TelemetryRecordDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/telemetry")
public class TelemetryController {

    private final IngestTelemetryUseCase ingestTelemetryUseCase;

    public TelemetryController(IngestTelemetryUseCase ingestTelemetryUseCase) {
        this.ingestTelemetryUseCase = ingestTelemetryUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> sendTelemetry(@RequestBody @Valid TelemetryRecordDTO telemetryRecordDTO) {
        this.ingestTelemetryUseCase.execute(telemetryRecordDTO);
        return ResponseEntity.accepted().build();
    }
}
