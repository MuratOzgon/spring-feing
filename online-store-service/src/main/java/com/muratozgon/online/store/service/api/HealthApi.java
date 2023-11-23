package com.muratozgon.online.store.service.api;

import com.muratozgon.online.store.service.external.ActuatorHealthResponse;
import com.muratozgon.online.store.service.external.BaseClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HealthApi {
    private final List<BaseClient> clients;

    @GetMapping("/online-store/health")
    public List<HealthResponse> health(){
        List<HealthResponse> responses = new ArrayList<>();
        for (BaseClient baseClient : clients) {
            ActuatorHealthResponse healthResponse = baseClient.health();
            responses.add(new HealthResponse(baseClient.getClass().getInterfaces()[0].getSimpleName(), healthResponse.getStatus()));
        }
        return responses;
    }
}
