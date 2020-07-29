package com.example.otsmaindesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FoodInfo extends AppCompatActivity {
    TextView fdname, fdpricetv, fdabout, fdtype, restorent_address, restorent_name;
    Button add_to_card;
    ImageView fdimage;
    String frEmail;
    EditText qty;
    Bitmap imagetostore;

    String fdPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);

        fdabout = findViewById(R.id.tv_get_about);
        fdname = findViewById(R.id.tv_get_name);
        fdpricetv = findViewById(R.id.tv_get_price);
        fdtype = findViewById(R.id.tv_get_type);
        restorent_address = findViewById(R.id.tv_get_Address);
        restorent_name = findViewById(R.id.tv_get_restorent_name);
        fdimage = findViewById(R.id.get_fdimage);
        add_to_card = findViewById(R.id.btn_addtocard);
        qty = findViewById(R.id.et_qty);

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
                    fdpricetv.setText(fprice);
                    fdtype.setText(ftype);
                    fdabout.setText(fabout);
                    fdPrice = fprice;
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


        add_to_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String qty1 = qty.getText().toString();
                if (qty1.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "First enter No of plates!", Toast.LENGTH_SHORT).show();
                } else if (qty1.equals("0")) {
                    Toast.makeText(getApplicationContext(), "Minimum no of plates 1!", Toast.LENGTH_SHORT).show();
                } else {

                    Date c = Calendar.getInstance().getTime();
                    System.out.println("Current time => " + c);

                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c);

                    DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                    SQLiteDatabase database = helper.getWritableDatabase();
                    DbBitmapUtility convert = new DbBitmapUtility();
//"CREATE TABLE MyCard(_id INTEGER PRIMARY KEY AUTOINCREMENT,ITEM_NAME VARCHAR(255),QTY VARCHAR(255),CUS_EMAIL VARCHAR(255),image blob,DATE VARCHAR(255),PRICE VARCHAR(255))";
                    ContentValues values = new ContentValues();
                    values.put("ITEM_NAME", FName);
                    values.put("QTY", qty1);
                    values.put("CUS_EMAIL", username);
                    values.put("image", convert.getBytes(imagetostore));
                    values.put("DATE", formattedDate);
                    values.put("PRICE", fdPrice);
                    database.insert("MyCard", null, values);
                    Toast.makeText(getApplicationContext(), "Item Added To Card", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
