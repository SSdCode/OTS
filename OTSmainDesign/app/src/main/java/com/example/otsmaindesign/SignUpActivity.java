package com.example.otsmaindesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.otsmaindesign.database.DatabaseHelper;

public class SignUpActivity extends AppCompatActivity {
    Button btn;
    EditText fname, lname, city, email, mo, pass1, pass2;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        casting();
        btn = (Button) findViewById(R.id.btnSub);
        final CheckBox CBViewHidePass = (CheckBox) findViewById(R.id.cbox);

        CBViewHidePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pass1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    pass2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    pass1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pass2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Firstname = fname.getText().toString().trim();
                String Lastname = lname.getText().toString().trim();
                String Usertype = spinner.getSelectedItem().toString().trim();
                String City = city.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Mono = mo.getText().toString().trim();
                String Password = pass1.getText().toString().trim();
                String Repassword = pass2.getText().toString().trim();

                if (Firstname.isEmpty() || Lastname.isEmpty() || City.isEmpty() || Email.isEmpty() || Mono.isEmpty() || Password.isEmpty() || Repassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter all details!", Toast.LENGTH_SHORT).show();
                } else if (!Password.equals(Repassword)) {
                    Toast.makeText(getApplicationContext(), "Password not match!", Toast.LENGTH_SHORT).show();
                } else if (Email.equals("Admin")) {
                    Toast.makeText(getApplicationContext(), "Email is taken!", Toast.LENGTH_SHORT).show();
                } else {

                    int ut;
                    if (Usertype.equals("Login as Customer")) {
                        ut = 1;
                    } else {
                        ut = 2;
                    }

                    Boolean ans = checkUser(Email, Mono);
                    if (ans) {
                        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                        SQLiteDatabase database = helper.getWritableDatabase();

                        ContentValues values = new ContentValues();
                        values.put("FNAME", Firstname);
                        values.put("LNAME", Lastname);
                        values.put("UTYPE", ut);
                        values.put("CITY", City);
                        values.put("EMAIL", Email);
                        values.put("MONO", Mono);
                        values.put("PASSWORD", Password);
                        database.insert("AllUsers", null, values);
                        Toast.makeText(getApplicationContext(), "User created!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "User Already Register!", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    private Boolean checkUser(String email, String mono) {
//AllUsers(_id INTEGER PRIMARY KEY AUTOINCREMENT,FNAME VARCHAR(255),LNAME VARCHAR(255),UTYPE INTEGER,CITY VARCHAR(255),EMAIL VARCHAR(255),MONO VARCHAR(255),PASSWORD VARCHAR(255))";
        DatabaseHelper helper = new DatabaseHelper(this);
        final SQLiteDatabase database = helper.getReadableDatabase();

        String readdata = "SELECT * FROM AllUsers";
        Cursor cursor = database.rawQuery(readdata, null);

        Boolean ans = true;
        if (cursor.getCount() == 0) {
        } else {
            cursor.moveToFirst();
            do {
                String name = cursor.getString(5);
                String mobno = cursor.getString(6);
                if (email.equals(name) || mono.equals(mobno)) {
                    ans = false;
                    return ans;
                }
            } while (cursor.moveToNext());
        }
        return ans;
    }

    private void casting() {
        fname = findViewById(R.id.etFname);
        lname = findViewById(R.id.etLname);
        city = findViewById(R.id.etCity);
        email = findViewById(R.id.etEmail);
        mo = findViewById(R.id.etMno);
        pass1 = findViewById(R.id.etPass1);
        pass2 = findViewById(R.id.etPass2);
        spinner = findViewById(R.id.uType);
    }
}
