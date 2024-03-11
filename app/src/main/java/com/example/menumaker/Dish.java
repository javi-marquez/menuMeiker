package com.example.menumaker;

public class Dish {

    public enum Categories {
        PASTA, CARNE, PESCADO, LEGUMBRES, ARROZ
    }
    private String name;
    private Categories category;

    public Dish(){

    }

    public Dish(String name, Categories category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", category=" + category +
                '}';
    }
}
