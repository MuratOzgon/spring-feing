package com.muratozgon.online.store.service.external.error;

import feign.Response;

public interface FeignHttpExceptionHandler {
    Exception handle(Response response);
}
