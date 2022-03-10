package com.example.otsmaindesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.otsmaindesign.database.DatabaseHelper;

import java.util.ArrayList;

public class ViewAllDetails extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_details);

        listView = (ListView) findViewById(R.id.lv_udet);

        Intent i = getIntent();
        final String Name = i.getStringExtra("name");
        if (Name.equals("customers")) {
            DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
            SQLiteDatabase database = helper.getReadableDatabase();

            String q = "SELECT * FROM AllUsers WHERE UTYPE = 1;";
            Cursor c1 = database.rawQuery(q, null);
            startManagingCursor(c1);
            ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.layout, c1, new String[]{"FNAME", "LNAME", "UTYPE", "CITY", "EMAIL", "MONO", "PASSWORD"}, new int[]{R.id.fname, R.id.lname, R.id.uType, R.id.city, R.id.email, R.id.mono, R.id.password});
            registerForContextMenu(listView);
            listView.setAdapter(adapter);
        }
        if (Name.equals("merchant")){
            DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
            SQLiteDatabase database = helper.getReadableDatabase();

            String q = "SELECT * FROM AllUsers WHERE UTYPE = 2;";
            Cursor c1 = database.rawQuery(q, null);
            startManagingCursor(c1);
            ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.layout, c1, new String[]{"FNAME", "LNAME", "UTYPE", "CITY", "EMAIL", "MONO", "PASSWORD"}, new int[]{R.id.fname, R.id.lname, R.id.uType, R.id.city, R.id.email, R.id.mono, R.id.password});
            registerForContextMenu(listView);
            listView.setAdapter(adapter);
        }
    }
}
