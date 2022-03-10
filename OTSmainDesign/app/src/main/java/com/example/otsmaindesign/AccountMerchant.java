package com.example.otsmaindesign;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otsmaindesign.database.DatabaseHelper;

public class AccountMerchant extends AppCompatActivity {
    TextView mer_Name;
    TextView email;
    TextView moNo;
    TextView city;
    Button add_shop_dets;
    private ImageView imageView;
    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;
    private Bitmap imageToStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_merchant);

        db();

        add_shop_dets = findViewById(R.id.btn_addshopdets);
        Intent i = getIntent();
        final String Name = i.getStringExtra("uname");
        add_shop_dets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase database = helper.getReadableDatabase();
//Merchant_Shop(_id INTEGER PRIMARY KEY AUTOINCREMENT,SHOPENAME VARCHAR(255),CITY VARCHAR(255),LANDMARK VARCHAR(255),ABOUT VARCHAR(255),EMAIL VARCHAR(255))
                Cursor cursor = database.rawQuery("SELECT * FROM Merchant_Shop", null);

                if (cursor.getCount() == 0) {
                    Intent i = new Intent(getApplicationContext(), AddShopDetails.class);
                    i.putExtra("uname", Name);
                    startActivity(i);
                } else {
                    Boolean ans = true;
                    while (cursor.moveToNext()) {
                        String email = cursor.getString(5);
                        if (email.equals(Name)) {
                            ans = false;
                            Toast.makeText(getApplicationContext(), "Details Already Inserted", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (ans) {
                        Intent i = new Intent(getApplicationContext(), AddShopDetails.class);
                        i.putExtra("uname", Name);
                        startActivity(i);
                    }
                }
            }


        });
        try {
            imageView = findViewById(R.id.image);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void chooseImage(View v) {
        try {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(i, PICK_IMAGE_REQUEST);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void db() {
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase database = helper.getReadableDatabase();

        Intent i = getIntent();
        String Name = i.getStringExtra("uname");
//        merName.setText(Name);

        mer_Name = findViewById(R.id.mer_name);
        email = findViewById(R.id.mer_email);
        moNo = findViewById(R.id.mer_mono);
        city = findViewById(R.id.mer_city);
//        email.setText(Name);

        Cursor cursor = database.rawQuery("SELECT * FROM AllUsers", null);

        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No data!", Toast.LENGTH_SHORT).show();
        } else {

            while (cursor.moveToNext()) {
                if (Name.equals(cursor.getString(5))) {
                    String item = (cursor.getString(1));
                    String item2 = (cursor.getString(2));
                    String name = "" + item + " " + item2;
                    mer_Name.setText(name);
                    String item3 = (cursor.getString(4));
                    city.setText(item3);
                    String item5 = (cursor.getString(5));
                    email.setText(item5);
                    String item4 = (cursor.getString(6));
                    moNo.setText(item4);
                    break;
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);
                imageView.setImageBitmap(imageToStore);
            } else {
                Toast.makeText(getApplicationContext(), "Image Cant Show!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
