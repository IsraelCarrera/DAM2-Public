package model;


import org.bson.Document;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

public class Converters {
    //User
    public  User convertDocumentToUser(Document d){
        return User.builder()
                .dniUser(d.getString("_id"))
                .username(d.getString("username"))
                .password(d.getString("password"))
                .tipo(d.getString("user_type"))
                .build();
    }

    public Document convertUserToDocument(User user){
        Document d = new Document()
                .append("_id",user.getDniUser())
                .append("username",user.getUsername())
                .append("password",user.getPassword())
                .append("user_type",user.getTipo());
        return d;
    }



    //Items
    public Item convertDocumentToItem(Document d){
        return Item.builder()
                .idItem(d.getObjectId("_id"))
                .company(d.getString("company"))
                .name(d.getString("name"))
                .price(d.getDouble("price"))
                .build();
    }

    public Document convertItemToDocument(Item item){
        Document d = new Document()
                .append("name", item.getName())
                .append("company",item.getCompany())
                .append("price",item.getPrice());
        return d;
    }

    //Customers
    public Customer convertDocumentToCustomer(Document d){
        return Customer.builder()
                .dniCustomer(d.getString("_id"))
                .name(d.getString("name"))
                .phone(d.getString("telephone"))
                .address(d.getString("address"))
                .build();
    }

    public Document convertCustomerToDocument(Customer customer){
        Document d = new Document()
                .append("_id",customer.getDniCustomer())
                .append("name",customer.getName())
                .append("telephone",customer.getPhone())
                .append("address", customer.getAddress());
        return d;
    }

    //Purchases
    public Purchase convertDocumentToPurchase(Document d, String idCustomer){
        return Purchase.builder()
                .idPurchase(d.getString("id_purchase"))
                .date(Instant.ofEpochMilli(d.getDate("date").getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .idCustomer(idCustomer)
                .nameItem(d.getString("name_item"))
                .build();
    }
    public Purchase convertDocumentItemToPurchase(Document d, String nameItem){
        return Purchase.builder()
                .idPurchase(d.getString("id_purchase"))
                .date(Instant.ofEpochMilli(d.getDate("date").getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .idCustomer(d.getString("dni_customer"))
                .nameItem(nameItem)
                .build();
    }

    public Document convertPurchasesToDocumentForCustomer(Purchase purchase){
        Document d = new Document()
                .append("id_purchase",purchase.getIdPurchase())
                .append("name_item",purchase.getNameItem())
                .append("date",Date.from(purchase.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return d;
    }
    public Document convertPurchasesToDocumentForItems(Purchase purchase){
        Document d = new Document()
                .append("id_purchase",purchase.getIdPurchase())
                .append("dni_customer",purchase.getIdCustomer())
                .append("date",Date.from(purchase.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return d;
    }

    //Reviews
    public Review convertDocumentToReviews(Document d, String nameItem){
        return Review.builder()
                .idPurchase(d.getString("id_purchase"))
                .nameItem(nameItem)
                .title(d.getString("title"))
                .description(d.getString("description"))
                .rating(d.getInteger("rate"))
                .idCustomer(d.getString("dni_customer"))
                .date(Instant.ofEpochMilli(d.getDate("date").getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .build();
    }

    public Document convertReviewToDocumentForItems(Review r){
        Document d = new Document()
                .append("id_purchase",r.getIdPurchase())
                .append("dni_customer",r.getIdCustomer())
                .append("title", r.getTitle())
                .append("description", r.getDescription())
                .append("rate", r.getRating())
                .append("date",Date.from(r.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return  d;
    }

    //Como no sabía muy bien como hacerlo, borro la purchase, y la añado con la review, así también de esta forma, controlo que si hace otra review, solo pertenezca la más nueva.
    public Document convertReviewToDocumentForCustomer(Review r, Purchase purchase){
        Document rev = new Document()
                .append("title", r.getTitle())
                .append("description", r.getDescription())
                .append("rate", r.getRating())
                .append("date",Date.from(r.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Document d = new Document()
                .append("id_purchase",purchase.getIdPurchase())
                .append("name_item",purchase.getNameItem())
                .append("date",Date.from(purchase.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .append("review", rev);
        return  d;
    }
}
