package dao.mongoDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import configuration.ConfigProperties;
import dao.DAOCustomers;
import lombok.extern.log4j.Log4j2;
import model.Converters;
import model.Customer;
import model.Purchase;
import model.Review;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.pull;
import static com.mongodb.client.model.Updates.push;


@Log4j2
public class DAOCustomerMongo implements DAOCustomers {
    MongoClient mongo = MongoClients.create(ConfigProperties.getInstance().getProperty("urlMongoDB"));
    MongoDatabase db = mongo.getDatabase(ConfigProperties.getInstance().getProperty("mongoDBName"));
    MongoCollection<Document> est = db.getCollection("Customer");
    List<Document> customers = new ArrayList<>();
    List<Document> purchases = new ArrayList<>();
    Converters converters = new Converters();

    @Override
    public Customer getCustomer(String id) {
        return getAllCustomer().stream().filter(customer -> customer.getDniCustomer().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Customer> getAllCustomer() {
        List<Customer> listCustomers = null;
        try {
            listCustomers = est
                    .find()
                    .projection(new Document("name", 1)
                            .append("telephone", 1)
                            .append("address", 1)
                            .append("_id", 1))
                    .into(customers)
                    .stream()
                    .map(it -> converters.convertDocumentToCustomer(it))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return listCustomers;
    }

    @Override
    public boolean saveCustomer(Customer t) {
        boolean isAdd = false;
        try {
            Document document = converters.convertCustomerToDocument(t);
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
    public boolean deleteCustomer(String dni) {
        boolean isDelete = false;
        try {
            est.deleteOne(eq("_id", dni));
            isDelete = true;
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return isDelete;
    }

    @Override
    public List<Purchase> getAllPurchases() {
        List<Purchase> listPurchases = null;
        try {
            listPurchases = est.find()
                    .projection(new Document("purchases.id_purchase", 1)
                            .append("purchases.date", 1)
                            .append("purchases.name_item", 1)
                            .append("_id", 1))
                    .into(purchases)
                    .stream()
                    .map(it -> {
                        List<Document> listPurs = it.getList("purchases", Document.class);
                        if (listPurs == null) {
                            listPurs = new ArrayList<>();
                        }
                        return listPurs.stream().map(p -> converters.convertDocumentToPurchase(p, (it.getString("_id")))).collect(Collectors.toList());
                    })
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return listPurchases;
    }

    @Override
    public boolean savePurchase(Purchase p) {
        boolean isSavePurchase = false;
        try {
            est.updateOne(
                    eq("_id", p.getIdCustomer()),
                    push("purchases", converters.convertPurchasesToDocumentForCustomer(p))
            );
            isSavePurchase = true;
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return isSavePurchase;
    }

    @Override
    public boolean deletePurchase(Purchase p) {
        boolean isDeletePurchase = false;
        try {
            est.updateOne(
                    eq("_id", p.getIdCustomer()),
                    pull("purchases", eq("id_purchase", p.getIdPurchase()))
            );
            isDeletePurchase = true;
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return isDeletePurchase;
    }

    @Override
    public boolean saveReview(Review r, Purchase p) {
        boolean isSave = false;
        try {
            est.updateOne(
                    eq("_id", p.getIdCustomer()),
                    pull("purchases", eq("id_purchase", p.getIdPurchase()))
            );
            est.updateOne(
                    eq("_id", p.getIdCustomer()),
                    push("purchases", converters.convertReviewToDocumentForCustomer(r, p))
            );
            isSave = true;
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return isSave;
    }

    @Override
    public boolean deleteReview(Review r) {
        boolean isDelete = false;
        Purchase purchase;
        try {
            //Primero busco la purchase, y la creo SIN review, borro con pull toda la purchase, y luego, la vuelvo a añadir.
            purchase = est.find(eq("purchases.id_purchase", r.getIdPurchase()))
                    .projection(new Document("purchases.id_purchase", 1)
                            .append("purchases.date", 1)
                            .append("purchases.name_item", 1)
                            .append("_id", 1))
                    .into(purchases)
                    .stream()
                    .map(it -> {
                        List<Document> listPurs = it.getList("purchases", Document.class);
                        if (listPurs == null) {
                            listPurs = new ArrayList<>();
                        }
                        return listPurs.stream().map(p -> converters.convertDocumentToPurchase(p, (it.getString("_id")))).collect(Collectors.toList());
                    })
                    .flatMap(List::stream)
                    .filter(purchase1 -> purchase1.getIdPurchase().equals(r.getIdPurchase()))
                    .collect(Collectors.toList()).get(0);
            //Ahora borramos la actual purchase
            est.updateOne(
                    eq("_id", r.getIdCustomer()),
                    pull("purchases", eq("id_purchase", r.getIdPurchase()))
            );

            //Y ahora añadimos la purchase.
            est.updateOne(
                    eq("_id", purchase.getIdCustomer()),
                    push("purchases", converters.convertPurchasesToDocumentForCustomer(purchase))
            );
            isDelete = true;
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return isDelete;
    }

    @Override
    public List<Purchase> getPurchasesByCustomer(String dni) {
        List<Purchase> listPurchases = null;
        try {
            listPurchases = est.find(eq("_id", dni))
                    .projection(new Document("purchases.id_purchase", 1)
                            .append("purchases.date", 1)
                            .append("purchases.name_item", 1)
                            .append("_id", 1))
                    .into(purchases)
                    .stream()
                    .map(it -> {
                        List<Document> listPurs = it.getList("purchases", Document.class);
                        if (listPurs == null) {
                            listPurs = new ArrayList<>();
                        }
                        return listPurs.stream().map(p -> converters.convertDocumentToPurchase(p, (it.getString("_id")))).collect(Collectors.toList());
                    })
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return listPurchases;
    }

    @Override
    public boolean checkIfCustomerHavePurchases(Customer t) {
        boolean havePurchases = false;
        List<Purchase> purchases = getPurchasesByCustomer(t.getDniCustomer());
        if (!purchases.isEmpty()) {
            havePurchases = true;
        }
        return havePurchases;
    }

}
