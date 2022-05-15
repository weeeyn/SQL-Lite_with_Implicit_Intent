package com.activity.sql_litewithimplicitintent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Records extends AppCompatActivity {

    DatabaseHelper dbHelper;
    String[] id;
    String[] fname;
    String[] mi;
    String[] lname;
    String[] email;
    String[] contact;
    ListView lvRecord;
    View recView;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        dbHelper = new DatabaseHelper(this);

        getData();

        recView = getLayoutInflater().inflate(R.layout.edit_delete_record, null);
        AlertDialog.Builder build = new AlertDialog.Builder(Records.this);
        build.setView(recView);
        dialog = build.create();

        lvRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String a = parent.getItemAtPosition(position).toString();

                displayData(a);
            }
        });
    }

    public void getData() {

        lvRecord = (ListView) findViewById (R.id.lvRecord);

        Cursor res = dbHelper.getData("0");

        if (res.getCount() == 0) {
            Toast.makeText(Records.this, "No record found!", Toast.LENGTH_LONG).show();
            id = new String[]{"No record found!"};
            fname = new String[]{"No record found!"};
            mi = new String[]{"No record found!"};
            lname = new String[]{"No record found!"};
            email = new String[]{"No record found!"};
            contact = new String[]{"No record found!"};
        }
        else {
            id = new String[res.getCount()];
            fname = new String[res.getCount()];
            mi = new String[res.getCount()];
            lname = new String[res.getCount()];
            email = new String[res.getCount()];
            contact = new String[res.getCount()];

            int ctr = 0;

            while (res.moveToNext()) {
                id[ctr] = res.getString(0);
                fname[ctr] = res.getString(1);
                mi[ctr] = res.getString(2);
                lname[ctr] = res.getString(3);
                email[ctr] = res.getString(4);
                contact[ctr] = res.getString(5);
                ctr++;
            }

            CustomAdapter adapter = new CustomAdapter(this, id, fname, mi, lname, email, contact);
            lvRecord.setAdapter(adapter);
        }
    }

    // RETRIEVE DATA
    public void displayData (String id) {

        final String getID = id;

        Cursor res = dbHelper.getData(id);

        final EditText edFname = (EditText) recView.findViewById (R.id.edFname);
        final EditText edMI = (EditText) recView.findViewById (R.id.edMI);
        final EditText edLname = (EditText) recView.findViewById (R.id.edLname);
        final EditText edEmail = (EditText) recView.findViewById (R.id.edEmail);
        final EditText edContact = (EditText) recView.findViewById (R.id.edContact);

        Button btnUpdate = (Button) recView.findViewById (R.id.btnUpdate);
        Button btnDelete = (Button) recView.findViewById (R.id.btnDelete);

        if (res.getCount() == 0) {
            Toast.makeText(Records.this, "No record found!", Toast.LENGTH_LONG).show();
        }
        else {
            while (res.moveToNext()) {
                edFname.setText(res.getString(1));
                edMI.setText(res.getString(2));
                edLname.setText(res.getString(3));
                edEmail.setText(res.getString(4));
                edContact.setText(res.getString(5));
            }
            dialog.show();
        }
        dialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fn = edFname.getText().toString();
                String mid = edMI.getText().toString();
                String ln = edLname.getText().toString();
                String em = edEmail.getText().toString();
                String con = edContact.getText().toString();

                Boolean res = dbHelper.updateRecord(getID, fn, mid, ln, em, con);

                if (res == true) {
                    getData();
                    dialog.dismiss();
                    Toast.makeText(Records.this, "Record Updated!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Records.this, "Error Updating!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showDialogDelete(getID);
            }
        });
    }

    public void showDialogDelete (String id) {

        final String getID = id;

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Boolean res = dbHelper.deleteRecord(getID);
                        if (res == true) {
                            dialog.dismiss();
                            getData();
                            Toast.makeText(Records.this, "Record Deleted", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(Records.this, "Error Deleting!", Toast.LENGTH_LONG).show();
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder delBuild = new AlertDialog.Builder(this);

        delBuild.setCancelable(true);
        delBuild.setTitle("DELETE RECORD");
        delBuild.setMessage("Are you sure you want to delete this record?")
                .setPositiveButton("YES", dialogClickListener)
                .setNegativeButton("NO", dialogClickListener).show();
    }

}