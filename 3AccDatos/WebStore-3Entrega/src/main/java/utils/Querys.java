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

    public static final String SELECT_reviewsID_QUERY = "SELECT idReview, rating, title, description, Reviews.date, Purchases.idPurchase, Purchases.date, Customers.idCustomer, " +
            "Customers.name, telephone, address, Items.idItem, Items.name, company, price FROM (((Reviews INNER JOIN Purchases ON Reviews.idPurchase=Purchases.idPurchase) " +
            "INNER JOIN Customers ON Purchases.idCustomer = Customers.idCustomer) INNER JOIN Items ON  Purchases.idItem= Items.idItem ) where idReview= ?";

    public static final String SELECT_reviewsIdPurchase_QUERY = "SELECT idReview, rating, title, description, Reviews.date, Purchases.idPurchase, Purchases.date, Customers.idCustomer, " +
            "Customers.name, telephone, address, Items.idItem, Items.name, company, price FROM (((Reviews INNER JOIN Purchases ON Reviews.idPurchase=Purchases.idPurchase) " +
            "INNER JOIN Customers ON Purchases.idCustomer = Customers.idCustomer) INNER JOIN Items ON  Purchases.idItem= Items.idItem ) where Reviews.idPurchase =  ?";
    public static final String SELECT_reviewsIdItem_QUERY = "SELECT idReview, rating, title, description, Reviews.date, Purchases.idPurchase, Purchases.date, Customers.idCustomer, " +
            "Customers.name, telephone, address, Items.idItem, Items.name, company, price FROM (((Reviews INNER JOIN Purchases ON Reviews.idPurchase=Purchases.idPurchase) " +
            "INNER JOIN Customers ON Purchases.idCustomer = Customers.idCustomer) INNER JOIN Items ON  Purchases.idItem= Items.idItem ) where Items.idItem =  ?";
    public static final String SELECT_reviewsIdUser_QUERY = "SELECT idReview, rating, title, description, Reviews.date, Purchases.idPurchase, Purchases.date, Customers.idCustomer, " +
            "Customers.name, telephone, address, Items.idItem, Items.name, company, price FROM (((Reviews INNER JOIN Purchases ON Reviews.idPurchase=Purchases.idPurchase) " +
            "INNER JOIN Customers ON Purchases.idCustomer = Customers.idCustomer) INNER JOIN Items ON  Purchases.idItem= Items.idItem ) where Customers.idCustomer =  ?";
    public static final String SELECT_reviewsIdUserIdCustomer_QUERY = "SELECT idReview, rating, title, description, Reviews.date, Purchases.idPurchase, Purchases.date, Customers.idCustomer, " +
            "Customers.name, telephone, address, Items.idItem, Items.name, company, price FROM (((Reviews INNER JOIN Purchases ON Reviews.idPurchase=Purchases.idPurchase) " +
            "INNER JOIN Customers ON Purchases.idCustomer = Customers.idCustomer) INNER JOIN Items ON  Purchases.idItem= Items.idItem ) where Customers.idCustomer =  ? and Items.idItem = ?";

    public static final String SELECT_usersIdUser_QUERY = "Select * from Users where idUser= ?";
    public static final String SELECT_userLogin_QUERY = "Select * from Users where username=? and password=?";


    //Inserts
    public static final String INSERT_item_QUERY = "INSERT INTO Items (name,company,price) values(?,?,?)";
    public static final String INSERT_customer_QUERY = "INSERT INTO Customers (idCustomer,name,telephone,address) values(?,?,?,?)";
    public static final String INSERT_purchases_QUERY = "INSERT INTO Purchases (date,idCustomer,idItem) values(?,?,?)";
    public static final String INSERT_users_QUERY = "INSERT INTO Users (username, password) values(?,?)";
    public static final String INSERT_reviews_QUERY = "INSERT INTO Reviews (rating, title, description, date, idPurchase) values(?,?,?,?,?)";

    //Deletes
    public static final String DELETE_item_QUERY = "DELETE FROM Items where idItem= ?";
    public static final String DELETE_Customer_QUERY = "DELETE FROM Customers where idCustomer= ?";
    public static final String DELETE_purchases_QUERY = "DELETE FROM Purchases where idPurchase= ?";
    public static final String DELETE_users_QUERY = "DELETE FROM Users where idUser= ?";
    public static final String DELETE_reviews_QUERY = "DELETE FROM Reviews where idReview = ?";

    //Updates
    public static final String UPDATE_item_QUERY = "UPDATE Items SET name = ? , company = ? , price = ? where idItem = ?";
    public static final String UPDATE_customer_QUERY = "UPDATE Customers SET name = ? , telephone = ? , address = ? where idCustomer = ?";
    public static final String UPDATE_purchase_QUERY = "UPDATE Purchases SET date = ?  where idPurchase = ?";
    public static final String UPDATE_reviews_QUERY = "UPDATE Reviews SET rating = ?, title = ?, description = ?, date= ? where idReview = ?";
}
