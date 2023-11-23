package com.muratozgon.online.store.service.api;

import com.muratozgon.online.store.service.external.inventory.CreateProductRequest;
import com.muratozgon.online.store.service.external.inventory.CreateProductResponse;
import com.muratozgon.online.store.service.external.inventory.InventoryServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OnlineStoreApi {
    private final InventoryServiceClient inventoryServiceClient;

    @PostMapping("/online-store/products")
    public CreateOnlineStoreProductResponse createProduct(@RequestBody CreateOnlineStoreProductRequest request) {
        CreateProductResponse response = inventoryServiceClient.createProduct(new CreateProductRequest(request.getName(), request.getInitialStock()));
        return CreateOnlineStoreProductResponse.builder()
                .productId(response.getProductId())
                .stock(response.getStock())
                .name(response.getName())
                .build();
    }

    @PostMapping("/online-store/products/{productId}/buy")
    public ResponseEntity<?> buy(@PathVariable("productId") UUID productId) {
        OffsetDateTime boughtAt = OffsetDateTime.now(Clock.systemUTC());
        inventoryServiceClient.buy(productId.toString(), 1, boughtAt);
        return ResponseEntity.ok().build();
    }
}
