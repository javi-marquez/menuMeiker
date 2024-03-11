package com.example.menumaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom_menu;
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://menumeiker-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference databaseReference = database.getReference();
    private User user;
    private String userID, userEmail;
    private final int TOTAL_DAYS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_menu = findViewById(R.id.bottom_navigation_menu);
        bottom_menu.setSelectedItemId(R.id.list);

        userEmail = getIntent().getExtras().get("email").toString();
        userID = String.valueOf(userEmail.hashCode());

        DatabaseReference userReference = database.getReference("users/" + userID);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                if (user == null) {
                    createEmptyUserInDatabase();
                }

                replaceFragment(new DishesFragment());

                bottom_menu.setOnItemSelectedListener(item -> {
                    if (item.getItemId() == R.id.settings) {
                        replaceFragment(new SettingsFragment());
                    }
                    if (item.getItemId() == R.id.home) {
                        replaceFragment(new HomeFragment());
                    }
                    if (item.getItemId() == R.id.list) {
                        replaceFragment(new DishesFragment());
                    }

                    return true;
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("databaseUser", "ERROR");
            }
        });


    }

    private void createEmptyUserInDatabase() {
        user = new User("11111", userEmail, new ArrayList<>(), new ArrayList<>());
        database.getReference("users").child(userID).setValue(user);
    }

    private void updateUserInDatabase(){
        database.getReference("users").child(userID).setValue(user);
    }

    public int[] getSettingsAsIntArray() {
        char[] charSettings = user.getSettings().toCharArray();
        int[] intSettings = new int[charSettings.length];

        for (int i = 0; i < charSettings.length; i++) {
            intSettings[i] = Character.getNumericValue(charSettings[i]);
        }

        return  intSettings;
    }

    public void updateDishesSettings(String settings) {
        user.setSettings(settings);
        updateUserInDatabase();
    }

    public boolean menuCanBeGenerated(){
        Dish.Categories[] categories = Dish.Categories.values();

        int[] userSettings = getSettingsAsIntArray();

        for (int i = 0; i < userSettings.length; i++) {
            if (userSettings[i] == 0) continue;

            if (user.isCategoryEmpty(categories[i])) {
                return false;
            }
        }

        return true;
    }

    public String getMenuGenerationErrorMessage() {
        Dish.Categories[] categories = Dish.Categories.values();

        int[] userSettings = getSettingsAsIntArray();

        for (int i = 0; i < userSettings.length; i++) {
            if (userSettings[i] == 0) continue;

            if (user.isCategoryEmpty(categories[i])) {
                String errorMessage = "Añade al menos un plato a la categoría " + categories[i].name();
                return errorMessage;
            }
        }

        return "";
    }

    public void addNewDish(Dish dish){
        user.addNewDish(dish);
        updateUserInDatabase();
    }

    public void removeDish(Dish dish){
        user.removeDish(dish);
        updateUserInDatabase();
    }

    public void generateMenu(){
        ArrayList<Dish> newMenu = new ArrayList<>();
        Dish.Categories[] categories = Dish.Categories.values();
        int count = TOTAL_DAYS;
        int[] settings = getSettingsAsIntArray();
        int randomIndex = (int) (Math.random() * 5);

        while (count > 0) {
            if (settings[randomIndex] > 0) {
                settings[randomIndex]--;
                newMenu.add(user.getRandomDishFromCategory(categories[randomIndex]));
                count--;
            }
            randomIndex = (int) (Math.random() * 5);

        }

        user.setMenu(newMenu);
        updateUserInDatabase();

    }

    public ArrayList<Dish> getMenu(){
        return user.getMenu();
    }

    public ArrayList<Dish> getDishes() {
        return user.getDishes();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_constraint_layout, fragment);
        fragmentTransaction.commit();
    }



}