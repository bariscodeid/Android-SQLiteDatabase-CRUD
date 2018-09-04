package dev.id.bariscode.crudandroidsqlitedatabase.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class databaseConfig extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "db_dataperson.db";

    public static final String TABLE_NAME = "tbl_datas";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";

    public databaseConfig(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db_createtable) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_ADDRESS + " TEXT NOT NULL" +
                " )";
        db_createtable.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase databaseUpgrade, int oldVersion, int newVersion) {
        databaseUpgrade.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(databaseUpgrade);
    }

    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();

        String querySelecttion = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(querySelecttion, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_ID, cursor.getString(0));
                map.put(COLUMN_NAME, cursor.getString(1));
                map.put(COLUMN_ADDRESS, cursor.getString(2));
                wordList.add(map);
            } while (cursor.moveToNext());
        }

        Log.e("QUE SELECT: ", "" + wordList);
        database.close();
        return wordList;
    }

    public void insert(String name, String address) {
        SQLiteDatabase database = this.getWritableDatabase();

        String queryInsert = "INSERT INTO " + TABLE_NAME + " (name, address) " + "VALUES ('" + name + "', '" + address + "')";

        Log.e("Inserting Data: ", queryInsert);
        database.execSQL(queryInsert);
        database.close();
    }

    public void update(int id, String name, String address) {
        SQLiteDatabase database = this.getWritableDatabase();

        String queryUpdate = "UPDATE " + TABLE_NAME + " SET "
                + COLUMN_NAME + " = '" + name + "', "
                + COLUMN_ADDRESS + " = '" + address + "'"
                + " WHERE " + COLUMN_ID + "=" + "'" + id + "'";

        Log.e("Updating Data: ", queryUpdate);
        database.execSQL(queryUpdate);
        database.close();
    }

    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        String queryDelete = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + "'" + id + "'";

        Log.e("Deleting Data: ", queryDelete);
        database.execSQL(queryDelete);
        database.close();
    }

}
