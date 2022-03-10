package com.example.otsmaindesign.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.otsmaindesign.database.DatabaseHelper;
import com.example.otsmaindesign.MainActivity;
import com.example.otsmaindesign.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    TextView cus_Name;
    TextView email;
    TextView moNo;
    TextView city;
    Button logout;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        logout = v.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        String name = getArguments().getString("name");
        db(v, name);
        return v;
    }

    private void db(View v, String name) {
        DatabaseHelper helper = new DatabaseHelper(getContext());
        SQLiteDatabase database = helper.getReadableDatabase();


//        merName.setText(Name);

        cus_Name = v.findViewById(R.id.cus_name);
        email = v.findViewById(R.id.cus_email);
        moNo = v.findViewById(R.id.cus_mono);
        city = v.findViewById(R.id.cus_city);

        Cursor cursor = database.rawQuery("SELECT * FROM AllUsers", null);

        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No data!", Toast.LENGTH_SHORT).show();
        } else {

            while (cursor.moveToNext()) {
                if (name.equals(cursor.getString(5))) {
                    String item = (cursor.getString(1));
                    String item2 = (cursor.getString(2));
                    String nme = " " + item + " " + item2;
                    cus_Name.setText(nme);
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
}
