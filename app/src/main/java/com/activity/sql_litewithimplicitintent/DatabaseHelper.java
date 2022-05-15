package com.activity.sql_litewithimplicitintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String dbName = "database1";
    public static final String tblname = "table1";

    public DatabaseHelper (Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +  tblname + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, fname TEXT, mi TEXT, lname TEXT, email TEXT, contact TEXT)");
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tblname);
        onCreate(db);
    }

    // ADD RECORD
    public boolean insertRecord(String fname, String mi, String lname, String email, String contact) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();

        cValues.put("fname", fname);
        cValues.put("mi", mi);
        cValues.put("lname", lname);
        cValues.put("email", email);
        cValues.put("contact", contact);

        long result = db.insert(tblname, null, cValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    // GET DATA
    public Cursor getData (String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;

        if (id == "0") {
            res = db.rawQuery("SELECT * FROM " + tblname, null);
        }
        else {
            res = db.rawQuery("SELECT * FROM " + tblname + " WHERE id = '" + id + "'", null);
        }
        return res;
    }

    // DELETE RECORD
    public boolean deleteRecord (String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tblname, "id = ?", new String[] {id});
        return true;
    }

    // UPDATE RECORD
    public boolean updateRecord (String id, String fname, String mi, String lname, String email, String contact) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();

        cValues.put("id", id);
        cValues.put("fname", fname);
        cValues.put("mi", mi);
        cValues.put("lname", lname);
        cValues.put("email", email);
        cValues.put("contact", contact);

        db.update(tblname, cValues, "id = ?", new String[] {id});
        return true;
    }
}
