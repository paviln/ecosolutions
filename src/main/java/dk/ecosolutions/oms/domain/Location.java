package dk.ecosolutions.oms.domain;

import dk.ecosolutions.oms.application.enums.Type;

public class Location {
    private int id;
    private String name;
    private Address address;
    private Type type;

    public Location() {
    }

    public Location(int id, String name, Address address, Type type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = type;
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

    public Type getType() {
        return type;
    }

    public void setType(Type types_id) {
        this.type = types_id;
    }
}
