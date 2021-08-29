package br.com.product.integration;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.env.PropertySource;
import io.micronaut.function.aws.proxy.MicronautLambdaHandler;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.MediaType;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

@MicronautTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationControllerTest {

    protected static MicronautLambdaHandler handler;
    protected static Context lambdaContext = new MockLambdaContext();
    protected static ObjectMapper objectMapper;

    private static Integer MONGO_PORT = 27017;

    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo:4.2.10")
            .withExposedPorts(MONGO_PORT);

    @BeforeAll
    protected static void setupSpec() {
        try {
            System.setProperty("mongodb.uri", mongo.getReplicaSetUrl());
            handler = new MicronautLambdaHandler();
            handler.getApplicationContext().getEnvironment().addPropertySource(PropertySource.of(Map.of("mongodb.uri",mongo.getReplicaSetUrl())));
            objectMapper = handler.getApplicationContext().getBean(ObjectMapper.class);
        } catch (ContainerInitializationException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    protected static void cleanupSpec() {
        handler.getApplicationContext().close();
    }


    protected AwsProxyResponse handleApiGatewayRequest(AwsProxyRequest request) {
        return handler.handleRequest(request, lambdaContext);
    }

    protected String createJsonBoby(Object value) throws JsonProcessingException {
       return objectMapper.writeValueAsString(value);
    }
}
