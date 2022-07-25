package Part1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Sorts.descending;

public class Ej7 {
    //    private static final MongoClient mongo = MongoClients.create("mongodb://10.0.0.1:27017");
    private static final MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
    private static final MongoDatabase db = mongo.getDatabase("Israel");
    private static final MongoCollection<Document> est = db.getCollection("Madrid");

    public static void main(String[] args) {
        //    7. Event location with the largest number of events (Sol: Centro de interpretación de la
//            Naturaleza Montecarmelo, with 50 events)
        //MongoCompass
//        [{$group: {
//            _id: '$event-location',
//                    count: {
//                $sum: 1
//            }
//        }}, {$sort: {
//            count: -1
//        }}, {$limit: 1}]
        //java
        est.aggregate(List.of(
                group("$event-location", sum("count", 1)),
                sort(descending("count")),
                limit(1)
        )).into(new ArrayList<>()).forEach(System.out::println);
    }
}
