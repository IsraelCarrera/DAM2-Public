package model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = "get_all_Customers",
                query = "from Customer"
        )
})


@Entity
@Table(name = "Customers", schema = "israelcarreramanzaneque-webstore")
public class Customer {
    private int idCustomer;
    private String name;
    private String telephone;
    private String address;

    public Customer() {
    }

    public Customer(int idCustomer, String name, String phone, String address) {
        this.idCustomer = idCustomer;
        this.name = name;
        this.address = address;
        this.telephone = phone;
    }

    public Customer(String name, String phone, String address) {
        this.name = name;
        this.address = address;
        this.telephone = phone;
    }

    @Id
    @Column(name = "idCustomer", nullable = false)
    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "telephone", nullable = false, length = 100)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "address", nullable = false, length = 100)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (idCustomer != customer.idCustomer) return false;
        if (name != null ? !name.equals(customer.name) : customer.name != null) return false;
        if (telephone != null ? !telephone.equals(customer.telephone) : customer.telephone != null) return false;
        if (address != null ? !address.equals(customer.address) : customer.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCustomer;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return idCustomer + " - " + name;
    }

    public String toStringPreHibernate() {
        return "ID: " + idCustomer + "  Name: " + name + "  Phone: " + telephone + "  Address: " + address;
    }


}
