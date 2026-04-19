package com.lab.infortech.telemetry_ingestion_api.infrastructure.messaging;

import com.lab.infortech.telemetry_ingestion_api.domain.Telemetry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
        var authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String tokenJwt = authentication.getToken().getTokenValue();

        Message<Telemetry> kafkaMessage = MessageBuilder
                .withPayload(telemetry)
                .setHeader(KafkaHeaders.TOPIC, KAFKA_TOPIC)
                .setHeader(KafkaHeaders.KEY, telemetry.getPatientId().toString())
                .setHeader("Authorization", "Bearer " + tokenJwt)
                .build();

        kafkaTemplate.send(kafkaMessage);

        log.info("Enviando telemetria do paciente {} para o Kafka", telemetry.getPatientId());
    }
}
