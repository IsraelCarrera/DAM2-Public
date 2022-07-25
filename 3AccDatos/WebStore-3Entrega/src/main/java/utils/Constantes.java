package utils;

public class Constantes {
    //General
    public static final String WRITE_NUMBER = "You have to write a number.";

    //Customers
    public static final String CUSTOMER_UPDATE_FINE = "Customer update successfully. ";
    public static final String CUSTOMER_UPDATE_WRONG = "Customer couldn't be update.";
    public static final String CUSTOMER_NO_SELECTED = "Any customer hasn't been selected.";
    public static final String CUSTOMER_NO_ID = "There aren't any customer with this ID.";
    public static final String CUSTOMER_DELETE_FINE = "Customer delete with success.";
    public static final String CUSTOMER_DELETE_WRONG = "Customer can't been deleted.";
    public static final String CUSTOMER_DELETE_WITHPURCHASES = "Customer have a Purchases. Can't been deleted.";
    public static final String CUSTOMER_ADD_FINE = "Customer Added successfully.";
    public static final String CUSTOMER_ADD_WRONG = "Customer couldn't be added.";
    public static final String CUSTOMER_EXISTS = "There is already a customer with this user.";

    //Items
    public static final String ITEM_UPDATE_FINE = "Item update successfully. ";
    public static final String ITEM_UPDATE_WRONG = "Item couldn't be update.";
    public static final String ITEM_NO_SELECTED = "Any Item hasn't been selected.";
    public static final String ITEM_NO_ID = "There aren't any item with this ID.";
    public static final String ITEM_DELETE_FINE = "Item delete with success.";
    public static final String ITEM_DELETE_WRONG = "Item can't been deleted.";
    public static final String ITEM_NODELETE = "Item dont delete.";
    public static final String ITEM_PURCHASES_DELETE = "Item and its purchase has been deleted.";
    public static final String ITEM_DELETE_WITHPURCHASES = "This item has a purchase. Do you want to delete it anyway with their purchases?";
    public static final String ITEM_DELETE_REVIEWS = "Any purchase have a Review. Cant delete.";
    public static final String ITEM_ADD_FINE = "Item Added successfully.";
    public static final String ITEM_ADD_WRONG = "Item couldn't be added.";

    //Purchases

    public static final String PURCHASE_UPDATE_FINE = "Purchase update successfully. ";
    public static final String PURCHASE_NO_SELECTED = "Any purchase hasn't been selected.";
    public static final String PURCHASE_NO_ID = "There aren't any purchase with this ID.";
    public static final String PURCHASE_DELETE_FINE = "Purchase delete with success.";
    public static final String PURCHASE_DELETE_WRONG = "Purchase can't been deleted.";
    public static final String PURCHASE_NODELETE_REVIEWS = "This purchase have a Review. Can't delete that.";
    public static final String PURCHASE_ADD_FINE = "Purchase Added successfully.";
    public static final String PURCHASE_ADD_WRONG = "Purchase couldn't be added.";
    public static final String PURCHASE_ADD_NOSELECTED = "You haven't selected an item or customer.";


    //Reviews
    public static final String REVIEW_ADD_SUCCESSFULLY = "Review add successfully.";
    public static final String REVIEW_NOT_SAVED = "Review haven't been saved.";
    public static final String REVIEW_MISSING_TD = "Missing to fill in Title or description.";
    public static final String REVIEW_DONTSELECTED = "You haven't selected either of the two.";
    public static final String REVIEW_NORATING = "You haven't selected Rating.";
    public static final String REVIEW_REMOVED_SUCCESSFULLY = "Review removed successfully.";
    public static final String REVIEW_NOT_DELETED = "Review haven't been deleted. ";
    public static final String REVIEWS_NOTSELECTED = "You haven't selected any reviews.";
    public static final String REVIEWS_NOITEM = "There are no reviews with this item.";
    public static final String REVIEW_NOUPDATE = "Review hasn't been updated.";
    public static final String REVIEW_UPDATE = "Review update successfully.";
}
