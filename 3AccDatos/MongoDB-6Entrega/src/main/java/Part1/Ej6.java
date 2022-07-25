package Part1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.regex;

public class Ej6 {
    //    private static final MongoClient mongo = MongoClients.create("mongodb://10.0.0.1:27017");
    private static final MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
    private static final MongoDatabase db = mongo.getDatabase("Israel");
    private static final MongoCollection<Document> est = db.getCollection("Madrid");

    public static void main(String[] args) {
        //6. Number of events per location at Latina
        //MongoCompass
//        [{
//            $match: {
//                'event-location': {
//                    $regex: RegExp('Latina')
//                }
//            }
//        }, {
//            $group: {
//                _id: '$event-location',
//                        count: {
//                    $sum: 1
//                }
//            }
//        }]
        //Java
        est.aggregate(List.of(
                match(regex("organization.organization-name", "Latina")),
                group("$event-location", sum("count", 1))
        )).into(new ArrayList<>()).forEach(System.out::println);
    }
}
