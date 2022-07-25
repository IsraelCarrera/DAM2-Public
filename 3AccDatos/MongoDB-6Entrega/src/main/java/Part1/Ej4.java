package Part1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Ej4 {
    //    private static final MongoClient mongo = MongoClients.create("mongodb://10.0.0.1:27017");
    private static final MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
    private static final MongoDatabase db = mongo.getDatabase("Israel");
    private static final MongoCollection<Document> est = db.getCollection("Madrid");

    public static void main(String[] args) {
        // Number of events at Latina playing in January (Sol: 45)
        //Idem como en el 4, hacerlo por documments.
        //MongoCompass
//        [{
//            $match: {
//                'event-location': {
//                    $regex: RegExp('Latina')
//                }
//            }
//        }, {
//            $project: {
//                _id: 0,
//                        title: 1,
//                        fechaNueva: {
//                    $toDate: '$dtstart'
//                }
//            }
//        }, {
//            $project: {
//                _id: 0,
//                        title: 1,
//                        mes: {
//                    $month: '$fechaNueva'
//                }
//            }
//        }, {
//            $match: {
//                mes: 1
//            }
//        }, {
//            $count: 'count'
//        }]

        //Java
        est.aggregate(Arrays.asList(new Document("$match",
                                new Document("event-location",
                                        new Document("$regex", Pattern.compile("Latina")))),
                        new Document("$project",
                                new Document("_id", 0L)
                                        .append("title", 1L)
                                        .append("fechaNueva",
                                                new Document("$toDate", "$dtstart"))),
                        new Document("$project",
                                new Document("_id", 0L)
                                        .append("title", 1L)
                                        .append("mes",
                                                new Document("$month", "$fechaNueva"))),
                        new Document("$match",
                                new Document("mes", 1L)),
                        new Document("$count", "count")))
                .into(new ArrayList<>())
                .forEach(System.out::println);
    }
}
