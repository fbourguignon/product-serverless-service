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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerIntegrationTestTest extends IntegrationControllerTest {

    @Test
    @Order(1)
    void mustCreateProductWithSucess() throws JsonProcessingException {

        var productRequest = ProductRequest.builder()
                .name("Playstation 5")
                .category("Console")
                .description("Sony next generation console")
                .price(4500.00)
                .build();

        AwsProxyRequest request = new AwsProxyRequestBuilder("/products", HttpMethod.POST.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(createJsonBoby(productRequest))
                .build();

        final var response = handleApiGatewayRequest(request);

        final var productResponse = objectMapper.readValue(response.getBody(), ProductResponse.class);

        assertEquals(HttpStatus.CREATED.getCode(), response.getStatusCode());
        assertNotNull(productResponse.getId());
        assertEquals("Playstation 5", productResponse.getName());
        assertEquals("Console", productResponse.getCategory());
        assertEquals("Sony next generation console", productResponse.getDescription());
        assertEquals(4500.00, productResponse.getPrice());
    }

    @Test
    @Order(2)
    void mustUpdateProductWithSucess() throws JsonProcessingException {

        final var product = createProduct("Iphone 12", 6000.00, "Apple Smarthphone", "Smarthphone");

        final var productRequest = ProductRequest.builder()
                .price(7500.00)
                .build();

        AwsProxyRequest request = new AwsProxyRequestBuilder("/products/".concat(product.getId()), HttpMethod.PUT.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(createJsonBoby(productRequest))
                .build();

        final var apiGatewayResponse = handleApiGatewayRequest(request);

        final var productResponse = objectMapper.readValue(apiGatewayResponse.getBody(), ProductResponse.class);

        assertEquals(HttpStatus.OK.getCode(), apiGatewayResponse.getStatusCode());
        assertEquals(7500.00, productResponse.getPrice());
    }


    @Test
    @Order(3)
    void mustGetProductWithSucess() throws JsonProcessingException {
        final var product = createProduct("Xbox Series S", 2700.00, "Microsoft next generation console", "Console");

        AwsProxyRequest request = new AwsProxyRequestBuilder("/products/".concat(product.getId()), HttpMethod.GET.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .build();

        final var apiGatewayResponse = handleApiGatewayRequest(request);

        final var productResponse = objectMapper.readValue(apiGatewayResponse.getBody(), ProductResponse.class);

        assertEquals(HttpStatus.OK.getCode(), apiGatewayResponse.getStatusCode());
        assertEquals("Xbox Series S", productResponse.getName());
        assertEquals("Console", productResponse.getCategory());
        assertEquals("Microsoft next generation console", productResponse.getDescription());
        assertEquals(2700.00, productResponse.getPrice());
    }

    @Test
    @Order(4)
    void mustListProductsWithSucess() throws JsonProcessingException {
        AwsProxyRequest request = new AwsProxyRequestBuilder("/products", HttpMethod.GET.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .build();

        final var apiGatewayResponse = handleApiGatewayRequest(request);
        final var productResponseList = objectMapper.readValue(apiGatewayResponse.getBody(), new TypeReference<List<ProductResponse>>() {
        });

        assertEquals(HttpStatus.OK.getCode(), apiGatewayResponse.getStatusCode());

        assertEquals("Playstation 5", productResponseList.get(0).getName());
        assertEquals("Console", productResponseList.get(0).getCategory());
        assertEquals("Sony next generation console", productResponseList.get(0).getDescription());
        assertEquals(4500.00, productResponseList.get(0).getPrice());

        assertEquals("Iphone 12", productResponseList.get(1).getName());
        assertEquals("Smarthphone", productResponseList.get(1).getCategory());
        assertEquals("Apple Smarthphone", productResponseList.get(1).getDescription());
        assertEquals(7500.00, productResponseList.get(1).getPrice());

        assertEquals("Xbox Series S", productResponseList.get(2).getName());
        assertEquals("Console", productResponseList.get(2).getCategory());
        assertEquals("Microsoft next generation console", productResponseList.get(2).getDescription());
        assertEquals(2700.00, productResponseList.get(2).getPrice());
    }

    @Test
    @Order(5)
    void mustDeleteProductWithSucess() throws JsonProcessingException {

        final var product = createProduct("Xbox Series X", 5800.00, "Microsoft next generation console", "Console");

        AwsProxyRequest request = new AwsProxyRequestBuilder("/products/".concat(product.getId()), HttpMethod.DELETE.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .build();

        final var apiGatewayResponse = handleApiGatewayRequest(request);

        assertEquals(HttpStatus.NO_CONTENT.getCode(), apiGatewayResponse.getStatusCode());
    }

    private ProductResponse createProduct(String name, Double price, String description, String category) throws JsonProcessingException {

        final ProductRequest productRequest = ProductRequest.builder()
                .name(name)
                .price(price)
                .description(description)
                .category(category)
                .build();

        AwsProxyRequest request = new AwsProxyRequestBuilder("/products", HttpMethod.POST.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(createJsonBoby(productRequest))
                .build();

        final var response = handleApiGatewayRequest(request);

        return objectMapper.readValue(response.getBody(), ProductResponse.class);
    }

}
