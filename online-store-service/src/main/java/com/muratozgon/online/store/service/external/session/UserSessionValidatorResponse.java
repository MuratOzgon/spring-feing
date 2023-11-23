package com.muratozgon.online.store.service.external.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionValidatorResponse {
    private boolean isValid;
    private String sessionId;
}
