package grupa3.com.niewolnik;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

/**
 * Created by student on 22/05/16.
 */
public class DB extends SQLiteOpenHelper {

    public DB(Context context) {
        super(context, "niewolnik.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE SETTINGS (WEEKDAY CHAR(3) PRIMARY KEY, WORK_MINUTES INT(1) DEFAULT 8 )"));

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

    public void delWorkday(String date) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {date};
        db.delete("REGISTER", "date=?", args);
    }

    public List<WorkDay> getWorkDay(String date) {
        List<WorkDay> workDays = new LinkedList<WorkDay>();
        String[] kolumny={"LP","DATE","A_TIME","L_TIME","FREE_DAY"};
        SQLiteDatabase db = getReadableDatabase();
        String args[]={date};
        Cursor cursor = db.query("REGISTER", kolumny,"date=?", args,null,null,null,null);
        //Cursor kursor=db.query("telefony",kolumny,"nr=?",args,null,null,null,null);

        while(cursor.moveToNext()){
            Log.d("DB ","moveToNext0:"+cursor.getInt(0));
            WorkDay workDay = new WorkDay();
            workDay.setLP (cursor.getInt(0));
            workDay.setDate(cursor.getString(1));
            workDay.setArriveTime(cursor.getString(2));
            workDay.setLeavingTime(cursor.getString(3));
            workDay.setFreeDay(cursor.getInt(4));
            workDays.add(workDay);
        }
        return workDays;
    }

    public List<WorkDay> getAll() {
        List<WorkDay> workDays = new LinkedList<WorkDay>();
        String[] cols={"LP","DATE","A_TIME","L_TIME","FREE_DAY"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.query("REGISTER",cols,null,null,null,null,null);

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

    public void delSetting(String weekDay) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(String.format("DELETE FROM SETTINGS WHERE WEEKDAY='%s'",weekDay));
    }

    public void addSetting(String weekDay,int workHours) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("WEEKDAY", weekDay);
        values.put("WORK_MINUTES", workHours);
        db.insertOrThrow("SETTINGS", null, values);
    }
    public void updateSetting(String weekDay,int workHours) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(String.format("UPDATE SETTINGS SET WORK_MINUTES=%s WHERE WEEKDAY='%s'", workHours, weekDay));
    }
    public HashMap<String,Integer> getAllSettings() {
        HashMap<String,Integer> settings = new HashMap<String,Integer>();
        String[] cols={"WEEKDAY","WORK_MINUTES"};
        SQLiteDatabase db_read = getReadableDatabase();
        Cursor cursor = db_read.query("SETTINGS",null,null,null,null,null,null);

        while(cursor.moveToNext()){
            settings.put(cursor.getString(0), cursor.getInt(1));
        }
        return settings;
    }

    public int getMonthStatus(String date) {

        String[] split_date = date.split("-");
        int iYear = Integer.parseInt(split_date[0]);
        int iMonth = Integer.parseInt(split_date[1]);; // 1 (months begin with 0)
        int iDay = 1;


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        GregorianCalendar calendar = new GregorianCalendar(2013,01,28,13,24,56);
        calendar.get(Calendar.DAY_OF_MONTH);
        Log.d("DB ", "day of month:" + calendar.get(Calendar.DAY_OF_MONTH));

        return 0;
    }

    public int getDayStatus (String date) {

        List<WorkDay> workDays = new LinkedList<WorkDay>();
        String[] cols={"LP","DATE","A_TIME","L_TIME","FREE_DAY"};
        SQLiteDatabase db = getReadableDatabase();
        String args[]={date};
        Cursor cursor = db.query("REGISTER", cols,"date=?", args,null,null,null,null);
        //Cursor kursor=db.query("telefony",kolumny,"nr=?",args,null,null,null,null);

        while(cursor.moveToNext()){
            WorkDay workDay = new WorkDay();
            workDay.setLP (cursor.getInt(0));
            workDay.setDate(cursor.getString(1));
            workDay.setArriveTime(cursor.getString(2));
            workDay.setLeavingTime(cursor.getString(3));
            workDay.setFreeDay(cursor.getInt(4));
            workDays.add(workDay);
        }

        int status=0;
        int work_minutes=0;
        //Log.d("DB ","size:"+workDays.size()+workDays.get(1).getDate());
        for(WorkDay workday:workDays) {

            String a_time = workday.getDate()+" "+workday.getArriveTime();
            String l_time = workday.getDate()+" "+workday.getLeavingTime();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1= new Date();
            try {
                d1 = format.parse(workday.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //getting weekday from date
            args[0]=(new SimpleDateFormat("EE").format(d1)).toString().toLowerCase();
            String[] col={"WORK_MINUTES"};
            cursor = db.query("SETTINGS", col,"weekday=?", args,null,null,null,null);

            while(cursor.moveToNext()) {
                work_minutes=(cursor.getInt(0));
            }
            status+=getDateDiff(a_time,l_time);

            Log.d("DB ", "a_time:"+a_time);
            Log.d("DB ", "l_time:" + l_time);
            Log.d("DB ","work_minutes:"+work_minutes);
            Log.d("DB ","weekday:"+(new SimpleDateFormat("EE").format(d1)).toString().toLowerCase());

        }
        status=status-work_minutes;
        Log.d("DB ","status:"+status);


        return status;
    }

    private int getDateDiff(String date1,String date2){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(date1);
            d2 = format.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Get msec from each, and subtract.
        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        return (int)diffMinutes;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(String.format("CREATE TABLE SETTINGS (WEEKDAY CHAR(3) PRIMARY KEY, WORK_HOURS INT(1) DEFAULT 8 )"));
        //db.execSQL(String.format("UPDATE SETTINGS SET WEEKDAY='mon', WORK_HOURS='8' where WEEKDAY=0"));
        //db.execSQL(String.format("INSERT INTO SETTINGS (WEEKDAY,WORK_HOURS) VALUES ('tue','8')"));
        //db.execSQL(String.format("DELETE SETTINGS WHERE WEEKDAY IN ('33','ala','al','a','ak')"));
    }




}