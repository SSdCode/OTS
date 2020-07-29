package com.example.otsmaindesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CartItemPayment extends AppCompatActivity {
    Button remove_from_cart, paytm, cod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_item_payment);

        paytm = findViewById(R.id.btn_paytm);
        cod = findViewById(R.id.btn_cod);


        remove_from_cart = findViewById(R.id.btn_remove_from_cart);
        Intent i = getIntent();
        final String username = i.getStringExtra("username");
        final String FName = i.getStringExtra("food_name");
        final String total_Amt = i.getStringExtra("total_amt");
        remove_from_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase database = helper.getReadableDatabase();

                database.delete("MyCard", "ITEM_NAME = ? AND CUS_EMAIL = ?", new String[]{FName, username});
                Intent i = new Intent(getApplicationContext(), LoginUser.class);
                i.putExtra("username", username);
                startActivity(i);
                finish();
                Toast.makeText(getApplicationContext(), "Food Remove Successful", Toast.LENGTH_SHORT).show();
            }
        });
        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Paytm_payment.class);
                i.putExtra("username", username);
                i.putExtra("total_amt", total_Amt);
                startActivity(i);
            }
        });
        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
