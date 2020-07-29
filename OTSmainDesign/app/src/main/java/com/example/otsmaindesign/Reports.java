package com.example.otsmaindesign;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Reports extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        listView = findViewById(R.id.li);

        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase database = helper.getReadableDatabase();

        String q = "SELECT * FROM MyCard;";
        Cursor c1 = database.rawQuery(q, null);
        startManagingCursor(c1);
        //"CREATE TABLE MyCard(_id INTEGER PRIMARY KEY AUTOINCREMENT,ITEM_NAME VARCHAR(255),QTY VARCHAR(255),CUS_EMAIL VARCHAR(255),image blob,DATE VARCHAR(255),PRICE VARCAHR(255))";
        ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.report_layout, c1, new String[]{"DATE", "CUS_EMAIL", "ITEM_NAME", "QTY", "PRICE"}, new int[]{R.id.TvDate, R.id.TvId, R.id.TvNAME, R.id.TvQTY, R.id.TvPrice});
        registerForContextMenu(listView);
        listView.setAdapter(adapter);
    }
}
