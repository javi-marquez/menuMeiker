package com.example.menumaker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDishFragment extends Fragment {
    String[] items = {"PASTA", "CARNE", "PESCADO", "LEGUMBRES", "ARROZ"};
    String category = "";
    AutoCompleteTextView auto_complete_text;
    ArrayAdapter<String> adapterItems;
    EditText edit_text_name;
    Button btn_add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_dish, container, false);

        edit_text_name = view.findViewById(R.id.edit_text_name);
        btn_add = view.findViewById(R.id.btn_add);

        auto_complete_text = view.findViewById(R.id.auto_complete_text);
        adapterItems = new ArrayAdapter<String>(getContext(), R.layout.list_item, items);
        auto_complete_text.setAdapter(adapterItems);
        auto_complete_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = edit_text_name.getEditableText().toString();

                if (name.isBlank()) {
                    Toast.makeText(getActivity(), "Introduce un nombre", Toast.LENGTH_SHORT).show();
                } else if (category.isEmpty()) {
                    Toast.makeText(getActivity(), "Elige una categoría", Toast.LENGTH_SHORT).show();
                } else {
                    ((MainActivity) getActivity()).addNewDish(new Dish(name, Dish.Categories.valueOf(category)));
                    ((MainActivity) getActivity()).replaceFragment(new DishesFragment());
                }
            }
        });

        return view;
    }
}