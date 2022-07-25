package Part3;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Field;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Accumulators.max;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

public class Ej16 {
    //    private static final MongoClient mongo = MongoClients.create("mongodb://10.0.0.1:27017");
    private static final MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
    private static final MongoDatabase db = mongo.getDatabase("Israel");
    private static final MongoCollection<Document> est = db.getCollection("Games");

    public static void main(String[] args) {
        ejer1();
        ejer2();
        ejer3();
        ejer4();
        ejer5();
    }

    //Find all games named "Call of Duty".
    public static void ejer1() {
        //Mongo Compass
//        [{
//            $match: {
//                Title: {
//                    $regex: 'Call of Duty'
//                }
//            }
//        }]
        //Java
        est.aggregate(Arrays.asList(
                        match(regex("Title", "Call of Duty"))))
                .into(new ArrayList<>())
                .forEach(System.out::println);

    }

    //Find the title of all games published by Sony.
    public static void ejer2() {
        //Mongo Compass
//        [{$match: {
//            'Metadata.Publishers': 'Sony'
//        }}, {$project: {
//            Title: 1,
//                    _id: 0
//        }}]
        //Java
        est.aggregate(Arrays.asList(
                        match(eq("Metadata.Publishers", "Sony")),
                        project(fields(include("Title"), exclude("_id")))))
                .into(new ArrayList<>())
                .forEach(System.out::println);

    }


    //Find all games with a score greater than 93 ordered from highest to lowest.
    public static void ejer3() {
        //Mongo Compass
//        [{$set: {
//            score: '$Metrics.Review Score'
//        }}, {$match: {
//            score: {
//                $gte: 93
//            }
//        }}, {$project: {
//            Title: 1,
//                    _id: 0,
//                    score: 1
//        }}, {$sort: {
//            score: -1
//        }}]
        //Java
        est.aggregate(Arrays.asList(
                        addFields(new Field<>("score", "$Metrics.Review Score")),
                        match(gte("score", 93)),
                        project(fields(include("Title", "score"), exclude("_id"))),
                        sort(descending("score"))))
                .into(new ArrayList<>()).forEach(System.out::println);

    }

    //Indicate the games that have been released by year (group by)
    public static void ejer4() {
        //MongoCompass
//        [{$set: {
//            year: '$Release.Year'
//        }}, {$group: {
//            _id: '$year',
//                    total: {
//                $sum: 1
//            }
//        }}, {$sort: {
//            _id: 1
//        }}]
        //java
        est.aggregate(Arrays.asList(
                        addFields(new Field<>("year", "$Release.Year")),
                        group("$year", sum("count", 1)),
                        sort(ascending("_id"))))
                .into(new ArrayList<>()).forEach(System.out::println);

    }

    //Indicate the highest score per year.
    public static void ejer5() {
        //MongoCompass
//        [{$set: {
//            year: '$Release.Year'
//        }}, {$group: {
//            _id: '$year',
//                    total: {
//                $max: '$Metrics.Review Score'
//            }
//        }}]
        est.aggregate(Arrays.asList(
                        addFields(new Field<>("year", "$Release.Year")),
                        group("$year", max("ratingMax", "$Metrics.Review Score")),
                        sort(ascending("_id"))))
                .into(new ArrayList<>()).forEach(System.out::println);

    }
}
