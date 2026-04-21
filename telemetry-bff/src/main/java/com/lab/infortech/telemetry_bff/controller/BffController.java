package com.lab.infortech.telemetry_bff.controller;

import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("/api/v1/telemetry")
@CrossOrigin(origins = "*")
public class BffController {

    private final Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();

    @KafkaListener(topics = "telemetry-ingestion-topic", groupId = "telemetry-bff-group")
    public void consumeFromKafka(String message) {
        sink.tryEmitNext(message);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamHeartbeats() {
        return sink.asFlux();
    }
}
