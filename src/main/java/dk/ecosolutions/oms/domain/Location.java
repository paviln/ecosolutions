package dk.ecosolutions.oms.domain;

import dk.ecosolutions.oms.application.enums.Type;

public class Location implements Comparable {
    private int id;
    private String name;
    private int priority;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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

    @Override
    public int compareTo(Object compereLoc) {
        int comparePriority = ((Location) compereLoc).getPriority();
        return this.priority - comparePriority;
    }
}
