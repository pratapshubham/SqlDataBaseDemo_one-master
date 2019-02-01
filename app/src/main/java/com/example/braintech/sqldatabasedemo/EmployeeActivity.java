package com.example.braintech.sqldatabasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {
    List<Employee> myList;
    SQLiteDatabase sqLiteDatabase;
    ListView listView;
    EmployeeAdapter employeeAdapter;
    Button update, delete;
    TextView textViewNorecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        listView = (ListView) findViewById(R.id.listViewEmployees);
        update = (Button) findViewById(R.id.buttonUpdateEmployee);
        textViewNorecord = (TextView) findViewById(R.id.textViewEmpty);
        displayEmployeeData();

    }

    public void displayEmployeeData() {
        myList = new ArrayList<>();
        sqLiteDatabase = openOrCreateDatabase("Employee", MODE_PRIVATE, null);//build the database connection;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from employees", null);


        System.out.println("Total Count of Cursor------->" + cursor.getCount());
        if (cursor.getCount() < 1) {
            textViewNorecord.setText("No Record Found");
        }
        if (cursor.moveToFirst()){
            do {
                myList.add(new Employee(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        employeeAdapter = new EmployeeAdapter(this, R.layout.list_layout_employee, myList, sqLiteDatabase);
        listView.setAdapter(employeeAdapter);

    }
}
