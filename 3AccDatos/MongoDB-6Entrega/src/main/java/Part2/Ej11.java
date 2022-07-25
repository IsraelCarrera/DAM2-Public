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
import static com.mongodb.client.model.Sorts.descending;

public class Ej11 {
    //    private static final MongoClient mongo = MongoClients.create("mongodb://10.0.0.1:27017");
    private static final MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
    private static final MongoDatabase db = mongo.getDatabase("Israel");
    private static final MongoCollection<Document> est = db.getCollection("Customer");

    public static void main(String[] args) {
        //11. Id of the customer with more purchases
        //Mongo Compass
//        [{$match: {
//            purchases: {
//                $exists: true
//            }
//        }}, {$set: {
//            purchasesTotal: {
//                $size: '$purchases'
//            }
//        }}, {$sort: {
//            purchasesTotal: -1
//        }}, {$limit: 1}, {$project: {
//            _id: 1
//        }}]
        //Java
        est.aggregate(Arrays.asList(
                        match(exists("purchases", true)),
                        addFields(new Field<>("purchasesTotal", new Document("$size", "$purchases"))),
                        sort(descending("purchasesTotal")),
                        limit(1),
                        project(fields(include("_id")))))
                .into(new ArrayList<>()).forEach(System.out::println);
    }
}
