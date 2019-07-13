package io.github.koh1project.SplitTheCost;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "split.db";
    private static final int DB_VERSION = 1;

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE members (id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                           "name TEXT NOT NULL," +
                           "group_id INTEGER , position_id INTEGER)");

        db.execSQL("CREATE TABLE groups (id INTEGER PRIMARY KEY  AUTOINCREMENT ,group_name TEXT " +
                           "NOT NULL)");

        db.execSQL("CREATE TABLE positions (id INTEGER PRIMARY KEY  AUTOINCREMENT ,position_name " +
                           "TEXT NOT NULL)");

        db.execSQL("CREATE TABLE checklist (id INTEGER PRIMARY KEY AUTOINCREMENT ,name TEXT NOT " +
                           "NULL , price INTEGER , special TEXT , paid_check TEXT , member_id " +
                           "INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS members");
        onCreate(db);
    }

    public void createCheckListTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE checklist (id INTEGER PRIMARY KEY AUTOINCREMENT ,name TEXT NOT " +
                           "NULL , price INTEGER , special TEXT , paid_check TEXT , member_id " +
                           "INTEGER)");
    }

    public void dropCheckListTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS checklist");
    }
}
