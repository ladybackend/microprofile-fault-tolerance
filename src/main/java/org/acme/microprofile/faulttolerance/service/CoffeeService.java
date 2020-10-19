package org.acme.microprofile.faulttolerance.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.acme.microprofile.faulttolerance.vo.Coffee;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class CoffeeService {

    @Inject
    MongoClient mongoClient;

    private MongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("quarkus-coffee").getCollection("coffee");
    }

    public List<Coffee> list(){

        List<Coffee> coffeeList = new ArrayList<>();

        try (MongoCursor<Document> cursor = getCollection().find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();

                Coffee coffee = new Coffee(document.getInteger("id"),
                        document.getString("name"),
                        document.getString("countryOfOrigin"),
                        document.getInteger("price"));

                coffeeList.add(coffee);
            }
        }
        return coffeeList;
    }

}
