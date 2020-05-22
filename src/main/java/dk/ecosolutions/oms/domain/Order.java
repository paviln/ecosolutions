package dk.ecosolutions.oms.domain;
import java.sql.Timestamp;

public class Order {
    private int id;
    private int status;
    private Timestamp created_at;
    private int user_id;
    private int customer_id;

    public Order() {
    }

    public Order(int id, int status, Timestamp created_at, int user_id, int customer_id) {
        this.id = id;
        this.status = status;
        this.created_at = created_at;
        this.user_id = user_id;
        this.customer_id = customer_id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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


