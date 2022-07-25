package model;

import javax.persistence.*;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(
                name = "get_all_Purchase",
                query = "from Purchase"
        ),
        @NamedQuery(
                name = "get_purchase_by_idItem",
                query = "from Purchase p where p.item.id= :idItem"),
        @NamedQuery(
                name = "get_purchase_by_date",
                query = "from Purchase p where p.date= :fecha"),
        @NamedQuery(
                name = "get_purchase_by_idCustomer",
                query = "from Purchase p where p.customer.id= :idCostumer"),
        @NamedQuery(
                name = "get_purchase_between_dates",
                query = "from Purchase p where p.date BETWEEN :iniDate AND :finDate"),
        @NamedQuery(
                name = "count_purchase_between_dates",
                query = "select count(p) from Purchase p where p.item.id= :idItem and (p.date BETWEEN :iniDate AND :finDate)")
})

@Entity
@Table(name = "Purchases", schema = "israelcarreramanzaneque-webstore")
public class Purchase {
    private int idPurchase;
    private LocalDate date;
    private Customer customer;
    private Item item;

    public Purchase() {
    }

    public Purchase(int idPurchase, Customer customer, Item item, LocalDate date) {
        this.idPurchase = idPurchase;
        this.customer = customer;
        this.item = item;
        this.date = date;
    }
    public Purchase(Customer customer, Item item, LocalDate date) {
        this.customer = customer;
        this.item = item;
        this.date = date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPurchase", nullable = false)
    public int getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(int idPurchase) {
        this.idPurchase = idPurchase;
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

        Purchase purchase = (Purchase) o;

        if (idPurchase != purchase.idPurchase) return false;
        if (date != null ? !date.equals(purchase.date) : purchase.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPurchase;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idCustomer", referencedColumnName = "idCustomer", nullable = false)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customersByIdCustomer) {
        this.customer = customersByIdCustomer;
    }

    @ManyToOne
    @JoinColumn(name = "idItem", referencedColumnName = "idItem", nullable = false)
    public Item getItem() {
        return item;
    }

    public void setItem(Item itemsByIdItem) {
        this.item = itemsByIdItem;
    }

    @Override
    public String toString() {
        return "  Customer: {" + customer + "}  Item: {" + item + "}  Date: " + date;
    }

    public String toStringForClientInfo() {
        return "ID: " + idPurchase + "  ItemID: " + item.getIdItem() + "  Date: " + date + "\n";
    }

    public String toStringTexto() {
        return idPurchase + ";" + customer + ";" + item + ";" + date;
    }


}
