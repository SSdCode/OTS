package com.example.otsmaindesign;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    Context context;
    private static final String myDb = "MyDb";
    private static final int version = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, myDb, null, version);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createtable1 = "CREATE TABLE AllUsers(_id INTEGER PRIMARY KEY AUTOINCREMENT,FNAME VARCHAR(255),LNAME VARCHAR(255),UTYPE INTEGER,CITY VARCHAR(255),EMAIL VARCHAR(255),MONO VARCHAR(255),PASSWORD VARCHAR(255))";
        db.execSQL(createtable1);

        String createtable2 = "CREATE TABLE Merchant_Shop(_id INTEGER PRIMARY KEY AUTOINCREMENT,SHOPENAME VARCHAR(255),CITY VARCHAR(255),LANDMARK VARCHAR(255),ABOUT VARCHAR(255),EMAIL VARCHAR(255))";
        db.execSQL(createtable2);

        String createtable3 = "CREATE TABLE Merchant_Shop_ITEMS(_id INTEGER PRIMARY KEY AUTOINCREMENT,ITEM_NAME VARCHAR(255),ITEM_TYPE VARCHAR(255),ITEM_PRICE VARCHAR(255),SHOP_EMAIL VARCHAR(255),ABOUT_ITEMS VARCHAR(255),image blob)";
        db.execSQL(createtable3);

        String createtable4 = "CREATE TABLE MyCard(_id INTEGER PRIMARY KEY AUTOINCREMENT,ITEM_NAME VARCHAR(255),QTY VARCHAR(255),CUS_EMAIL VARCHAR(255),image blob,DATE VARCHAR(255),PRICE VARCHAR(255))";
        db.execSQL(createtable4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<ModelClass> getAllImagesData() {
        try {
            SQLiteDatabase database = this.getReadableDatabase();
            ArrayList<ModelClass> objectModelClassList = new ArrayList<>();

            Cursor cursor = database.rawQuery("select * from Merchant_Shop", null);
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    String nameOfImage = cursor.getString(1);
                    objectModelClassList.add(new ModelClass(nameOfImage));
                }
                return objectModelClassList;
            } else {
                Message.message(context, "No values in database");
                return null;
            }
        } catch (Exception e) {
            Message.message(context, e.getMessage() + e);
            return null;
        }
    }


    public ArrayList<ModelClass> getAllCartData() {
//        try {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<ModelClass> objectModelClassList = new ArrayList<>();
//"CREATE TABLE MyCard(_id INTEGER PRIMARY KEY AUTOINCREMENT,ITEM_NAME VARCHAR(255),QTY VARCHAR(255),CUS_EMAIL VARCHAR(255),image blob,DATE VARCHAR(255),PRICE VARCHAR(255))";
        Cursor cursor = database.rawQuery("select * from MyCard", null);
        if (cursor.getCount() != 0) {
            cursor.moveToPosition(-1);

            while (cursor.moveToNext()) {
                Integer amtt = 0;
                Integer qtyy = 0;
                Integer total_amt = 0;
                String nameOfImage = cursor.getString(1);
                String amt = cursor.getString(6);
                String qty = cursor.getString(2);
                byte[] imageBytes = cursor.getBlob(4);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                amtt = Integer.parseInt(amt.trim());
                qtyy = Integer.parseInt(qty.trim());
                total_amt = amtt * qtyy;
                objectModelClassList.add(new ModelClass(bitmap, nameOfImage, total_amt));
            }
            cursor.close();
            return objectModelClassList;
        } else {
            Message.message(context, "No Items In Cart");
            return null;
        }
/*        } catch (Exception e) {
            Message.message(context, e.getMessage() + e);
            return null;
        }       */
    }

    public ArrayList<ModelClass> getAllMerchantShopData() {
        try {
            SQLiteDatabase database = this.getReadableDatabase();
            ArrayList<ModelClass> objectModelClassList = new ArrayList<>();

            Cursor cursor = database.rawQuery("select * from Merchant_Shop", null);
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    String nameOfImage = cursor.getString(1);
                    objectModelClassList.add(new ModelClass(nameOfImage));

                }
                return objectModelClassList;
            } else {
                Message.message(context, "No values in database");
                return null;
            }
        } catch (Exception e) {
            Message.message(context, e.getMessage() + e);
            return null;
        }
    }

    public ArrayList<ModelClass> getAllFoodData() {

        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<ModelClass> objectModelClassList = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from Merchant_Shop_ITEMS", null);
        Cursor cursor1 = database.rawQuery("select * from Merchant_Shop", null);

        if (cursor.getCount() != 0 && cursor1.getCount() != 0) {
            cursor.moveToPosition(-1);

            while (cursor.moveToNext()) {

                String spEmail = cursor.getString(4);

                cursor1.moveToPosition(-1);
                String spDets = "Restaurant Not Found!";
                while (cursor1.moveToNext()) {

                    if (spEmail.equals(cursor1.getString(5))) {

                        String spCity = cursor1.getString(2);
                        String landmark = cursor1.getString(3);
                        String spName = cursor1.getString(1);
                        spDets = " " + spName + "    -    " + spCity + "    -    " + landmark;
                    }
                }
                String nameOfImage = cursor.getString(1);
                byte[] imageBytes = cursor.getBlob(6);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                objectModelClassList.add(new ModelClass(bitmap, nameOfImage, spDets));
            }
            return objectModelClassList;
        } else {
            Message.message(context, "No values in database");
            return null;
        }
    }

    public ArrayList<ModelClass> getMyFoodData(String email) {

        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<ModelClass> objectModelClassList = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from Merchant_Shop_ITEMS", null);
        Cursor cursor1 = database.rawQuery("select * from Merchant_Shop", null);

        if (cursor.getCount() != 0 && cursor1.getCount() != 0) {
            cursor.moveToPosition(-1);

            while (cursor.moveToNext()) {

                String spEmail = cursor.getString(4);
                if (spEmail.equals(email)) {
                    cursor1.moveToPosition(-1);
                    String spDets = "Restaurant Not Found!";
                    while (cursor1.moveToNext()) {

                        if (spEmail.equals(cursor1.getString(5))) {

                            String spCity = cursor1.getString(2);
                            String landmark = cursor1.getString(3);
                            String spName = cursor1.getString(1);
                            spDets = " " + spName + "    -    " + spCity + "    -    " + landmark;
                        }
                    }
                    String nameOfImage = cursor.getString(1);
                    byte[] imageBytes = cursor.getBlob(6);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    objectModelClassList.add(new ModelClass(bitmap, nameOfImage, spDets));
                }
            }
            return objectModelClassList;
        } else {
            Message.message(context, "No values in database");
            return null;
        }
    }
}
