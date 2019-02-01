package com.example.braintech.sqldatabasedemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Employee> {
    Context context;
    int listLayoutRes;
    List<Employee> employeeList;
    SQLiteDatabase sqLiteDatabase;

    public EmployeeAdapter(Context context , int listLayoutRes, List<Employee> employeeList, SQLiteDatabase sqLiteDatabase) {
        super(context, listLayoutRes, employeeList);
        this.context = context;
        this.listLayoutRes = listLayoutRes;
        this.employeeList = employeeList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(listLayoutRes, null);


        final Employee employee = employeeList.get(position);

        TextView textViewUserName = view.findViewById(R.id.textViewusername);
        TextView textViewMobile = view.findViewById(R.id.textViewmobilenumber);
        TextView textViewAdd = view.findViewById(R.id.textViewaddress);


        textViewUserName.setText(employee.getUser());
        textViewMobile.setText(employee.getPhone());
        textViewAdd.setText(employee.getAddress());

        Button btn_delete = view.findViewById(R.id.buttonDeleteEmployee);
        Button btn_update = view.findViewById(R.id.buttonUpdateEmployee);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee(employee);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletedata(employee);

            }
        });

        return view;
    }
    private void updateEmployee(final Employee employee) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_update_data, null);
        builder.setView(view);
        final EditText editTextUserupdate = view.findViewById(R.id.editTextUsernameupdate);
        final EditText editTextMobileupdate = view.findViewById(R.id.editTextMobileupdate);
        final EditText editTextAddressupdate = view.findViewById(R.id.editTextAddressupdate);


        editTextUserupdate.setText(employee.getUser());
        editTextMobileupdate.setText(employee.getPhone());
        editTextAddressupdate.setText(employee.getAddress());

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.btnUpdation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = editTextUserupdate.getText().toString();
                String Mobile = editTextMobileupdate.getText().toString();
                String ADDRESS = editTextAddressupdate.getText().toString();

                if (Username.isEmpty()) {
                    editTextUserupdate.setError("Enter UserName");
                    editTextUserupdate.requestFocus();
                    return;
                }

                if (Mobile.isEmpty()) {
                    editTextMobileupdate.setError("Enter Phone");
                    editTextMobileupdate.requestFocus();
                    return;
                }

                if (ADDRESS.isEmpty()) {
                    editTextAddressupdate.setError("Enter Address");
                    editTextAddressupdate.requestFocus();
                    return;
                }

                String sql =  "UPDATE  employees \n" +
                        "SET Username = ?, \n" +
                        "Mobile = ?, \n" +
                        "ADDRESS = ?"+
                        "WHERE id = ?;\n";


                //System.out.println("Da ta---------->"+Username);
                //System.out.println("Data---------->"+Mobile);
                //System.out.println("Data---------->"+ADDRESS);

                sqLiteDatabase.execSQL(sql, new String[]{Username, Mobile, ADDRESS,
                        String.valueOf(employee.getId())});
                Toast.makeText(context, "Employee Updated", Toast.LENGTH_SHORT).show();
                reloadEmployeesFromDatabase();

                dialog.dismiss();
            }
        });
    }
    private void reloadEmployeesFromDatabase() {
        Cursor cursorEmployees = sqLiteDatabase.rawQuery("SELECT * FROM employees", null);
        if (cursorEmployees.moveToFirst()) {
            employeeList.clear();
           do
            {
                employeeList.add(new Employee(cursorEmployees.getInt(0),cursorEmployees.getString(1),
                        cursorEmployees.getString(2),cursorEmployees.getString(3)));
            } while(cursorEmployees.moveToNext());
        }
        cursorEmployees.close();
        notifyDataSetChanged();
    }
    private void deletedata(final Employee employee)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String sql = "DELETE FROM employees WHERE id = ?";
                sqLiteDatabase.execSQL(sql, new Integer[]{employee.getId()});
                Toast.makeText(getContext(),"Item Deleted",Toast.LENGTH_SHORT).show();
                //reloadEmployeesFromDatabase();
                ((EmployeeActivity)context).displayEmployeeData();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
