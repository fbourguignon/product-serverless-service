package br.com.product.integration;

import br.com.product.interfaces.requests.ProductRequest;
import br.com.product.interfaces.responses.ProductResponse;
import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductControllerIntegrationTestTest extends IntegrationControllerTest {

    private String productId;
    private final String PRODUCT_NAME = "Playstation 5";
    private final Double PRODUCT_OLD_PRICE = 4500.00;
    private final Double PRODUCT_NEW_PRICE = 4500.00;
    private final String PRODUCT_DESCRIPTION = "New sony console";
    private final String PRODUCT_CATEGORY = "Consoles";

    @Test
    @Order(1)
    void mustCreateProductWithSucess() throws JsonProcessingException {
        final ProductRequest productRequest = ProductRequest.builder()
                .name(PRODUCT_NAME)
                .price(PRODUCT_OLD_PRICE)
                .description(PRODUCT_DESCRIPTION)
                .category(PRODUCT_CATEGORY)
                .build();

        AwsProxyRequest request = new AwsProxyRequestBuilder("/products", HttpMethod.POST.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(createJsonBoby(productRequest))
                .build();

        final var response = handleApiGatewayRequest(request);

        final var productResponse = objectMapper.readValue(response.getBody(), ProductResponse.class);
        this.productId = productResponse.getId();

        assertEquals(HttpStatus.CREATED.getCode(), response.getStatusCode());
        assertNotNull(productResponse.getId());
        assertEquals(PRODUCT_NAME, productResponse.getName());
        assertEquals(PRODUCT_CATEGORY, productResponse.getCategory());
        assertEquals(PRODUCT_DESCRIPTION, productResponse.getDescription());
        assertEquals(PRODUCT_OLD_PRICE, productResponse.getPrice());
    }

    @Test
    @Order(2)
    void mustUpdateProductWithSucess() throws JsonProcessingException {

        final var productRequest = ProductRequest.builder()
                .price(PRODUCT_NEW_PRICE)
                .build();

        AwsProxyRequest request = new AwsProxyRequestBuilder("/products/".concat(this.productId), HttpMethod.PUT.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(createJsonBoby(productRequest))
                .build();

        final var apiGatewayResponse = handleApiGatewayRequest(request);

        final var productResponse = objectMapper.readValue(apiGatewayResponse.getBody(), ProductResponse.class);

        assertEquals(HttpStatus.OK.getCode(), apiGatewayResponse.getStatusCode());
        assertEquals(PRODUCT_NEW_PRICE, productResponse.getPrice());
    }


    @Test
    @Order(3)
    void mustGetProductWithSucess() throws JsonProcessingException {

        AwsProxyRequest request = new AwsProxyRequestBuilder("/products/".concat(this.productId), HttpMethod.GET.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .build();

        final var apiGatewayResponse = handleApiGatewayRequest(request);

        final var productResponse = objectMapper.readValue(apiGatewayResponse.getBody(), ProductResponse.class);

        assertEquals(HttpStatus.OK.getCode(), apiGatewayResponse.getStatusCode());
        assertEquals(this.productId,productResponse.getId());
        assertEquals(PRODUCT_NAME, productResponse.getName());
        assertEquals(PRODUCT_CATEGORY, productResponse.getCategory());
        assertEquals(PRODUCT_DESCRIPTION, productResponse.getDescription());
        assertEquals(PRODUCT_NEW_PRICE, productResponse.getPrice());
    }

    @Test
    @Order(4)
    void mustListProductsWithSucess() throws JsonProcessingException {

        AwsProxyRequest request = new AwsProxyRequestBuilder("/products", HttpMethod.GET.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .build();

        final var apiGatewayResponse = handleApiGatewayRequest(request);
        final var productResponseList = objectMapper.readValue(apiGatewayResponse.getBody(), new TypeReference<List<ProductResponse>>(){});

        final var productResponse = productResponseList.stream().findFirst().get();

        assertEquals(HttpStatus.OK.getCode(), apiGatewayResponse.getStatusCode());
        assertEquals(this.productId,productResponse.getId());
        assertEquals(PRODUCT_NAME, productResponse.getName());
        assertEquals(PRODUCT_CATEGORY, productResponse.getCategory());
        assertEquals(PRODUCT_DESCRIPTION, productResponse.getDescription());
        assertEquals(PRODUCT_NEW_PRICE, productResponse.getPrice());
    }

    @Test
    @Order(5)
    void mustDeleteProductWithSucess() {

        AwsProxyRequest request = new AwsProxyRequestBuilder("/products/".concat(this.productId), HttpMethod.DELETE.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .build();

        final var apiGatewayResponse = handleApiGatewayRequest(request);

        assertEquals(HttpStatus.NO_CONTENT.getCode(), apiGatewayResponse.getStatusCode());
    }
}
