package com.lab.infortech.telemetry_ingestion_api.infrastructure.messaging;

import com.lab.infortech.telemetry_ingestion_api.domain.Telemetry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaTelemetryProducer {
    private final KafkaTemplate<String, Telemetry> kafkaTemplate;
    private static final String KAFKA_TOPIC = "telemetry-ingestion-topic";

    public KafkaTelemetryProducer(KafkaTemplate<String, Telemetry> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceMessage(Telemetry telemetry) {
        kafkaTemplate.send(KAFKA_TOPIC, telemetry.getPatientId().toString(), telemetry);
        log.info("Enviando telemetria do paciente {} para o Kafka", telemetry.getPatientId());
    }
}
