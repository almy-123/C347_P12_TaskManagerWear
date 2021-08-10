package com.myapplicationdev.android.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    ListView lv;
    ArrayList<Task> arrayList;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        lv = findViewById(R.id.lv);
        arrayList = new ArrayList<Task>();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayList.clear();

        DBHelper dbh = new DBHelper(MainActivity.this);
        arrayList.addAll(dbh.getTasks());
        dbh.close();
        arrayAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(arrayAdapter);
    }
}