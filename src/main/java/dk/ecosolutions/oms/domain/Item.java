package dk.ecosolutions.oms.domain;

public class Item {
    private int id;
    private int quantity;
    private int order_id;
    private int clothe_id;

    public Item() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getClothe_id() {
        return clothe_id;
    }

    public void setClothe_id(int clothes_id) {
        this.clothe_id = clothes_id;
    }
    public int getOrder_id(){
        return order_id;
    }
    public void setOrder_id(int order_id){
        this.order_id = order_id;
    }
}
