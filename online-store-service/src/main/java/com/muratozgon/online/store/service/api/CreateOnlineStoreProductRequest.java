package com.muratozgon.online.store.service.api;

import lombok.Data;

@Data
public class CreateOnlineStoreProductRequest {
    private String name;
    private int initialStock;
}
