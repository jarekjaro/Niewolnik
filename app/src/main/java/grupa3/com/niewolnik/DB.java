package grupa3.com.niewolnik;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

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

    public void addWorkday(WorkDay workDay) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("DATE", workDay.getDate());
        wartosci.put("A_TIME", workDay.getArriveTime());
        wartosci.put("L_TIME", workDay.getLeavingTime());
        wartosci.put("FREE_DAY", workDay.getFreeDay());
        db.insertOrThrow("REGISTER", null, wartosci);
    }

    public List<WorkDay> dajWszystkie() {
        List<WorkDay> workDays = new LinkedList<WorkDay>();
        String[] kolumny={"LP","DATE","A_TIME","L_TIME","FREE_DAY"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.query("REGISTER",kolumny,null,null,null,null,null);

        while(kursor.moveToNext()){
            WorkDay workDay = new WorkDay();
            workDay.setLP (kursor.getInt(0));
            workDay.setDate(kursor.getString(1));
            workDay.setArriveTime(kursor.getString(2));
            workDay.setLeavingTime(kursor.getString(3));
            workDay.setFreeDay(kursor.getInt(4));
            workDays.add(workDay);
        }
        return workDays;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}