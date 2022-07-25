package model;

import javax.persistence.*;
@NamedQueries({
        @NamedQuery(
                name = "get_all_Items",
                query = "from Item"
        )
})


@Entity
@Table(name = "Items")
public class Item {
    private int idItem;
    private String name;
    private String company;
    private float price;

    public Item() {
    }

    public Item(int idItem, String name, String company, float price) {
        this.idItem = idItem;
        this.name = name;
        this.company = company;
        this.price = price;
    }

    public Item(String name, String company, float price) {
        this.name = name;
        this.company = company;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idItem", nullable = false)
    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "company", nullable = false, length = 45)
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (idItem != item.idItem) return false;
        if (Double.compare(item.price, price) != 0) return false;
        if (name != null ? !name.equals(item.name) : item.name != null) return false;
        if (company != null ? !company.equals(item.company) : item.company != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idItem;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Name: " + name + "  Company: " + company;
    }

    public String toStringPreHibernate() {
        return "ID: " + idItem + "  Name: " + name + "  Company: " + company + " Price: " + price;
    }
    public String toStringTextFile() {
        return idItem + ";" + name + ";" + company + ";" + price;
    }

    public String toStringVisual() {
        return "ID: " + idItem + "  Name: " + name + "  Company: " + company + " Price: " + String.format("%,.2f", price);
    }

}
