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
import static com.mongodb.client.model.Filters.size;
import static com.mongodb.client.model.Projections.*;

public class Ej15 {
    //    private static final MongoClient mongo = MongoClients.create("mongodb://10.0.0.1:27017");
    private static final MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
    private static final MongoDatabase db = mongo.getDatabase("Israel");
    private static final MongoCollection<Document> est = db.getCollection("Customer");

    public static void main(String[] args) {
        //15. Budget spent by each customer
        //Mongo Compass
//        [{
//            $lookup: {
//                from: 'Item',
//                        localField: 'purchases.id_purchase',
//                        foreignField: 'purchases.id_purchase',
//                        as: 'cost'
//            }
//        }, {
//            $project: {
//                name: 1,
//                        costeTotal: {
//                    $sum: '$cost.price'
//                }
//            }
//        }]
        //Java
        est.aggregate(Arrays.asList(
                        lookup("Item","purchases.id_purchase","purchases.id_purchase","cost"),
                        addFields(new Field<>("costeTotal", new Document("$sum", "$cost.price"))),
                        project(fields(include("name","costeTotal")))))
                .into(new ArrayList<>()).forEach(System.out::println);
    }
}
