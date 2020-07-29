package com.example.otsmaindesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Paytm_payment extends AppCompatActivity {
    TextView price;
    EditText mono, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm_payment);

        mono = findViewById(R.id.ptmno);
        name = findViewById(R.id.ptmname);
        price = findViewById(R.id.tvfdPrice);
        Intent i = getIntent();
        final String total_Amt = i.getStringExtra("total_amt");

        price.setText("Total Amount :" + total_Amt);
    }

    public void btn_pay(View view) {
        if (mono.getText().toString().isEmpty() || name.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Payment Successful", Toast.LENGTH_SHORT).show();
        }
    }
}
