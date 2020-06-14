package dk.ecosolutions.oms.domain;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.Timestamp;
import java.util.List;

/**
 * Represents an Order.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */

public class Order {
    private int id;
    private IntegerProperty status = new SimpleIntegerProperty();
    private List<Item> items;
    private Location location;
    private Timestamp created_at;
    private int user_id;
    private int customer_id;

    public Order() {
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status.get();
    }

    public void setStatus(int status) {
        this.status.set(status);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp date) {
        this.created_at = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
}