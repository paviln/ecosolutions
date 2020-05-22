package dk.ecosolutions.oms.domain;

public class Location {
    private int id;
    private String name;
    private Address address;
    private int types_id;

    public Location() {
    }

    public Location(int id, String name, Address address, int types_id) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.types_id = types_id;
    }

    public boolean validation() {
        if (name.isEmpty()) {
            return false;
        }

        return address.validation();
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getTypes_id() {
        return types_id;
    }

    public void setTypes_id(int types_id) {
        this.types_id = types_id;
    }
}
