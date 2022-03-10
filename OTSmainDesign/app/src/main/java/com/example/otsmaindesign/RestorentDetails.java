package com.example.otsmaindesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.otsmaindesign.adapter.RVAdapter;
import com.example.otsmaindesign.database.DatabaseHelper;

public class RestorentDetails extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    RVAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restorent_details);


        recyclerView = findViewById(R.id.shopRV);
        databaseHelper = new DatabaseHelper(getApplicationContext());

        rvAdapter = new RVAdapter(databaseHelper.getAllImagesData());

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(rvAdapter);
        rvAdapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String result = rvAdapter.result(position);
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
