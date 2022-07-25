package Part2;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Field;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Projections.*;

public class Ej10 {
    //    private static final MongoClient mongo = MongoClients.create("mongodb://10.0.0.1:27017");
    private static final MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
    private static final MongoDatabase db = mongo.getDatabase("Israel");
    private static final MongoCollection<Document> est = db.getCollection("Customer");

    public static void main(String[] args) {
        //10. Number of reviews per customer
        //MongoCompass
//        [{
//            $match: {
//                purchases: {
//                    $exists: true
//                }
//            }
//        }, {
//            $project: {
//                name: 1,
//                        reviewsCustomer: {
//                    $size: '$purchases.review'
//                }
//            }
//        }]
        //Java.
        est.aggregate(Arrays.asList(
                        match(exists("purchases", true)),
                        addFields(new Field<>("reviewsCustomer", new Document("$size", "$purchases.review"))),
                project(fields(include("name","reviewsCustomer"),exclude("_id")))))
                .into(new ArrayList<>()).forEach(System.out::println);
    }
}
