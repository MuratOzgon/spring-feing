package com.muratozgon.online.store.service.external;

import org.springframework.web.bind.annotation.GetMapping;

public interface BaseClient {
    @GetMapping("/actuator/health")
    ActuatorHealthResponse health();
}
