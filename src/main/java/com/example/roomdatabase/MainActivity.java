package com.example.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.roomdatabase.database.AppDatabase;
import com.example.roomdatabase.entity.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText etName,etEmail,etPhone;
    Button btnInsert,btnRead;

    User user;
    List<User> userList;
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        btnInsert = findViewById(R.id.btn_insert);
        btnRead = findViewById(R.id.btn_read);

        listView = findViewById(R.id.list_view);

        database = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"Jivan.db").allowMainThreadQueries().build();

        readRecord();

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();

                insertRecord(name,email,phone);

            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                readRecord();
            }
        });
        }

    private void insertRecord(String name, String email, String phone) {

        user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);

        database.userDAO().insertRecord(user);

        Toast.makeText(this, "Record Inserted Succesfully", Toast.LENGTH_SHORT).show();

        readRecord();
    }


    private void readRecord() {

        userList = database.userDAO().readRecord();

        Toast.makeText(MainActivity.this, ""+ userList.size(), Toast.LENGTH_SHORT).show();

        if (userList != null && userList.size()>0){

            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, userList);
            listView.setAdapter(adapter);
        }
    }
}