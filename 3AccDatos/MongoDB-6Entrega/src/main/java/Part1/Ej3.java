package Part1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

public class Ej3 {
    //    private static final MongoClient mongo = MongoClients.create("mongodb://10.0.0.1:27017");
    private static final MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
    private static final MongoDatabase db = mongo.getDatabase("Israel");
    private static final MongoCollection<Document> est = db.getCollection("Madrid");

    public static void main(String[] args) {
        //3.Title of events starting in January
        //Copiar lo que trae directamente del compass
        //MongoCompass
//        [{
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
//        }]

        //Java
        est.aggregate(Arrays.asList(new Document("$project",
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
                                new Document("mes", 1L))))
                .into(new ArrayList<>())
                .forEach(System.out::println);
    }
}
