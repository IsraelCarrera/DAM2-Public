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

public class    Ej13 {
    //    private static final MongoClient mongo = MongoClients.create("mongodb://10.0.0.1:27017");
    private static final MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
    private static final MongoDatabase db = mongo.getDatabase("Israel");
    private static final MongoCollection<Document> est = db.getCollection("Customer");

    public static void main(String[] args) {
        //13. Customers with no review lower than 3
        //Entiendo que es customers con menos de 3 reviews, sino, estaré haciendo otra cosa.
        //Mongo Compass
//        [{$match: {
//            purchases: {
//                $exists: true
//            }
//        }}, {$set: {
//            numberofReviews: {
//                $size: '$purchases.review'
//            }
//        }}, {$match: {
//            numberofReviews: {
//                $lt: 3
//            }
//        }}]

        est.aggregate(Arrays.asList(
                        match(exists("purchases", true)),
                        addFields(new Field<>("numberofReviews", new Document("$size", "$purchases.review"))),
                        match(lt("numberofReviews", 3)),
                        project(fields(include("name", "numberofReviews"), exclude("_id")))))
                .into(new ArrayList<>()).forEach(System.out::println);
    }
}
