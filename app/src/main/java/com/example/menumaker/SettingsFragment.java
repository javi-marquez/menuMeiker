package com.example.menumaker;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsFragment extends Fragment {
    private final int TOTAL_DISHES = 5;
    private int dishes_pasta = 0, dishes_carne = 0;
    //private int[] dishes = new int[]{dishes_pasta, dishes_carne};
    private int[] dishes;

    private enum Categories {
        PASTA, CARNE, PESCADO, LEGUMBRES, ARROZ
    }

    TextView txt_config_num_pasta, txt_config_num_carne, txt_config_num_pescado, txt_config_num_legumbres, txt_config_num_arroz;
    Button btn_minus_pasta, btn_minus_carne, btn_minus_pescado, btn_minus_legumbres, btn_minus_arroz;
    Button btn_plus_pasta, btn_plus_carne, btn_plus_pescado, btn_plus_legumbres, btn_plus_arroz;
    Button[] minus_buttons, plus_buttons;
    Button btn_save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        dishes = ((MainActivity) getActivity()).getSettingsAsIntArray();

        txt_config_num_pasta = (TextView) view.findViewById(R.id.txt_config_num_pasta);
        txt_config_num_carne = (TextView) view.findViewById(R.id.txt_config_num_carne);
        txt_config_num_pescado = (TextView) view.findViewById(R.id.txt_config_num_pescado);
        txt_config_num_legumbres = (TextView) view.findViewById(R.id.txt_config_num_legumbres);
        txt_config_num_arroz = (TextView) view.findViewById(R.id.txt_config_num_arroz);

        btn_minus_pasta = (Button) view.findViewById(R.id.btn_minus_pasta);
        btn_minus_carne = (Button) view.findViewById(R.id.btn_minus_carne);
        btn_minus_pescado = (Button) view.findViewById(R.id.btn_minus_pescado);
        btn_minus_legumbres = (Button) view.findViewById(R.id.btn_minus_legumbres);
        btn_minus_arroz = (Button) view.findViewById(R.id.btn_minus_arroz);

        btn_plus_pasta = (Button) view.findViewById(R.id.btn_plus_pasta);
        btn_plus_carne = (Button) view.findViewById(R.id.btn_plus_carne);
        btn_plus_pescado = (Button) view.findViewById(R.id.btn_plus_pescado);
        btn_plus_legumbres = (Button) view.findViewById(R.id.btn_plus_legumbres);
        btn_plus_arroz = (Button) view.findViewById(R.id.btn_plus_arroz);

        minus_buttons = new Button[]{btn_minus_pasta, btn_minus_carne, btn_minus_pescado, btn_minus_legumbres, btn_minus_arroz};
        plus_buttons = new Button[]{btn_plus_pasta, btn_plus_carne, btn_plus_pescado, btn_plus_legumbres, btn_plus_arroz};

        btn_save = (Button) view.findViewById(R.id.btn_save);

        txt_config_num_pasta.setText(String.valueOf(dishes[Categories.PASTA.ordinal()]));
        txt_config_num_carne.setText(String.valueOf(dishes[Categories.CARNE.ordinal()]));
        txt_config_num_pescado.setText(String.valueOf(dishes[Categories.PESCADO.ordinal()]));
        txt_config_num_legumbres.setText(String.valueOf(dishes[Categories.LEGUMBRES.ordinal()]));
        txt_config_num_arroz.setText(String.valueOf(dishes[Categories.ARROZ.ordinal()]));

        updateButtonsVisibility();

        btn_minus_pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtract(Categories.PASTA);
                updateButtonsVisibility();
                txt_config_num_pasta.setText("" + getDishesFromCategory(Categories.PASTA));
            }
        });

        btn_plus_pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(Categories.PASTA);
                updateButtonsVisibility();
                txt_config_num_pasta.setText("" + getDishesFromCategory(Categories.PASTA));
            }
        });

        btn_minus_carne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtract(Categories.CARNE);
                updateButtonsVisibility();
                txt_config_num_carne.setText("" + getDishesFromCategory(Categories.CARNE));
            }
        });

        btn_plus_carne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(Categories.CARNE);
                updateButtonsVisibility();
                txt_config_num_carne.setText("" + getDishesFromCategory(Categories.CARNE));
            }
        });

        btn_minus_pescado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtract(Categories.PESCADO);
                updateButtonsVisibility();
                txt_config_num_pescado.setText("" + getDishesFromCategory(Categories.PESCADO));
            }
        });

        btn_plus_pescado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(Categories.PESCADO);
                updateButtonsVisibility();
                txt_config_num_pescado.setText("" + getDishesFromCategory(Categories.PESCADO));
            }
        });

        btn_minus_legumbres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtract(Categories.LEGUMBRES);
                updateButtonsVisibility();
                txt_config_num_legumbres.setText("" + getDishesFromCategory(Categories.LEGUMBRES));
            }
        });

        btn_plus_legumbres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(Categories.LEGUMBRES);
                updateButtonsVisibility();
                txt_config_num_legumbres.setText("" + getDishesFromCategory(Categories.LEGUMBRES));
            }
        });

        btn_minus_arroz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtract(Categories.ARROZ);
                updateButtonsVisibility();
                txt_config_num_arroz.setText("" + getDishesFromCategory(Categories.ARROZ));
            }
        });

        btn_plus_arroz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(Categories.ARROZ);
                updateButtonsVisibility();
                txt_config_num_arroz.setText("" + getDishesFromCategory(Categories.ARROZ));
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getAvailableDishes() > 0) {
                    Toast.makeText(getActivity(), "Todavía quedan platos por asignar",
                            Toast.LENGTH_SHORT).show();
                } else {
                    saveSettings();
                    Toast.makeText(getActivity(), "La configuración se ha guardado",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void add(Categories category) {
        dishes[category.ordinal()]++;
    }

    private void subtract(Categories category) {
        dishes[category.ordinal()]--;
    }

    private void updateButtonsVisibility() {
        if (getAvailableDishes() == 0) {
            setButtonsVisibility(plus_buttons, View.INVISIBLE);
        } else {
            setButtonsVisibility(plus_buttons, View.VISIBLE);
        }

        for (int i = 0; i < dishes.length; i++) {
            if (dishes[i] == 0) {
                minus_buttons[i].setVisibility(View.INVISIBLE);
            } else {
                minus_buttons[i].setVisibility(View.VISIBLE);
            }
        }
    }

    private void setButtonsVisibility(Button[] buttons, int visibility) {
        for (Button button : buttons) {
            button.setVisibility(visibility);
        }
    }

    private int getAvailableDishes() {
        int currentSelectedDishes = 0;
        for (int i : dishes) {
            currentSelectedDishes += i;
        }

        return TOTAL_DISHES - currentSelectedDishes;
    }

    private int getDishesFromCategory(Categories categories) {
        return dishes[categories.ordinal()];
    }

    private void saveSettings() {
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).updateDishesSettings(getSettingsAsString());
        }
    }

    private String getSettingsAsString() {
        String s = "";
        for (int i = 0; i < dishes.length; i++) {
            s += dishes[i];
        }
        return s;
    }

}