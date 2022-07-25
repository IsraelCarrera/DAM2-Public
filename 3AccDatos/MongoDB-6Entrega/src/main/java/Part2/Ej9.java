package Part2;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Projections.*;

public class Ej9 {
    //    private static final MongoClient mongo = MongoClients.create("mongodb://10.0.0.1:27017");
    private static final MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
    private static final MongoDatabase db = mongo.getDatabase("Israel");
    private static final MongoCollection<Document> est = db.getCollection("Customer");

    public static void main(String[] args) {
        //9. Number of purchases per customer
        //Mongo Compass
//        [{
//            $match: {
//                purchases: {
//                    $exists: true
//                }
//            }
//        }, {
//            $project: {
//                name: 1,
//                        numberOfPurchases: {
//                    $size: '$purchases'
//                }
//            }
//        }]
        //Java no se si el Size está bien, es la forma que he encontrado por internet..
        est.aggregate(Arrays.asList(
                        match(exists("purchases", true)),
                        addFields(new Field<>("numberOfPurchases", new Document("$size", "$purchases"))),
                        project(fields(include("name","numberOfPurchases"),exclude("_id")))))
                .into(new ArrayList<>()).forEach(System.out::println);
    }
}
