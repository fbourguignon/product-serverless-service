package br.com.product.infraestructure;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Replaces;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import javax.inject.Singleton;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Slf4j
@Factory
public class MongoClientFactory {

    @Property(name = "mongodb.uri")
    private String connectionUri;

    @Singleton
    @Replaces(MongoClient.class)
    MongoClient mongoClient()  {
        log.info("Creating mongodb client");

        CodecRegistry codecRegistry =
                fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        return MongoClients.create(MongoClientSettings
                .builder()
                .codecRegistry(codecRegistry)
                .applyConnectionString(new ConnectionString(connectionUri))
                .build());
    }
}
