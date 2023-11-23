package com.muratozgon.online.store.service.external.session;

import com.muratozgon.online.store.service.external.ActuatorHealthResponse;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserSessionClientFallbackFactory implements FallbackFactory<UserSessionClient> {
    @Override
    public UserSessionClient create(Throwable cause) {
        return new UserSessionClient() {
            @Override
            public UserSessionValidatorResponse validateSession(Map<String, Object> queryMap, Map<String, Object> headerMap) {
                String sessionId = (String) queryMap.get("sessionId");
                return new UserSessionValidatorResponse(false, sessionId);
            }

            @Override
            public ActuatorHealthResponse health() {
                return new ActuatorHealthResponse("DOWN");
            }
        };
    }
}
