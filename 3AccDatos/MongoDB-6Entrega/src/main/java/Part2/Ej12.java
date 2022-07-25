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
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Accumulators.avg;


public class Ej12 {
    //    private static final MongoClient mongo = MongoClients.create("mongodb://10.0.0.1:27017");
    private static final MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
    private static final MongoDatabase db = mongo.getDatabase("Israel");
    private static final MongoCollection<Document> est = db.getCollection("Item");

    public static void main(String[] args) {
        //12. Number of items with average rating greater than 4
        //mongo Compass
//        [{$set: {
//            avgRatingReview: {
//                $avg: '$reviews.rate'
//            }
//        }}, {$match: {
//            avgRatingReview: {
//                $gt: 4
//            }
//        }}, {$count: 'total'}]

        //Java Se que hay una función AVG, pero no sé como funciona con AddFields
        est.aggregate(Arrays.asList(
                        addFields(new Field<>("avgRatingReview", new Document("$avg", "$reviews.rate"))),
                        match(gt("avgRatingReview",4)),
                        count("total")))
                .into(new ArrayList<>()).forEach(System.out::println);

    }
}
