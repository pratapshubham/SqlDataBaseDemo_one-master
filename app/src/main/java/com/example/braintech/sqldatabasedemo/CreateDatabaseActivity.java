package com.example.braintech.sqldatabasedemo;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateDatabaseActivity extends AppCompatActivity {
EditText username,mobile,address;
Button AddUser;
String User,Phone,Address;
SQLiteDatabase sqLiteDatabase;
TextView viewAllUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_database);
        setTitle("Add User");
        getId();
        sqLiteDatabase = openOrCreateDatabase("Employee",MODE_PRIVATE,null);//build the database connection;

       createTable();

        AddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    inserData();
                    username.getText().clear();
                    mobile.getText().clear();
                    address.getText().clear();
            }
        });
        viewAllUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateDatabaseActivity.this,EmployeeActivity.class));
            }
        });
    }

    public void createTable()
    {
        sqLiteDatabase.execSQL("create table if not exists employees (id INTEGER PRIMARY KEY"+
                ",Username varchar(100),Mobile INTEGER,ADDRESS varchar(100))");
    }

    public void inserData()
    {

        User = username.getText().toString();
        Phone = mobile.getText().toString();
        Address = address.getText().toString();
        if (Validate())
        {
            String insertSQL = "INSERT INTO employees \n" +
                    "(Username, Mobile, ADDRESS)\n" +
                    "VALUES \n" +
                    "(?, ?, ?);";
            sqLiteDatabase.execSQL(insertSQL,new String[]{User, Phone, Address});
            /*AlertDialog alertDialog = null;
            alertDialog = new AlertDialog.Builder(CreateDatabaseActivity.this).create();
            alertDialog.setTitle("Message");
            alertDialog.setMessage("Employee Added Successfully");
            alertDialog.setCancelable(true);
            alertDialog.show();*/
            Toast.makeText(this,"Data Added Successfully",Toast.LENGTH_SHORT).show();
            //sqLiteDatabase.close();
        }
    }

    public void getId()
    {
        username = (EditText)findViewById(R.id.editTextUsername);
        mobile = (EditText)findViewById(R.id.editTextMobile);
        address = (EditText)findViewById(R.id.editTextAddress);
        AddUser = (Button)findViewById(R.id.btnAdduser);
        viewAllUser = (TextView)findViewById(R.id.textViewAllUser);
    }
    public Boolean Validate()
    {
        if (User.isEmpty())
        {
            Toast.makeText(this,"Enter UserName",Toast.LENGTH_SHORT).show();
            return  false;
        }
        else if (Phone.isEmpty())
        {
            Toast.makeText(this,"Enter Phone",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (Address.isEmpty())
        {
            Toast.makeText(this,"Enter Address",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
