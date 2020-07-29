package com.example.otsmaindesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button RegLinkC;
    EditText uname;
    EditText pass;
    Button btnLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RegLinkC =  findViewById(R.id.RegLink);
        btnLog =  findViewById(R.id.btnLogin);
        uname = findViewById(R.id.etUname);
        pass = findViewById(R.id.etPassMain);

        final CheckBox CBViewHidePass = (CheckBox) findViewById(R.id.checkBox);

        CBViewHidePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        RegLinkC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = uname.getText().toString().trim();
                final String userpassword = pass.getText().toString().trim();
                if (username.isEmpty() || userpassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username or passwords are empty", Toast.LENGTH_SHORT).show();
                } else if (username.equals("Admin") & userpassword.equals("Admin")) {
                    Intent i = new Intent(MainActivity.this, AdminSide.class);
                    startActivity(i);
                } else {
                    DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                    SQLiteDatabase database = helper.getReadableDatabase();

                    String[] columns = {"EMAIL", "PASSWORD"};
                    String[] cValues = {username, userpassword};
                    Cursor cursor = database.query("AllUsers", columns, "EMAIL = ? AND PASSWORD = ?", cValues, null, null, null);
                    if (cursor != null) {
                        if (cursor.moveToFirst()) {

                            String column[] = {"UTYPE"};
                            Cursor c = database.query("AllUsers", column, "EMAIL=?", new String[]{username}, null, null, null);

                            if (c != null && c.moveToFirst()) {
                                String res = c.getString(c.getColumnIndex("UTYPE"));
                                if (res.equals("1")) {
                                    Intent i = new Intent(getApplicationContext(), LoginUser.class);
                                    i.putExtra("username", username);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Intent i = new Intent(getApplicationContext(), LoginMerchant.class);
                                    i.putExtra("username", username);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Enter valid details!", Toast.LENGTH_SHORT).show();
                        }


                    }//if close
                }
            }
        });
    }
}
