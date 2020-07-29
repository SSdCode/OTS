package com.example.otsmaindesign;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment {
    DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    Cart_RvAdapter cart_rvAdapter;

    public CardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_card, container, false);


        /*View All Food Details*/
        recyclerView = v.findViewById(R.id.card_foodRV);
        databaseHelper = new DatabaseHelper(getContext());

        cart_rvAdapter = new Cart_RvAdapter(databaseHelper.getAllCartData());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(cart_rvAdapter);

        cart_rvAdapter.setOnItemClickListener(new Cart_RvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String username = getArguments().getString("name");
                String fdName = cart_rvAdapter.getImageName(position);
                String totalPrice = cart_rvAdapter.getTotalAmt(position);
                Intent i = new Intent(getContext(), CartItemPayment.class);
                i.putExtra("food_name", fdName);
                i.putExtra("username", username);
                i.putExtra("total_amt", totalPrice);
                startActivity(i);
            }
        });
        return v;
    }
}
