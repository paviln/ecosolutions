package dk.ecosolutions.oms.domain;
import java.sql.Timestamp;

public class Order {
    private int id;
    private String status;
    private Timestamp date;
    private int userID;
    private int customerID;

    public Order() {
    }

    public Order(int id,String status, Timestamp date, int userID, int customerID) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.userID = userID;
        this.customerID = customerID;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}


