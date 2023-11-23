package com.muratozgon.online.store.service;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.muratozgon.online.store.service.external.inventory.CreateProductRequest;
import com.muratozgon.online.store.service.external.inventory.CreateProductResponse;
import com.muratozgon.online.store.service.external.inventory.InventoryServiceClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest({"server.port=0", "eureka.client.enabled=false"})
public class InventoryServiceClientTest {

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public ServiceInstanceListSupplier serviceInstanceListSupplier() {
            return new TestServerInstanceListSupplier("inventory-service", 8081);
        }
    }

    @RegisterExtension
    static WireMockExtension INVENTORY_SERVICE = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(8081))
            .build();

    @Autowired
    InventoryServiceClient inventoryServiceClient;

    @Test
    public void testInventoryServiceClientCreateProductWorksProperly() {
        String responseBody = "{ \"productId\": \"ef115ae7-5af5-459c-a56f-8a1ea4cd3809\", \"name\": \"Phone\", \"stock\": 3}";
        INVENTORY_SERVICE.stubFor(WireMock.post("/products").withRequestBody(WireMock.equalToJson("{ \"name\": \"Phone\", \"initialStock\": 3}"))
                .willReturn(WireMock.okJson(responseBody)));

        CreateProductResponse response = inventoryServiceClient.createProduct(new CreateProductRequest("Phone", 3));

        assertThat(response.getProductId()).isNotNull();
        assertThat(response.getName()).isEqualTo("Phone");
        assertThat(response.getStock()).isEqualTo(3);

    }
}
