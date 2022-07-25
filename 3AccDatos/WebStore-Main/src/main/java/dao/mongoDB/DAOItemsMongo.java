package dao.mongoDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import configuration.ConfigProperties;
import dao.DAOItems;
import lombok.extern.log4j.Log4j2;
import model.Converters;
import model.Item;
import model.Purchase;
import model.Review;
import org.bson.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.pull;
import static com.mongodb.client.model.Updates.push;


@Log4j2
public class DAOItemsMongo implements DAOItems {
    MongoClient mongo = MongoClients.create(ConfigProperties.getInstance().getProperty("urlMongoDB"));
    MongoDatabase db = mongo.getDatabase(ConfigProperties.getInstance().getProperty("mongoDBName"));
    MongoCollection<Document> est = db.getCollection("Item");
    List<Document> auxiliar = new ArrayList<>();
    Converters converters = new Converters();


    @Override
    public List<Item> getAllItems() {
        List<Item> listItems = null;
        try {
            listItems = est
                    .find()
                    .projection(new Document("company", 1)
                            .append("name", 1)
                            .append("price", 1)
                            .append("_id", 1))
                    .into(auxiliar)
                    .stream()
                    .map(it -> converters.convertDocumentToItem(it))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return listItems;
    }

    @Override
    public boolean saveItem(Item t) {
        boolean isAdd = false;
        try {
            Document document = converters.convertItemToDocument(t);
            est.insertOne(document);
            isAdd = true;
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return isAdd;
    }


    @Override
    public boolean deleteItem(Item t) {
        boolean isDelete = false;
        //Borrar un item.
        try {
            est.deleteOne(eq("_id", t.getIdItem()));
            isDelete = true;
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return isDelete;
    }

    @Override
    public boolean savePurchase(Purchase p) {
        boolean isAdd = false;
        try {
            //Comprobar
            est.updateOne(
                    eq("name", p.getNameItem()),
                    push("purchases", converters.convertPurchasesToDocumentForItems(p))
            );
            isAdd = true;
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return isAdd;
    }

    @Override
    public boolean deletePurchase(Purchase p) {
        boolean isDeletePurchase = false;
        try {
            long s = est.updateOne(
                    eq("name", p.getNameItem()),
                    pull("purchases", eq("id_purchase", p.getIdPurchase()))
            ).getMatchedCount();
            isDeletePurchase = true;
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return isDeletePurchase;
    }


    @Override
    public List<Review> getAllReviews() {
        List<Review> listReviews = null;
        try {
            listReviews = est.find()
                    .projection(new Document("reviews.id_purchase", 1)
                            .append("reviews.title", 1)
                            .append("reviews.description", 1)
                            .append("reviews.dni_customer", 1)
                            .append("reviews.rate", 1)
                            .append("reviews.date", 1)
                            .append("name", 1)
                            .append("_id", 0))
                    .into(auxiliar)
                    .stream()
                    .map(it -> {
                        List<Document> listRevws = it.getList("reviews", Document.class);
                        if (listRevws == null) {
                            listRevws = new ArrayList<>();
                        }
                        return listRevws.stream().map(p -> converters.convertDocumentToReviews(p, it.getString("name"))).collect(Collectors.toList());
                    })
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return listReviews;
    }

    @Override
    public boolean saveReview(Review r) {
        boolean isAdd = false;
        try {
            //Primero borro la anterior, y luego, la coloco.
            est.updateOne(
                    eq("name", r.getNameItem()),
                    pull("reviews", eq("id_purchase", r.getIdPurchase()))
            );
            est.updateOne(
                    eq("name", r.getNameItem()),
                    push("reviews", converters.convertReviewToDocumentForItems(r))
            );
            isAdd = true;
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return isAdd;
    }

    @Override
    public boolean deleteReview(Review r) {
        boolean isDeleteReview = false;
        try {
            est.updateOne(
                    eq("name", r.getNameItem()),
                    pull("reviews", eq("id_purchase", r.getIdPurchase()))
            );
            isDeleteReview = true;
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return isDeleteReview;
    }

    @Override
    public List<Review> getReviewsByIdCustomer(String dni) {
        List<Review> listReviews = null;
        try {
            listReviews = est.find(eq("reviews.dni_customer", dni))
                    .projection(new Document("reviews.id_purchase", 1)
                            .append("reviews.title", 1)
                            .append("reviews.description", 1)
                            .append("reviews.rate", 1)
                            .append("reviews.date", 1)
                            .append("reviews.dni_customer", 1)
                            .append("name", 1)
                            .append("_id", 0))
                    .into(auxiliar)
                    .stream()
                    .map(it -> {
                        List<Document> listRevws = it.getList("reviews", Document.class);
                        if (listRevws == null) {
                            listRevws = new ArrayList<>();
                        }
                        return listRevws.stream().map(p -> converters.convertDocumentToReviews(p, it.getString("name"))).collect(Collectors.toList());
                    })
                    .flatMap(List::stream)
                    .filter(review -> review.getIdCustomer().equals(dni))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return listReviews;
    }

    @Override
    public boolean checkIfPurchaseHaveReview(Purchase purchase) {
        boolean haveReviews = false;
        List<Review> listReviews = est.find(eq("reviews.dni_customer", purchase.getIdCustomer()))
                .projection(new Document("reviews.id_purchase", 1)
                        .append("reviews.title", 1)
                        .append("reviews.description", 1)
                        .append("reviews.rate", 1)
                        .append("reviews.date", 1)
                        .append("reviews.dni_customer", 1)
                        .append("name", 1)
                        .append("_id", 0))
                .into(auxiliar)
                .stream()
                .map(it -> {
                    List<Document> listRevws = it.getList("reviews", Document.class);
                    if (listRevws == null) {
                        listRevws = new ArrayList<>();
                    }
                    return listRevws.stream().map(p -> converters.convertDocumentToReviews(p, it.getString("name"))).collect(Collectors.toList());
                })
                .flatMap(List::stream)
                .filter(review -> review.getIdPurchase().equals(purchase.getIdPurchase()))
                .collect(Collectors.toList());
        if (!listReviews.isEmpty()) {
            haveReviews = true;
        }
        return haveReviews;
    }

    @Override
    public boolean checkIfItemHaveReviews(Item t) {
        boolean haveReviews = false;
        try {
            List<String> listIds = est
                    .find(eq("name", t.getName()))
                    .projection(new Document("reviews.id_purchase", 1)
                            .append("_id", 0))
                    .into(auxiliar).stream().map(it -> {
                        List<Document> reviews = it.getList("reviews", Document.class);
                        if (reviews == null) {
                            reviews = new ArrayList<>();
                        }
                        return reviews.stream().map(p -> p.getString("id_purchase")).collect(Collectors.toList());
                    })
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            if (!listIds.isEmpty()) {
                haveReviews = true;
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return haveReviews;
    }

    @Override
    public List<Purchase> checkIfItemHavePurchases(Item t) {
        List<Purchase> listIds = null;
        try {
            listIds = est
                    .find(eq("name", t.getName()))
                    .projection(new Document("purchases.id_purchase", 1)
                            .append("name", 1)
                            .append("purchases.date", 1)
                            .append("purchases.dni_customer", 1)
                            .append("_id", 0))
                    .into(auxiliar)
                    .stream()
                    .map(it -> {
                        List<Document> listPurs = it.getList("purchases", Document.class);
                        if (listPurs == null) {
                            listPurs = new ArrayList<>();
                        }
                        return listPurs.stream().map(p -> converters.convertDocumentItemToPurchase(p, (it.getString("name")))).collect(Collectors.toList());
                    })
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return listIds;
    }

    @Override
    public Review getReviewByPurchase(Purchase purchase) {
        Review r = null;
        List<Review> listReviews = null;
        try {
            listReviews = est.find(eq("name", purchase.getNameItem()))
                    .projection(new Document("reviews.id_purchase", 1)
                            .append("reviews.title", 1)
                            .append("reviews.description", 1)
                            .append("reviews.rate", 1)
                            .append("reviews.date", 1)
                            .append("reviews.dni_customer", 1)
                            .append("name", 1)
                            .append("_id", 0))
                    .into(auxiliar)
                    .stream()
                    .map(it -> {
                        List<Document> listRevws = it.getList("reviews", Document.class);
                        if (listRevws == null) {
                            listRevws = new ArrayList<>();
                        }
                        return listRevws.stream().map(p -> converters.convertDocumentToReviews(p, it.getString("name"))).collect(Collectors.toList());
                    })
                    .flatMap(List::stream)
                    .filter(review -> review.getIdPurchase().equals(purchase.getIdPurchase()))
                    .collect(Collectors.toList());
            if (!listReviews.isEmpty()) {
                r = listReviews.get(0);
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return r;
    }

    @Override
    public int getNumberPurchases(Item t) {
        long numberPurchases = 0;
        try {
            numberPurchases = est.find(eq("name", t.getName()))
                    .projection(new Document("purchases.date", 1)
                            .append("_id", 0))
                    .into(auxiliar)
                    .stream()
                    .map(it -> {
                        List<Document> listDates = it.getList("purchases", Document.class);
                        if (listDates == null) {
                            listDates = new ArrayList<>();
                        }
                        return listDates.stream().map(p -> Instant.ofEpochMilli(p.getDate("date").getTime())
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()).collect(Collectors.toList());
                    })
                    .flatMap(List::stream)
                    .filter(date -> (date.getMonth().equals((LocalDate.now().minusMonths(1)).getMonth())) && (date.getYear() == (LocalDate.now().minusMonths(1)).getYear()))
                    .count();
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return Math.toIntExact(numberPurchases);
    }

    @Override
    public double getAverageRating(Item t) {
        double ratingItem = 0;
        try {
            ratingItem = est.find(eq("name", t.getName()))
                    .projection(new Document("reviews.rate", 1)
                            .append("_id", 0))
                    .into(auxiliar)
                    .stream()
                    .map(it -> {
                        List<Document> listDates = it.getList("reviews", Document.class);
                        if (listDates == null) {
                            listDates = new ArrayList<>();
                        }
                        return listDates.stream().map(p -> p.getInteger("rate")).collect(Collectors.toList());
                    })
                    .flatMap(List::stream)
                    .mapToDouble(Double::valueOf).average().getAsDouble();
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return ratingItem;
    }

    @Override
    public List<Review> getReviewsByItem(Item t) {
        List<Review> listReviews = null;
        try {
            listReviews = est.find(eq("name", t.getName()))
                    .projection(new Document("reviews.id_purchase", 1)
                            .append("reviews.title", 1)
                            .append("reviews.description", 1)
                            .append("reviews.rate", 1)
                            .append("reviews.date", 1)
                            .append("reviews.dni_customer", 1)
                            .append("name", 1)
                            .append("_id", 0))
                    .into(auxiliar)
                    .stream()
                    .map(it -> {
                        List<Document> listRevws = it.getList("reviews", Document.class);
                        if (listRevws == null) {
                            listRevws = new ArrayList<>();
                        }
                        return listRevws.stream().map(p -> converters.convertDocumentToReviews(p, it.getString("name"))).collect(Collectors.toList());
                    })
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return listReviews;
    }
}
