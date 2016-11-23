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
    private static Database database = null;
    private final static String DB_NAME = "person";
    private final static int DB_VERSION = 1;
    private final String TABLE_NAME = "birthday_log";

    static synchronized Database getDatabase(Context context) {
        if (database == null) {
            database = new Database(context, DB_NAME, null, DB_VERSION);
        }
        return database;
    }

    private Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(String.format("CREATE TABLE IF NOT EXISTS '%s' (name TEXT PRIMARY KEY, birthday TEXT, gift TEXT);", TABLE_NAME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    boolean insert(String name, String birthday, String gift) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("birthday", birthday);
        contentValues.put("gift", gift);
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues) != -1;
    }

    boolean update(String name, String birthday, String gift) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("birthday", birthday);
        contentValues.put("gift", gift);
        String whereClause = "name=?";
        String[] whereArgs = {name};
        return sqLiteDatabase.update(TABLE_NAME, contentValues, whereClause, whereArgs) != 0;
    }

    boolean delete(String name) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "name=?";
        String[] whereArgs = {name};
        return sqLiteDatabase.delete(TABLE_NAME, whereClause, whereArgs) != 0;
    }

    List<Person> queryAll() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(String.format("SELECT * FROM %s", TABLE_NAME), null);
        List<Person> persons = new ArrayList<>();
        while (cursor.moveToNext()) {
            Person person = new Person();
            person.setName(cursor.getString(cursor.getColumnIndex("name")));
            person.setBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
            person.setGift(cursor.getString(cursor.getColumnIndex("gift")));
            persons.add(person);
        }
        cursor.close();
        return persons;
    }
}
