package com.moviebooking.apigateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

/**
 * ============================================================
 * Fallback Controller (Circuit Breaker Responses)
 * ============================================================
 * WHAT: Provides user-friendly fallback responses when a
 *       downstream microservice is unavailable or timing out.
 *       These endpoints are invoked by the circuit breaker
 *       configured in GatewayRouteConfig.
 *
 * WHY Circuit Breaker Fallbacks:
 *       The Circuit Breaker pattern (from Release It! by Nygard)
 *       prevents cascading failures in microservices:
 *
 *       WITHOUT circuit breaker: Service A calls Service B
 *       (down) → thread pool exhausted waiting → Service A
 *       becomes slow → clients time out → entire platform fails.
 *
 *       WITH circuit breaker: After N failures, circuit "opens"
 *       → calls short-circuit immediately to fallback → no
 *       thread pool exhaustion → other services unaffected.
 *
 *
 *
 * WHY 503 Service Unavailable (not 500):
 *       503 signals a TEMPORARY unavailability — clients and
 *       load balancers know to retry later. 500 signals a
 *       permanent application error.
 * ============================================================
 */
@Slf4j
@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping("/auth")
    public Mono<ResponseEntity<Map<String, Object>>> authFallback() {
        log.error("[CIRCUIT-BREAKER] auth-service is unavailable");
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(buildFallbackResponse("Auth Service",
                        "Authentication service is temporarily unavailable. Please try again later.")));
    }


    private Map<String, Object> buildFallbackResponse(String service, String message) {
        return Map.of(
                "status", 503,
                "error", "Service Unavailable",
                "service", service,
                "message", message,
                "timestamp", Instant.now().toString(),
                "retryAfter", "30 seconds"
        );
    }
}
