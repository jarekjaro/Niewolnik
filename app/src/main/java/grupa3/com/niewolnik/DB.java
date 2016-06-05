package grupa3.com.niewolnik;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by student on 22/05/16.
 */
public class DB extends SQLiteOpenHelper {

    public DB(Context context) {
        super(context, "niewolnik.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE SETTINGS (WEEKDAY CHAR(3) PRIMARY KEY, WORK_HOURS INT(4) DEFAULT 8 )"));

        db.execSQL(
                String.format("CREATE TABLE REGISTER (LP INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "DATE CHAR(10)," +
                        "A_TIME CHAR(5)," +
                        "L_TIME CHAR(5)," +
                        "FREE_DAY INT(1));"));


        db.execSQL(String.format("CREATE TABLE SETTINGS_MONTH (DATE CHAR(10) PRIMARY KEY, WORK_MINUTES INT(4) )"));
        addSetting("mon", 480);
        addSetting("tue",480);
        addSetting("wed",480);
        addSetting("thu",480);
        addSetting("fri",480);
        addSetting("sat",480);
        addSetting("sun", 480);

    }

    public void setWorkdayLTime(WorkDay workDay) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(String.format("UPDATE REGISTER SET L_TIME='%s' WHERE DATE='%s' AND L_TIME=''", workDay.getLeavingTime(), workDay.getDate()));
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
        db.execSQL(String.format("DELETE FROM SETTINGS WHERE WEEKDAY='%s'", weekDay));
    }

    public void addSetting(String weekDay,int workHours) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("WEEKDAY", weekDay);
        values.put("WORK_HOURS", workHours);
        db.insertOrThrow("SETTINGS", null, values);
    }

    public int getDaySetting(String day) {

        String d="";
        if(day.length()>4) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1= new Date();
            try {
                d1 = format.parse(day);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            day=(new SimpleDateFormat("EE").format(d1)).toString().toLowerCase();
        }
        HashMap<String, Integer> allSettings = getAllSettings();
        if(allSettings.get(day)==null) {
            return 480;
        }
        return allSettings.get(day);
    }



    public void updateSetting(String weekDay,int workHours) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(String.format("UPDATE SETTINGS SET WORK_HOURS=%s WHERE WEEKDAY='%s'", workHours, weekDay));
    }
    public HashMap<String,Integer> getAllSettings() {
        HashMap<String,Integer> settings = new HashMap<String,Integer>();
        String[] cols={"WEEKDAY","WORK_HOURS"};
        SQLiteDatabase db_read = getReadableDatabase();
        Cursor cursor = db_read.query("SETTINGS",null,null,null,null,null,null);

        while(cursor.moveToNext()){
            settings.put(cursor.getString(0), cursor.getInt(1));
        }
        return settings;
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

            if(workday.getArriveTime()==null || workday.getArriveTime().equalsIgnoreCase("") || workday.getLeavingTime()==null || workday.getLeavingTime().equalsIgnoreCase("")) {
                continue;
            }
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
            String[] col={"WORK_HOURS"};
            cursor = db.query("SETTINGS", col,"weekday=?", args,null,null,null,null);

            while(cursor.moveToNext()) {
                work_minutes=(cursor.getInt(0));
            }
            status+=getDateDiff(a_time,l_time);

            Log.d("DB ", "a_time:"+a_time);
            Log.d("DB ", "l_time:" + l_time);
            Log.d("DB ","work_hours:"+work_minutes);
            Log.d("DB ","weekday:"+(new SimpleDateFormat("EE").format(d1)).toString().toLowerCase());

        }
        if(status!=0) {
            status = status - work_minutes;
        }
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

    public int getMonthStatus(String date) {



        String[] split_date = date.split("-");
        int iYear = Integer.parseInt(split_date[0]);
        int iMonth = Integer.parseInt(split_date[1]) - 1;
        int iDay = 1;

        GregorianCalendar calendar = new GregorianCalendar(iYear, iMonth, iDay);
        int daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int status = 0;

        for (int i = 1; i <= daysOfMonth; i++) {
            String day = "";
            if (i < 10) {
                day = "0" + Integer.toString(i);
            } else {
                day = Integer.toString(i);
            }
            Log.d("DB ", "day of month:" + split_date[0] + "-" + split_date[1] + "-" + day);

            status += getDayStatus(split_date[0] + "-" + split_date[1] + "-" + day);
        }

        return status;
    }

    public int getMinutesFromStart() {
        if(getWorkDay(getCurrentDateSimple()).size()==0) {
            return 0;
        }

        DateFormat dfFull = new SimpleDateFormat("yyy-MM-dd HH:mm");
        Date date = new Date();
        String cdate = dfFull.format(date);

        String cDateSimple =getCurrentDateSimple();

        List<WorkDay> workDays = getWorkDay(cDateSimple);

        String date2="";
        for(WorkDay w:workDays) {
            if(!w.getLeavingTime().equalsIgnoreCase("")) {
                continue;
            }
            String s_date = w.getDate();
            String a_time = w.getArriveTime();
            date2 = s_date + " " + a_time;
        }
        //dayStatus includes other leaving, arriving activities in current day
        int dayStatus=getDayStatus(cDateSimple);
        Log.d("DB", "dayStatus:"+Integer.toString(dayStatus));
        if(dayStatus!=0) {
            dayStatus+=getDaySetting(cDateSimple);
        }
        if(date2.equalsIgnoreCase("")) {
            return 0;
        }
        else {
            return getDateDiff(date2, cdate) + dayStatus;
        }
    }

    private String getCurrentDateSimple() {
        DateFormat cdfSimple = new SimpleDateFormat("yyy-MM-dd");
        Date cdSimple = new Date();
        return cdfSimple.format(cdSimple);
    }

    public void addMonthPlan(String date, int [] hours) {

        SQLiteDatabase db = getWritableDatabase();
        String day="";
        for(int i=1;i<=hours.length;i++) {
            if (i < 10) {
                day = "0" + Integer.toString(i);
            }
            else {
                day = Integer.toString(i);
            }
            ContentValues values = new ContentValues();
            String full_date=date+"-"+day;
            Log.d("DB", "full_date:" +full_date);
            Log.d("DB", "hours["+i+"]:" +hours[i-1]);
            values.put("DATE", full_date);
            values.put("WORK_MINUTES", hours[i-1]);
            db.delete("SETTINGS_MONTH",null,null);
            db.insertOrThrow("SETTINGS_MONTH", null, values);
        }
    }

    public int [] getMonthPlan(String date) {
        HashMap<String,Integer> settings_month = new HashMap<String,Integer>();
        String[] cols={"DATE","WORK_MINUTES"};
        SQLiteDatabase db_read = getReadableDatabase();
        Cursor cursor = db_read.query("SETTINGS_MONTH", null, null, null, null, null, null);
        List <Integer> hours = new LinkedList<Integer>();

        while(cursor.moveToNext()){
            settings_month.put(cursor.getString(0), cursor.getInt(1));
        }
        int [] h=null;
        int k=0;
        for (String s:new ArrayList<String>(settings_month.keySet()))
        {
            if(s.contains(date)){
                hours.add((Integer) settings_month.get(s));
            }
        }
        int[] v = new int [hours.size()];
        int j=0;
        for(Integer i:hours) {
            v[j++]=i;
        }
        return v;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }




}