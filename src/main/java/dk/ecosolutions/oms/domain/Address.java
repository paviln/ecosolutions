package dk.ecosolutions.oms.domain;

/**
 * Represents an Address.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */

public class Address {
    private int id;
    private String street;
    private String number;
    private String city;
    private String zip;

    public Address() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}