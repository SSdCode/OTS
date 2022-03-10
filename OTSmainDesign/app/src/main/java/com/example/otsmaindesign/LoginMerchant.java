package com.example.otsmaindesign;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.otsmaindesign.adapter.FoodRVAdapter;
import com.example.otsmaindesign.database.DatabaseHelper;

public class LoginMerchant extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    Button add_food_dets;
    RecyclerView recyclerView;
    FoodRVAdapter foodRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_merchant);

        add_food_dets = findViewById(R.id.btn_addfooddets);

        Intent i = getIntent();
        final String Nam = i.getStringExtra("username");

        recyclerView = findViewById(R.id.self_FoodRV);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        foodRVAdapter = new FoodRVAdapter(databaseHelper.getMyFoodData(Nam));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(foodRVAdapter);

        

        foodRVAdapter.setOnItemClickListener(new FoodRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String result = foodRVAdapter.result(position);
                Intent i = new Intent(getApplicationContext(), MerchantFoodInfo.class);
                i.putExtra("foodname", result);
                i.putExtra("username", Nam);
                startActivity(i);
            }
        });
        add_food_dets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*---------------------------------Check if shop details exist or not--------------------------------------*/

                DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase database = helper.getReadableDatabase();
                Cursor cursor = database.rawQuery("select * from Merchant_Shop", null);

                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Not Any Shop Details Found!", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean b = false;
                    while (cursor.moveToNext()) {
                        if (Nam.equals(cursor.getString(5))) {
                            b = true;
                            Intent i = new Intent(getApplicationContext(), AddFoodDetails.class);
                            i.putExtra("username", Nam);
                            startActivity(i);
                        }
                    }
                    if (!b) {
                        Toast.makeText(getApplicationContext(), "First add your shop details", Toast.LENGTH_SHORT).show();
                    }
                }
                /*---------------------------------------------------------------------------------------------------------*/
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.merchant_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_merchant_account_id:
                Intent intent = new Intent(getApplicationContext(), AccountMerchant.class);
                Intent i = getIntent();
                final String Nam = i.getStringExtra("username");
                intent.putExtra("uname", Nam);
                startActivity(intent);
                return true;
            default:
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
                return true;
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to exit?").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
