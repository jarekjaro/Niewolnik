package grupa3.com.niewolnik;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by student on 22/05/16.
 */
public class DB extends SQLiteOpenHelper {


    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                String.format("CREATE TABLE REGISTER (LP INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "DATE CHAR(10)," +
                        "A_TIME CHAR(5)," +
                        "L_TIME CHAR(5)," +
                        "FREE_DAY INT(1));"));
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}