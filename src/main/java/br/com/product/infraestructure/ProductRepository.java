package br.com.product.infraestructure;

import br.com.product.domain.entity.Product;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Singleton
public class ProductRepository {

    private final String DATABASE = "product-service";
    private final String COLLECTION = "products";
    private final MongoClient mongoClient;

    @Inject
    public ProductRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    private MongoCollection<Product> getCollection() {
        return mongoClient
                .getDatabase(DATABASE)
                .getCollection(COLLECTION, Product.class);
    }

    public Product get(ObjectId id) {
        log.info("Retrieving product from mongodb / id:[{}]", id.toString());
        Product product = getCollection().find(getBsonFilterById(id)).first();
            return nonNull(product) ? product : null;
    }

    public List<Product> list() {
        log.info("Listing products from mongodb");
        return getCollection().find().into(new ArrayList<>());
    }

    public Product save(Product product) {
        log.info("Saving product on mongodb / name:[{}]", product.getName());
        final InsertOneResult insertOneResult = getCollection().insertOne(product);
        return get(insertOneResult.getInsertedId().asObjectId().getValue());
    }

    public Product update(ObjectId id,Product product) {
        log.info("Updating product on mongodb / id:[{}] name:[{}]",id.toString(), product.getName());
        getCollection().updateOne(new Document("_id", id), new Document("$set", product));
        return get(id);
    }

    public void delete(ObjectId id){
        log.info("Removing product from mongodb / id:[{}]", id);
        getCollection().deleteOne(getBsonFilterById(id));
    }

    private Bson getBsonFilterById(ObjectId id) {
        return Filters.eq("_id", id);
    }

}
