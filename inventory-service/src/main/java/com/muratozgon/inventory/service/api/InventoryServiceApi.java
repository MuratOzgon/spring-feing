package com.muratozgon.inventory.service.api;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class InventoryServiceApi {
    private static Map<UUID, Product> productMap = new HashMap<>();

    static {
        UUID productId = UUID.fromString("ef115ae7-5af5-459c-a56f-8a1ea4cd3809");
        productMap.put(productId, new Product(productId, "Phone", 5, null));
    }

    @PostMapping("/products")
    public CreateProductResponse createProduct(@RequestBody CreateProductRequest request) {
        String productName = request.getName();
        if (!StringUtils.hasText(productName)) {
            throw new ProductCreationFailedException("Product name cannot be empty");
        }
        int initialStock = request.getInitialStock();
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, productName, initialStock, null);
        productMap.put(productId, product);
        return CreateProductResponse.builder()
                .productId(productId)
                .name(productName)
                .stock(initialStock)
                .build();
    }

    @PostMapping("/products/{productId}/buy")
    public ResponseEntity<?> buy(@PathVariable("productId") UUID productId,
                                 @RequestParam(value = "amount", defaultValue = "1") int amount,
                                 @RequestParam("boughtAt")OffsetDateTime boughtAt) {
        Product product = productMap.get(productId);
        int currentStock = product.getStock();
        product.setStock(currentStock - amount);
        product.setLastBoughtAt(boughtAt);
        return ResponseEntity.ok().build();
    }
}
