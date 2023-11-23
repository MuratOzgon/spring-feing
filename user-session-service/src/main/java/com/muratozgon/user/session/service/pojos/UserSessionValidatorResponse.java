package com.muratozgon.user.session.service.pojos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserSessionValidatorResponse {
    private boolean valid;
    private UUID sessionId;
}
