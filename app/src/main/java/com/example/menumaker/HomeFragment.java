package com.example.menumaker;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ArrayList<Dish> menu = new ArrayList<>();
    ConstraintLayout constraint_layout;
    TextView txt_dish_monday, txt_dish_tuesday, txt_dish_wednesday, txt_dish_thursday, txt_dish_friday;
    TextView txt_category_monday, txt_category_tuesday, txt_category_wednesday, txt_category_thursday, txt_category_friday;
    ImageView img_dish_monday, img_dish_tuesday, img_dish_wednesday, img_dish_thursday, img_dish_friday;
    TextView[] txt_dishes, txt_category;
    ImageView[] img_dishes;
    Button btn_generate;
    int[] img_resources;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        constraint_layout = view.findViewById(R.id.home_constraint_layout);

        txt_dish_monday = view.findViewById(R.id.txt_dish_monday);
        txt_dish_tuesday = view.findViewById(R.id.txt_dish_tuesday);
        txt_dish_wednesday = view.findViewById(R.id.txt_dish_wednesday);
        txt_dish_thursday = view.findViewById(R.id.txt_dish_thursday);
        txt_dish_friday = view.findViewById(R.id.txt_dish_friday);

        txt_dishes = new TextView[]{txt_dish_monday, txt_dish_tuesday, txt_dish_wednesday, txt_dish_thursday, txt_dish_friday};

        txt_category_monday = view.findViewById(R.id.txt_category_monday);
        txt_category_tuesday = view.findViewById(R.id.txt_category_tuesday);
        txt_category_wednesday = view.findViewById(R.id.txt_category_wednesday);
        txt_category_thursday = view.findViewById(R.id.txt_category_thursday);
        txt_category_friday = view.findViewById(R.id.txt_category_friday);

        txt_category = new TextView[]{txt_category_monday, txt_category_tuesday, txt_category_wednesday, txt_category_thursday, txt_category_friday};

        img_dish_monday = view.findViewById(R.id.img_dish_monday);
        img_dish_tuesday = view.findViewById(R.id.img_dish_tuesday);
        img_dish_wednesday = view.findViewById(R.id.img_dish_wednesday);
        img_dish_thursday = view.findViewById(R.id.img_dish_thursday);
        img_dish_friday = view.findViewById(R.id.img_dish_friday);

        img_dishes = new ImageView[]{img_dish_monday, img_dish_tuesday, img_dish_wednesday, img_dish_thursday, img_dish_friday};
        img_resources = new int[]{R.drawable.pasta, R.drawable.carne, R.drawable.pescado, R.drawable.legumbres, R.drawable.arroz};

        btn_generate = view.findViewById(R.id.btn_generate);

        menu = ((MainActivity) getActivity()).getMenu();

        if (menu.size() == 0) {
            constraint_layout.setVisibility(View.INVISIBLE);
        } else {
            constraint_layout.setVisibility(View.VISIBLE);
        }

        updateDisplay();

        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MainActivity) getActivity()).menuCanBeGenerated()) {
                    ((MainActivity) getActivity()).generateMenu();

                    constraint_layout.setVisibility(View.VISIBLE);
                    updateDisplay();

                } else {
                    Toast.makeText(getActivity(),
                            ((MainActivity) getActivity()).getMenuGenerationErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void updateDisplay() {
        menu = ((MainActivity) getActivity()).getMenu();

        for (int i = 0; i < menu.size(); i++) {
            String name = menu.get(i).getName();
            txt_dishes[i].setText(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
            txt_category[i].setText("(" + menu.get(i).getCategory().toString() + ")");
            img_dishes[i].setImageResource(img_resources[menu.get(i).getCategory().ordinal()]);
        }
    }
}