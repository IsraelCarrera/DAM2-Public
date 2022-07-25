package utils;

public class Querys {
    //Getall
    public static final String SELECT_allItems_QUERY = "SELECT * FROM Items";
    public static final String SELECT_allCustomers_QUERY = "SELECT * FROM Customers";
    public static final String SELECT_allPurchases_QUERY = "SELECT idPurchase, date, Customers.idCustomer, Customers.name, telephone, address, Items.idItem, Items.name, company, price " +
            "FROM Purchases INNER JOIN (Customers, Items) ON  (Purchases.idItem= Items.idItem and Purchases.idCustomer = Customers.idCustomer)";
    public static final String SELECT_allReviews_QUERY = "SELECT idReview, rating, title, description, Reviews.date, Purchases.idPurchase, Purchases.date, Customers.idCustomer, " +
            "Customers.name, telephone, address, Items.idItem, Items.name, company, price FROM (((Reviews INNER JOIN Purchases ON Reviews.idPurchase=Purchases.idPurchase) " +
            "INNER JOIN Customers ON Purchases.idCustomer = Customers.idCustomer) INNER JOIN Items ON  Purchases.idItem= Items.idItem )";

    //GetById/Date/OthersId...
    public static final String SELECT_itemID_QUERY = "select * FROM Items where idItem= ?";
    public static final String SELECT_customerID_QUERY = "select * FROM Customers where idCustomer= ?";
    public static final String SELECT_purchasesID_QUERY = "SELECT idPurchase, date, Customers.idCustomer, Customers.name, telephone, address, Items.idItem, Items.name, company, price FROM Purchases INNER JOIN (Customers, Items) ON  (Purchases.idItem= Items.idItem and Purchases.idCustomer = Customers.idCustomer) where idPurchase= ?";
    public static final String SELECT_purchasesDate_QUERY = "SELECT idPurchase, date, Customers.idCustomer, Customers.name, telephone, address, Items.idItem, Items.name, company, price FROM Purchases INNER JOIN (Customers, Items) ON  (Purchases.idItem= Items.idItem and Purchases.idCustomer = Customers.idCustomer) where Purchases.date= ?";
    public static final String SELECT_purchasesIdItem_QUERY = "SELECT idPurchase, date, Customers.idCustomer, Customers.name, telephone, address, Items.idItem, Items.name, company, price FROM Purchases INNER JOIN (Customers, Items) ON  (Purchases.idItem= Items.idItem and Purchases.idCustomer = Customers.idCustomer)  where Purchases.idItem= ?";
    public static final String SELECT_purchasesIdCostumer_QUERY = "SELECT idPurchase, date, Customers.idCustomer, Customers.name, telephone, address, Items.idItem, Items.name, company, price FROM Purchases INNER JOIN (Customers, Items) ON  (Purchases.idItem= Items.idItem and Purchases.idCustomer = Customers.idCustomer)  where Purchases.idCustomer= ?";
    public static final String SELECT_reviewsIdPurchase_QUERY = "SELECT idReview, rating, title, description, Reviews.date, Purchases.idPurchase, Purchases.date, Customers.idCustomer, " +
            "Customers.name, telephone, address, Items.idItem, Items.name, company, price FROM (((Reviews INNER JOIN Purchases ON Reviews.idPurchase=Purchases.idPurchase) " +
            "INNER JOIN Customers ON Purchases.idCustomer = Customers.idCustomer) INNER JOIN Items ON  Purchases.idItem= Items.idItem ) where Reviews.idPurchase =  ?";
    public static final String SELECT_reviewsIdItem_QUERY = "SELECT idReview, rating, title, description, Reviews.date, Purchases.idPurchase, Purchases.date, Customers.idCustomer, " +
            "Customers.name, telephone, address, Items.idItem, Items.name, company, price FROM (((Reviews INNER JOIN Purchases ON Reviews.idPurchase=Purchases.idPurchase) " +
            "INNER JOIN Customers ON Purchases.idCustomer = Customers.idCustomer) INNER JOIN Items ON  Purchases.idItem= Items.idItem ) where Items.idItem =  ?";


    //Inserts
    public static final String INSERT_item_QUERY = "INSERT INTO Items (name,company,price) values(?,?,?)";
    public static final String INSERT_costumer_QUERY = "INSERT INTO Customers values(?,?,?,?)";
    public static final String INSERT_purchases_QUERY = "INSERT INTO Purchases values(?,?,?,?)";

    //Deletes
    public static final String DELETE_item_QUERY = "DELETE FROM Items where idItem= ?";
    public static final String DELETE_Customer_QUERY = "DELETE FROM Customers where idCustomer= ?";
    public static final String DELETE_purchases_QUERY = "DELETE FROM Purchases where idPurchase= ?";


    //Updates
    public static final String UPDATE_item_QUERY = "UPDATE Items SET name = ? , company = ? , price = ? where idItem = ?";
    public static final String UPDATE_customer_QUERY = "UPDATE Customers SET name = ? , telephone = ? , address = ? where idCustomer = ?";
    public static final String UPDATE_purchase_QUERY = "UPDATE Purchases SET date = ?  where idPurchase = ?";
}
