package com.lab.infortech.telemetry_monitoring_api.entrypoint.messaging;

import com.lab.infortech.telemetry_monitoring_api.entrypoint.messaging.dto.TelemetryMessage;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TelemetryKafkaConsumer {
    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.topic.telemetry}")
    public void consume(@Payload @Valid TelemetryMessage telemetryMessage) {
        log.info("Recebida telemetria do paciente ID: {} | Batimentos: {} bpm",
                telemetryMessage.patientId(),
                telemetryMessage.heartRate());
    }
}
