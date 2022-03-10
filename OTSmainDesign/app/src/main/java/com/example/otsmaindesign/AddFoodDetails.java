package com.example.otsmaindesign;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.otsmaindesign.database.DatabaseHelper;
import com.example.otsmaindesign.otherclasses.DbBitmapUtility;

public class AddFoodDetails extends AppCompatActivity {
    Button submit;
    EditText fdname, fddesc, fdprice;
    Spinner fdtype;
    private ImageView imageView;
    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;
    private Bitmap imageToStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_details);

        fdname = findViewById(R.id.fd_name);
        fddesc = findViewById(R.id.fd_description);
        fdtype = findViewById(R.id.fd_type);
        fdprice = findViewById(R.id.fd_price);
        submit = findViewById(R.id.save_food);

        imageView = findViewById(R.id.food_image);
        /*---------------------------For Image-------------------------------*/
        imageToStore=BitmapFactory.decodeResource(getResources(),R.drawable.cook_tp);
        /*-------------------------------------------------------------------*/
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = fdname.getText().toString();
                String fdesc = fddesc.getText().toString();
                String fprice = fdprice.getText().toString();
                String ftype = fdtype.getSelectedItem().toString();

                if (fname.isEmpty() || fdesc.isEmpty() || fprice.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill all details", Toast.LENGTH_SHORT).show();
                } else if (imageView.getDrawable() == null && imageToStore == null) {
                    Toast.makeText(getApplicationContext(), "First select Image", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = getIntent();
                    final String Nam = i.getStringExtra("username");
//"CREATE TABLE Merchant_Shop_ITEMS(_id INTEGER PRIMARY KEY AUTOINCREMENT,ITEM_NAME VARCHAR(255),ITEM_TYPE VARCHAR(255),ITEM_PRICE VARCHAR(255),SHOP_EMAIL VARCHAR(255),ABOUT_ITEMS VARCHAR(255),image blob)";

                    DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                    SQLiteDatabase database = helper.getWritableDatabase();

//                    helper.storeImage(new ModelClass(imageToStore));
                    DbBitmapUtility convert = new DbBitmapUtility();

                    ContentValues values = new ContentValues();
                    values.put("ITEM_NAME", fname);
                    values.put("ITEM_TYPE", ftype);
                    values.put("ITEM_PRICE", fprice);
                    values.put("SHOP_EMAIL", Nam);
                    values.put("ABOUT_ITEMS", fdesc);
                    values.put("image", convert.getBytes(imageToStore));
                    database.insert("Merchant_Shop_ITEMS", null, values);
                    Toast.makeText(getApplicationContext(), "Data Inserted.", Toast.LENGTH_SHORT).show();
                    Intent i2 = new Intent(getApplicationContext(), LoginMerchant.class);
                    i2.putExtra("username", Nam);
                    startActivity(i2);
                }
            }
        });
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
