package com.example.otsmaindesign;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddShopDetails extends AppCompatActivity {
    Button submit;
    EditText sname, scity, slandmark, sabout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop_details);

        submit = findViewById(R.id.save_shop);
        sname = findViewById(R.id.sp_name);
        scity = findViewById(R.id.sp_city);
        slandmark = findViewById(R.id.sp_landmark);
        sabout = findViewById(R.id.sp_about);
        Intent i = getIntent();
        final String Nam = i.getStringExtra("uname");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = sname.getText().toString();
                String city = scity.getText().toString();
                String landm = slandmark.getText().toString();
                String about = sabout.getText().toString();

                if (name.isEmpty() || city.isEmpty() || landm.isEmpty() || about.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                    SQLiteDatabase database = helper.getWritableDatabase();

//                    helper.storeImage(new ModelClass(name));
//                    helper.storeImage(new ModelClass(name, imageToStore));
//Merchant_Shop(_id INTEGER PRIMARY KEY AUTOINCREMENT,SHOPENAME VARCHAR(255),CITY VARCHAR(255),LANDMARK VARCHAR(255),ABOUT VARCHAR(255),EMAIL VARCHAR(255))";
                    ContentValues values = new ContentValues();
                    values.put("SHOPENAME", name);
                    values.put("CITY", city);
                    values.put("LANDMARK", landm);
                    values.put("ABOUT", about);
                    values.put("EMAIL", Nam);
                    database.insert("Merchant_Shop", null, values);
                    Toast.makeText(getApplicationContext(), "Data Inserted.", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getApplicationContext(), AccountMerchant.class);
                    i.putExtra("uname", Nam);
                    startActivity(i);
                }
            }
        });

    }


}
