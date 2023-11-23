package com.muratozgon.online.store.service.external.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muratozgon.online.store.service.external.error.ErrorResponse;
import com.muratozgon.online.store.service.external.error.FeignHttpExceptionHandler;
import feign.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ProductCreationFailedExceptionHandler implements FeignHttpExceptionHandler {
    private ObjectMapper objectMapper =new ObjectMapper();

    @Override
    public Exception handle(Response response) {
        if (response.status()== HttpStatus.BAD_REQUEST.value()) {
            try {
                String responseBody = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
                ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
                return new ProductCreationFailedException(errorResponse.getMessage());
            } catch (IOException e) {
                throw new RuntimeException("Error while deserializing the response body");
            }
        }
        return null;
    }
}
