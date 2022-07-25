package Part1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Projections.*;

public class Ej8 {
    //    private static final MongoClient mongo = MongoClients.create("mongodb://10.0.0.1:27017");
    private static final MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
    private static final MongoDatabase db = mongo.getDatabase("Israel");
    private static final MongoCollection<Document> est = db.getCollection("Madrid");

    public static void main(String[] args) {
        //8. Average number of events of all locations
        //MongoCompass
//        [{$group: {
//            _id: '$event-location',
//                    count: {
//                $sum: 1
//            }
//        }}, {$group: {
//            _id: null,
//                    media: {
//                $avg: '$count'
//            }
//        }}, {$project: {
//            _id: 0,
//                    media: 1
//        }}]
        //java
        est.aggregate(List.of(
                group("$event-location", sum("count", 1)),
                group(null, avg("media", "$count")),
                project(fields(include("media"),exclude("_id")))
        )).into(new ArrayList<>()).forEach(System.out::println);
    }
}
