package dk.ecosolutions.oms.domain;

/**
 * Represents an Clothe.
 *
 * @author Chamling Ram Rai
 * @version 1.0
 * @since 1.0
 */

public class Clothe {
    private int id;
    private String name;

    public Clothe() {

    }

    public Clothe(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return name;
    }
}