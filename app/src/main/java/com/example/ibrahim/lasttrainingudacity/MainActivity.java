package com.example.ibrahim.lasttrainingudacity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ibrahim.lasttrainingudacity.data.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
    }

    public void startConveratuon (View view) {
        etName=findViewById (R.id.etName);
            SharedPrefManager.getInstance (this).saveNamesOfUsers (etName.getText ().toString ());
            Intent intent=new Intent(this, MainActivity2.class);
            startActivity(intent);

    }
}
