package com.muratozgon.user.session.service.controller;

import com.muratozgon.user.session.service.pojos.UserSessionValidatorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class UserSessionController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/user-sessions/validate")
    public ResponseEntity<?> validate(@RequestParam("sessionId")UUID sessionId) {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location", "http://localhost:" + serverPort + "/user-sessions/v2/validate?sessionId=" + sessionId)
                .build();
    }

    @GetMapping("/user-sessions/v2/validate")
    public UserSessionValidatorResponse validateV2(@RequestParam("sessionId")UUID sessionId, @RequestHeader(value = "X-Sleep", defaultValue = "0") long sleepTime) throws InterruptedException {
        log.info("Validate session called");
        Thread.sleep(sleepTime);
        boolean isValid = UUID.fromString("1bd0c74d-ed04-40b1-bc68-0de3992ec93f").equals(sessionId);
        return UserSessionValidatorResponse.builder()
                .sessionId(sessionId)
                .valid(isValid)
                .build();
    }
}
