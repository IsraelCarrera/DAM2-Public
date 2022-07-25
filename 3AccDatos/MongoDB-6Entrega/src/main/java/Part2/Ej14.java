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
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

public class Ej14 {
    //    private static final MongoClient mongo = MongoClients.create("mongodb://10.0.0.1:27017");
    private static final MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
    private static final MongoDatabase db = mongo.getDatabase("Israel");
    private static final MongoCollection<Document> est = db.getCollection("Customer");

    public static void main(String[] args) {
        //14. Customers that are not registered as users (Use $lookup)
        //Mongo Compass
//        [{$lookup: {
//            from: 'User',
//                    localField: '_id',
//                    foreignField: '_id',
//                    as: 'user'
//        }}, {$match: {
//            user: {
//                $size: 0
//            }
//        }}]

        //JAVA
        est.aggregate(Arrays.asList(
                        lookup("User","_id","_id","user"),
                        match(size("user",0)),
                        project(fields(include("name"), exclude("_id")))))
                .into(new ArrayList<>()).forEach(System.out::println);
    }
}
