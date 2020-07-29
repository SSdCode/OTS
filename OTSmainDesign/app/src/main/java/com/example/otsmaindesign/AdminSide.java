package com.example.otsmaindesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AdminSide extends AppCompatActivity {
    Button customersdets;
    Button merchantdets;
    Button shopdets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_side);

        customersdets = findViewById(R.id.cus_dets);
        merchantdets = findViewById(R.id.mer_dets);
        shopdets = findViewById(R.id.shop_dets);
        customersdets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewAllDetails.class);
                i.putExtra("name", "customers");
                startActivity(i);
            }
        });
        merchantdets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewAllDetails.class);
                i.putExtra("name", "merchant");
                startActivity(i);
            }
        });
        shopdets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RestorentDetails.class);
                startActivity(i);
            }
        });
    }

    public void gotoreports(View view) {
        Intent i = new Intent(getApplicationContext(), Reports.class);
        startActivity(i);
    }
}
