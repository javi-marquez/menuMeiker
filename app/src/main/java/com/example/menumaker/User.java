package com.example.menumaker;

import java.util.ArrayList;

public class User {
    private String settings;
    private String email;
    private ArrayList<Dish> menu = new ArrayList<>();
    private ArrayList<Dish> dishes = new ArrayList<>();

    public User() {
    }

    public User(String settings, String email, ArrayList<Dish> menu, ArrayList<Dish> dishes) {
        this.settings = settings;
        this.email = email;
        this.menu = menu;
        this.dishes = dishes;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Dish> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<Dish> menu) {
        this.menu = menu;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public ArrayList<Dish> getDishesFromCategory(Dish.Categories category) {
        ArrayList<Dish> dishesFromCategory = new ArrayList<>();

        for (Dish dish : dishes) {
            if (dish.getCategory().equals(category)) dishesFromCategory.add(dish);
        }

        return dishesFromCategory;
    }

    public Dish getRandomDishFromCategory(Dish.Categories category) {
        ArrayList<Dish> dishesFromCategory = getDishesFromCategory(category);
        int size = dishesFromCategory.size();
        int randomIndex = (int) (Math.random() * size);
        return dishesFromCategory.get(randomIndex);
    }

    public boolean isCategoryEmpty(Dish.Categories category) {
        return getDishesFromCategory(category).size() == 0;
    }

    public void addNewDish(Dish dish) {
        dishes.add(dish);
    }

    public void removeDish(Dish dish) {
        dishes.remove(dish);
    }

    @Override
    public String toString() {
        return "User{" +
                "settings='" + settings + '\'' +
                ", email='" + email + '\'' +
                ", menu=" + menu +
                ", dishes=" + dishes +
                '}';
    }
}
