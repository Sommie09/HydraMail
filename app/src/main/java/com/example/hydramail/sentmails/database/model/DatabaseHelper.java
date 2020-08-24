package com.example.hydramail.sentmails.database.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "mails_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Mails.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Mails.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertMail(String note) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Mails.COLUMN_MESSAGE, note);

        // insert row
        long id = db.insert(Mails.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Mails getMail(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Mails.TABLE_NAME,
                new String[]{Mails.COLUMN_ID, Mails.COLUMN_RECIPIENT, Mails.COLUMN_SUBJECT, Mails.COLUMN_MESSAGE, Mails.COLUMN_TIMESTAMP},
                Mails.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Mails note = new Mails(
                cursor.getInt(cursor.getColumnIndex(Mails.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Mails.COLUMN_RECIPIENT)),
                cursor.getString(cursor.getColumnIndex(Mails.COLUMN_SUBJECT)),
                cursor.getString(cursor.getColumnIndex(Mails.COLUMN_MESSAGE)),
                cursor.getString(cursor.getColumnIndex(Mails.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return note;
    }

    public List<Mails> getAllMails() {
        List<Mails> mails = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Mails.TABLE_NAME + " ORDER BY " +
                Mails.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Mails mail = new Mails();
                mail.setId(cursor.getInt(cursor.getColumnIndex(Mails.COLUMN_ID)));
                mail.setRecipient(cursor.getString(cursor.getColumnIndex(Mails.COLUMN_RECIPIENT)));
                mail.setSubject(cursor.getString(cursor.getColumnIndex(Mails.COLUMN_SUBJECT)));
                mail.setMessage(cursor.getString(cursor.getColumnIndex(Mails.COLUMN_MESSAGE)));
                mail.setTimestamp(cursor.getString(cursor.getColumnIndex(Mails.COLUMN_TIMESTAMP)));

                mails.add(mail);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return mails;
    }

    public int getMailCount() {
        String countQuery = "SELECT  * FROM " + Mails.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public void deleteMails(Mails mail) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Mails.TABLE_NAME, Mails.COLUMN_ID + " = ?",
                new String[]{String.valueOf(mail.getId())});
        db.close();
    }



}
