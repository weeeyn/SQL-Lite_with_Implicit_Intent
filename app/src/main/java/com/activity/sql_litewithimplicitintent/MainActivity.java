package com.activity.sql_litewithimplicitintent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    EditText etFname, etMI, etLname, etEmail, etContact;
    SQLiteDatabase dbLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        etFname = (EditText)  findViewById (R.id.etFname);
        etMI = (EditText)  findViewById (R.id.etMI);
        etLname = (EditText)  findViewById (R.id.etLname);
        etEmail = (EditText)  findViewById (R.id.etEmail);
        etContact = (EditText)  findViewById (R.id.etContact);
    }

    // MESSAGE INSERT RECORD
    public void showMessage (String title, String message) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setCancelable(true);
        myAlert.setTitle(title);
        myAlert.setMessage(message);
        myAlert.show();
    }

    // VIEW LIST
    public void viewList (View view) {
        Intent intent = new Intent(this, Records.class);
        startActivity(intent);
    }

    // ADD RECORD
    public void addRecord (View view) {

        if (TextUtils.isEmpty(etFname.getText().toString()))
        {
            etFname.setError("This cannot be empty!");
            return;
        }
        if (TextUtils.isEmpty(etMI.getText().toString()))
        {
            etMI.setError("This cannot be empty!");
            return;
        }
        if (TextUtils.isEmpty(etLname.getText().toString()))
        {
            etLname.setError("This cannot be empty!");
            return;
        }
        if (TextUtils.isEmpty(etEmail.getText().toString()))
        {
            etEmail.setError("This cannot be empty!");
            return;
        }
        if (TextUtils.isEmpty(etContact.getText().toString()))
        {
            etContact.setError("This cannot be empty!");
            return;
        }

        boolean r = dbHelper.insertRecord(etFname.getText().toString(), etMI.getText().toString(), etLname.getText().toString(), etEmail.getText().toString(), etContact.getText().toString());

        if (r == true) {
            Toast.makeText(this, "Record Saved!", Toast.LENGTH_LONG).show();
            etFname.setText("");
            etMI.setText("");
            etLname.setText("");
            etEmail.setText("");
            etContact.setText("");
        }
        else {
            Toast.makeText(this, "Error! Record not Saved!", Toast.LENGTH_LONG).show();
        }
    }

    // VIEW RECORD
    public void viewRecord (View view) {

        Cursor res = dbHelper.getData("0");

        if (res.getCount() == 0) {
            showMessage("ERROR", "No Record!");
        }
        else {
            StringBuffer buffer = new StringBuffer();

            while (res.moveToNext()) {
                buffer.append("Customer ID: " + res.getString(0) + "\n");
                buffer.append("First Name: " + res.getString(1) + "\n");
                buffer.append("Middle Initial: " + res.getString(2) + "\n");
                buffer.append("Last Name: " + res.getString(3) + "\n");
                buffer.append("Email Address: " + res.getString(4) + "\n");
                buffer.append("Contact Number: " + res.getString(5) + "\n\n");
            }
            showMessage("CUSTOMER INFORMATION", buffer.toString());
        }
    }

}