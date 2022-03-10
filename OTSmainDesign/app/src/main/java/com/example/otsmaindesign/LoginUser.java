package com.example.otsmaindesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.otsmaindesign.fragments.AccountFragment;
import com.example.otsmaindesign.fragments.CardFragment;
import com.example.otsmaindesign.fragments.MenuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoginUser extends AppCompatActivity {
    public static Context contexOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        
        Intent i = getIntent();
        final String Name = i.getStringExtra("username");
        contexOfApplication = getApplicationContext();

        BottomNavigationView navigationView = findViewById(R.id.btm_main_id);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.btm_mnu_id) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", Name);
                    MenuFragment fragment = new MenuFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_id, fragment);
                    fragmentTransaction.commit();
                }

                if (id == R.id.btm_search_id) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", Name);
                    CardFragment fragment = new CardFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_id, fragment);
                    fragmentTransaction.commit();
                }
                if (id == R.id.btm_person_id) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", Name);
                    AccountFragment fragment = new AccountFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_id, fragment);
                    fragmentTransaction.commit();
                }
                return true;
            }
        });
        navigationView.setSelectedItemId(R.id.btm_mnu_id);
    }

    public static Context getContexOfApplication() {
        return contexOfApplication;
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
