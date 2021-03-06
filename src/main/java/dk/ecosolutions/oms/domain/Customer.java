package dk.ecosolutions.oms.domain;

/**
 * Represents an Customer.
 *
 * @author Chamling Ram Rai
 * @version 1.0
 * @since 1.0
 */

public class Customer {
    private int id;
    private String name;
    private String phone;

    public Customer() {
    }

    public Customer(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}