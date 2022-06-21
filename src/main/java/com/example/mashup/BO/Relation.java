package com.example.mashup.BO;

public class Relation {
    private String type;
    private String resource;

    public Relation(String type, String resource) {
        this.type = type;
        this.resource = resource;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "type='" + type + '\'' +
                ", resource='" + resource + '\'' +
                '}';
    }
}
