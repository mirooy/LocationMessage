package com.example.cs160_sp18.prog3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;



public class Main extends AppCompatActivity {

    private Button button;
    private Toolbar toolbars;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);


        input = findViewById(R.id.inputID);
        toolbars = findViewById(R.id.toolbarlayout);

        setSupportActionBar(toolbars);
        getSupportActionBar().setTitle("Choose the ID");


        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = input.getText().toString();
                if(!id.equals("")) {
                    Intent intent = new Intent(Main.this, recyclerview.class);
                    intent.putExtra("username", id);
                    startActivity(intent);

                }
            }
        });

    }

}
