package com.lab.infortech.telemetry_monitoring_api.entrypoint.messaging;

import com.lab.infortech.telemetry_monitoring_api.domain.TelemetryEntity;
import com.lab.infortech.telemetry_monitoring_api.entrypoint.messaging.dto.TelemetryMessage;
import com.lab.infortech.telemetry_monitoring_api.infrastructure.repository.TelemetryRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TelemetryKafkaConsumer {

    private final TelemetryRepository telemetryRepository;
    private final JwtDecoder jwtDecoder;

    public TelemetryKafkaConsumer(TelemetryRepository telemetryRepository, JwtDecoder jwtDecoder) {
        this.telemetryRepository = telemetryRepository;
        this.jwtDecoder = jwtDecoder;
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.topic.telemetry}")
    public void consume(@Payload @Valid TelemetryMessage telemetryMessage,
                        @Header(value = "Authorization", required = false) String authHeader) {
        log.info("Recebida telemetria do paciente ID: {} | Batimentos: {} bpm",
                telemetryMessage.patientId(),
                telemetryMessage.heartRate());

        if(authHeader == null || !authHeader.startsWith("Bearer")) {
            log.error("Acesso negado: Tentativa de injeção de dados sem JWT.");
            throw new SecurityException("Mensagem rejeitada: Cabeçalho Authorization ausente ou inválido.");
        }

        String tokenRaw = authHeader.substring(7);

        try {
            Jwt jwt = jwtDecoder.decode(tokenRaw);

            JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("Token validado com sucesso! Processando telemetria do paciente: {}", telemetryMessage.patientId());

            TelemetryEntity telemetryEntity = TelemetryEntity.builder()
                    .patientId(telemetryMessage.patientId())
                    .heartRate(telemetryMessage.heartRate())
                    .diastolicBloodPressure(telemetryMessage.diastolicBloodPressure())
                    .systolicBloodPressure(telemetryMessage.systolicBloodPressure())
                    .captureTime(telemetryMessage.captureTime())
                    .build();

            this.telemetryRepository.save(telemetryEntity);

        } catch(Exception e) {
            log.error("Falha de segurança na mensagem do Kafka: {}", e.getMessage());
            throw new SecurityException("Token JWT inválido, adulterado ou expirado.", e);
        } finally {
            SecurityContextHolder.clearContext();
        }


    }
}
