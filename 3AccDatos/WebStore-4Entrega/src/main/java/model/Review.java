package model;

import javax.persistence.*;
import java.time.LocalDate;
@NamedQueries({
        @NamedQuery(
                name = "get_all_Reviews",
                query = "from Review"
        )
        ,
        @NamedQuery(
                name = "get_Review_ByIdItem",
                query = "from Review r where r.purchase.item.id= :idItem")
        ,
        @NamedQuery(
                name = "get_Review_ByIdPurchase",
                query = "from Review r where r.purchase.id= :idPurchase")
        ,
        @NamedQuery(
                name = "get_Review_ByIdCustomer",
                query = "from Review r where r.purchase.customer.id= :idCustomer")
        ,
        @NamedQuery(
                name = "get_Review_ByIdCustomer_and_IdPurchase",
                query = "from Review r where r.purchase.item.id= :idItem and r.purchase.customer.id= :idCustomer")
        ,
        @NamedQuery(
                name = "get_Review_ByRate",
                query = "from Review r where r.rating= :rate")
        ,
        @NamedQuery(
                name = "avgRating_Review_ByIdItem",
                query = "select avg(r.rating) from Review r where r.purchase.item.id= :idItem")
        ,
        @NamedQuery(
                name = "get_Review_ByIdItem_OrderByRatingAsc",
                query = "from Review r where r.purchase.item.id= :idItem order by r.rating asc ")
        ,
        @NamedQuery(
                name = "get_Review_ByIdItem_OrderByRatingDesc",
                query = "from Review r where r.purchase.item.id= :idItem order by r.rating desc")
        ,
        @NamedQuery(
                name = "get_Review_ByIdItem_OrderByDateAsc",
                query = "from Review r where r.purchase.item.id= :idItem order by r.date asc ")
        ,
        @NamedQuery(
                name = "get_Review_ByIdItem_OrderByDateDesc",
                query = "from Review r where r.purchase.item.id= :idItem order by r.date desc")
})

@Entity
@Table(name = "Reviews", schema = "israelcarreramanzaneque-webstore")
public class Review {
    private int idReview;
    private int rating;
    private String title;
    private String description;
    private LocalDate date;
    private Purchase purchase;

    public Review() {
    }

    public Review(int idReview, int rating, String title, String description, Purchase purchase) {
        this.idReview = idReview;
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.date = LocalDate.now();
        this.purchase = purchase;
    }

    public Review(int idReview, int rating, String title, String description, LocalDate date, Purchase purchase) {
        this.idReview = idReview;
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.date = date;
        this.purchase = purchase;
    }

    public Review(int rating, String title, String description, LocalDate date, Purchase purchase) {
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.date = date;
        this.purchase = purchase;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReview", nullable = false)
    public int getIdReview() {
        return idReview;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    @Basic
    @Column(name = "rating", nullable = false)
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 200)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (idReview != review.idReview) return false;
        if (rating != review.rating) return false;
        if (title != null ? !title.equals(review.title) : review.title != null) return false;
        if (description != null ? !description.equals(review.description) : review.description != null) return false;
        return date != null ? date.equals(review.date) : review.date == null;
    }

    @Override
    public int hashCode() {
        int result = idReview;
        result = 31 * result + rating;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idPurchase", referencedColumnName = "idPurchase", nullable = false)
    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchasesByIdPurchase) {
        this.purchase = purchasesByIdPurchase;
    }

    @Override
    public String toString() {
        return "ItemID: " + purchase.getItem().getIdItem() + " CustomerID: " + purchase.getCustomer().getIdCustomer() + " Rating: " + rating;
    }
    public String toStringAntesDeHibernate() {
        return "No. " + idReview + "  ItemID: " + purchase.getItem().getIdItem() + "\nRating: " + rating + "\nTitle: " + title + "\nComment: " + description + "\nDate: " + date + "  CustomerID: " + purchase.getCustomer().getIdCustomer() + "  Purchase no. " + purchase.getIdPurchase();
    }

    public String toStringVisual() {
        return "No. " + idReview + "  ItemID: " + purchase.getItem().getIdItem() + "  Rating: " + rating + "\nTitle: " + title + "\nComment: " + description + "\nDate: " + date + "\n____________________________________________________________\n";
    }

    public String toStringTexto() {
        return idReview + ":" + rating + ":" + title + ":" + description + ":" + date + ":" + purchase.getCustomer().getIdCustomer() + ":" + purchase.getItem().getIdItem() + ":" + purchase.getIdPurchase();
    }
}
