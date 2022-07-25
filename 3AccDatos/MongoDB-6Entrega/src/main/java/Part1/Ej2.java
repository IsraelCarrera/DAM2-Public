package Part1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Projections.*;

public class Ej2 {
    //    private static final MongoClient mongo = MongoClients.create("mongodb://10.0.0.1:27017");
    private static final MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
    private static final MongoDatabase db = mongo.getDatabase("Israel");
    private static final MongoCollection<Document> est = db.getCollection("Madrid");

    public static void main(String[] args) {
        //2. All events located at La Latina (Use last part of event-location)
        //MongoCompass
//        [{
//            $match: {
//                'event-location': {
//                    $regex: RegExp('Latina')
//                }
//            }
//        }]

        //Java
        est.aggregate(Arrays.asList(
                        match(regex("organization.organization-name", "Latina")),
                        project(fields(include("title","@id", "event-location"),exclude("_id")))))
                .into(new ArrayList<>())
                .forEach(System.out::println);
    }

}
