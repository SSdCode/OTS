package com.example.otsmaindesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otsmaindesign.database.DatabaseHelper;
import com.example.otsmaindesign.otherclasses.DbBitmapUtility;

public class MerchantFoodInfo extends AppCompatActivity {
    TextView fdname, fdprice, fdabout, fdtype, restorent_address, restorent_name;
    Button remove_food;
    ImageView fdimage;
    String frEmail;
    Bitmap imagetostore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_food_info);


        fdabout = findViewById(R.id.tv_get_about1);
        fdname = findViewById(R.id.tv_get_name1);
        fdprice = findViewById(R.id.tv_get_price1);
        fdtype = findViewById(R.id.tv_get_type1);
        restorent_address = findViewById(R.id.tv_get_Address1);
        restorent_name = findViewById(R.id.tv_get_restorent_name1);
        fdimage = findViewById(R.id.get_fdimage1);
        remove_food = findViewById(R.id.btn_remove_food);


        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase database = helper.getReadableDatabase();

        Intent i = getIntent();
        final String username = i.getStringExtra("username");
        final String FName = i.getStringExtra("foodname");
        fdname.setText(FName);


//"CREATE TABLE Merchant_Shop_ITEMS(_id INTEGER PRIMARY KEY AUTOINCREMENT,ITEM_NAME VARCHAR(255),ITEM_TYPE VARCHAR(255),ITEM_PRICE VARCHAR(255),SHOP_EMAIL VARCHAR(255),ABOUT_ITEMS VARCHAR(255),image blob)";

        Cursor cursor = database.rawQuery("SELECT * FROM Merchant_Shop_ITEMS", null);

        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No data!", Toast.LENGTH_SHORT).show();
        } else {
            DbBitmapUtility convert = new DbBitmapUtility();
            while (cursor.moveToNext()) {

                if (FName.equals(cursor.getString(1))) {

                    String fprice = (cursor.getString(3));
                    String ftype = (cursor.getString(2));
                    String fabout = (cursor.getString(5));
                    frEmail = (cursor.getString(4));
                    byte[] image = cursor.getBlob(6);
                    imagetostore = convert.getImage(image);
                    fdimage.setImageBitmap(convert.getImage(image));
                    fdprice.setText(fprice);
                    fdtype.setText(ftype);
                    fdabout.setText(fabout);

                }
            }
            cursor.close();
        }

//"CREATE TABLE Merchant_Shop(_id INTEGER PRIMARY KEY AUTOINCREMENT,SHOPENAME VARCHAR(255),CITY VARCHAR(255),LANDMARK VARCHAR(255),ABOUT VARCHAR(255),EMAIL VARCHAR(255))"
        Cursor cursor1 = database.rawQuery("SELECT * FROM Merchant_Shop", null);

        if (cursor1.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No data!", Toast.LENGTH_SHORT).show();
        } else {

            while (cursor1.moveToNext()) {
                if (frEmail.equals(cursor1.getString(5))) {

                    String frName = (cursor1.getString(1));
                    String frAddress = (cursor1.getString(2));
                    String frAddress2 = (cursor1.getString(3));

                    restorent_name.setText(frName);
                    String add = frAddress + " ,  " + frAddress2;
                    restorent_address.setText(add);
                }
            }
            cursor1.close();
        }

        remove_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase database = helper.getReadableDatabase();
//Merchant_Shop_ITEMS(_id INTEGER PRIMARY KEY AUTOINCREMENT,ITEM_NAME VARCHAR(255),ITEM_TYPE VARCHAR(255),ITEM_PRICE VARCHAR(255),SHOP_EMAIL VARCHAR(255),ABOUT_ITEMS VARCHAR(255),image blob)";
                database.delete("Merchant_Shop_ITEMS", "ITEM_NAME = ?", new String[]{FName});

                Intent i = new Intent(getApplicationContext(), LoginMerchant.class);
                i.putExtra("username", username);
                startActivity(i);
                finish();

                Toast.makeText(getApplicationContext(), "Food Remove Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
