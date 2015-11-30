package com.chebur.hw_9;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nick on 29.11.2015.
 */
public class MyDBHelper extends SQLiteOpenHelper {

    public final static int DB_VERSION = 1;
    public final static String TBL_NAME = "doctor";
    public final static String COL_ID = "id";
    public final static String COL_NAME = "name";
    public final static String COL_AGE = "age";
    public final static String TAG = "asrwth";

    public MyDBHelper(Context context) {
        super(context, TBL_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format(
                "create table %s (" +
                        "%s integer primary key autoincrement," +
                        "%s text not null unique," +
                        "%s integer" +
                        ");", TBL_NAME, COL_ID, COL_NAME, COL_AGE
        );
        Log.d(TAG, query);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void UpdateDoctor(Doctor dr) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, dr.get_name());
        cv.put(COL_AGE, dr.get_age());
        db.insertWithOnConflict(TBL_NAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

        db.close();
    }

    public List<Doctor> GetDoctors() {
        List<Doctor> doctors = new ArrayList<Doctor>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cr = db.query(TBL_NAME, null, null, null, null, null, null);

        if (cr.moveToFirst()) {
            do {
                Doctor dr = new Doctor();
                dr.set_age(cr.getInt(cr.getColumnIndex(COL_AGE)));
                dr.set_name(cr.getString(cr.getColumnIndex(COL_NAME)));
                dr.set_id(cr.getInt(cr.getColumnIndex(COL_ID)));

                doctors.add(dr);
            } while (cr.moveToNext());
        }
        else {
            Log.d(TAG, "cursor move to first error");
        }

        db.close();

        return  doctors;
    }
}
