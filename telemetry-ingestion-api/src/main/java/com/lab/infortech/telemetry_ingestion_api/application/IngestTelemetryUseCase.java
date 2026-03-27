package com.lab.infortech.telemetry_ingestion_api.application;

import com.lab.infortech.telemetry_ingestion_api.domain.Telemetry;
import com.lab.infortech.telemetry_ingestion_api.entrypoint.rest.dto.TelemetryRecordDTO;
import com.lab.infortech.telemetry_ingestion_api.infrastructure.messaging.KafkaTelemetryProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IngestTelemetryUseCase {

    private final KafkaTelemetryProducer kafkaTelemetryProducer;

    public IngestTelemetryUseCase(KafkaTelemetryProducer kafkaTelemetryProducer) {
        this.kafkaTelemetryProducer = kafkaTelemetryProducer;
    }

    public void execute(TelemetryRecordDTO telemetryRecordDTO) {
        Telemetry telemetry = Telemetry.builder()
                .patientId(telemetryRecordDTO.patientId())
                .heartRate(telemetryRecordDTO.heartRate())
                .systolicBloodPressure(telemetryRecordDTO.systolicBloodPressure())
                .diastolicBloodPressure(telemetryRecordDTO.diastolicBloodPressure())
                .captureTime(telemetryRecordDTO.captureTime())
                .build();

        if(telemetry.isCritical()) {
            log.info("ALERTA: Paciente ID {} está em estado crítico!!", telemetry.getPatientId());
        }

        kafkaTelemetryProducer.produceMessage(telemetry);
    }
}
