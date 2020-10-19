package org.acme.microprofile.faulttolerance.service;


import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import org.acme.microprofile.faulttolerance.vo.Coffee;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ReactiveCoffeeService {


    @Inject
    ReactiveMongoClient mongoClient;
    @Inject

    public Uni<List<Coffee>> list() {
        return getCollection().find()
                .map(doc -> {
                    Coffee coffee = new Coffee(doc.getInteger("id"),
                            doc.getString("name"),
                            doc.getString("countryOfOrigin"),
                            doc.getInteger("price"));
                    return coffee;
                }).collectItems().asList();
    }

    private ReactiveMongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("quarkus-coffee").getCollection("coffee");
    }
}
