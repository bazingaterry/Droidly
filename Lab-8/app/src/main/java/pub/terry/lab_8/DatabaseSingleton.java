package pub.terry.lab_8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by terrychan on 17/11/2016.
 */
class Database extends SQLiteOpenHelper {
    final String TABLE_NAME = "birthday_log";

    Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(String.format("CREATE TABLE IF NOT EXISTS '%s' (nameTextView TEXT PRIMARY KEY, birthdayTextView TEXT, giftTextView TEXT);", TABLE_NAME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insert(String name, String birthday, String gift) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nameTextView", name);
        contentValues.put("birthdayTextView", birthday);
        contentValues.put("giftTextView", gift);
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues) != -1;
    }

    public boolean update(String name, String birthday, String gift) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nameTextView", name);
        contentValues.put("birthdayTextView", birthday);
        contentValues.put("giftTextView", gift);
        String whereClause = "nameTextView=?";
        String[] whereArgs = {name};
        return sqLiteDatabase.update(TABLE_NAME, contentValues, whereClause, whereArgs) != 0;
    }

    public boolean delete(String name) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "nameTextView=?";
        String[] whereArgs = {name};
        return sqLiteDatabase.delete(TABLE_NAME, whereClause, whereArgs) != 0;
    }

    public List<Person> queryAll() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(String.format("SELECT * FROM %s", TABLE_NAME), null);
        List<Person> persons = new ArrayList<>();
        while (cursor.moveToNext()) {
            Person person = new Person();
            person.setName(cursor.getString(cursor.getColumnIndex("nameTextView")));
            person.setBirthday(cursor.getString(cursor.getColumnIndex("birthdayTextView")));
            person.setGift(cursor.getString(cursor.getColumnIndex("giftTextView")));
            persons.add(person);
        }
        cursor.close();
        return persons;
    }
}


public class DatabaseSingleton {
    private static Database database;
    final static String DB_NAME = "person";
    final static int DB_VERSION = 1;

    private DatabaseSingleton() {
    }

    public static synchronized Database getDatabase(Context context) {
        if (database == null) {
            database = new Database(context, DB_NAME, null, DB_VERSION);
        }
        return database;
    }
}
