package dk.ecosolutions.oms.domain;

/**
 * Represents an Item.
 *
 * @author Chamling Ram Rai
 * @version 1.0
 * @since 1.0
 */

public class Item {
    private int id;
    private int quantity;
    private int order_id;
    private Clothe clothe;

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

    public Clothe getClothe() {
        return clothe;
    }

    public void setClothe(Clothe clothes_id) {
        this.clothe = clothes_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}