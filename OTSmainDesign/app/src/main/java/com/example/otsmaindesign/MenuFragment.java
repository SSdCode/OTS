package com.example.otsmaindesign;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    FoodRVAdapter foodRVAdapter;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        final String name = getArguments().getString("name");

        /*View All Food Details*/
        recyclerView = v.findViewById(R.id.foodRV);
        databaseHelper = new DatabaseHelper(getContext());

        foodRVAdapter = new FoodRVAdapter(databaseHelper.getAllFoodData());
//        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(foodRVAdapter);


        foodRVAdapter.setOnItemClickListener(new FoodRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String result = foodRVAdapter.result(position);
                Intent i = new Intent(getContext(), FoodInfo.class);
                i.putExtra("foodname", result);
                i.putExtra("username", name);
                startActivity(i);
            }
        });
        return v;
    }
}
