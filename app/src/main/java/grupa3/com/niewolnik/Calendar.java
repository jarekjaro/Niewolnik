package grupa3.com.niewolnik;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class Calendar extends AppCompatActivity {

    String currentDate = "2016-06-01";
    String currentLeaveTime;
    String currentArrivalTime;
    Integer currentDayStatus;
    Integer currentDaySetting;
    TextView statisticDisplay;
    CalendarView calendarView;
    DB db_manager = new DB(this);
    WorkDay wd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        statisticDisplay = (TextView) findViewById(R.id.textView5);
        statisticDisplay.setText("Date: ");

        // TRYOUT
//        db_manager.addWorkday(new WorkDay("2016-05-26", "07:30", "17:20", 0));
//        db_manager.addWorkday(new WorkDay("2016-06-01", "08:02", "16:03", 0));
//        db_manager.addWorkday(new WorkDay("2016-06-03", "09:22", "20:03", 0));
//        db_manager.addWorkday(new WorkDay("2016-06-02", "02:11", "20:03", 1));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                month += 1;
                currentDate = year + "-";
                if (month < 9) {
                    currentDate += "0" + month;
                } else {
                    currentDate += month;
                }
                currentDate += "-";
                if (day < 9) {
                    currentDate += "0" + day;
                } else {
                    currentDate += day;
                }
                Log.d("DB ", "CDate: " + currentDate);
                printWorkTime(currentDate);
                //Toast.makeText(getApplicationContext(), "currentDate:\n" + currentDate, Toast.LENGTH_LONG).show();
            }
        });

    }


    public void showStatistics(View view) {
        long minDateMs;
        long maxDateMs;
        String minDate;
        String maxDate;


        DateFormat cdfSimple = new SimpleDateFormat("yyy-MM-dd");
        Date cdSimple = new Date();
        String date= cdfSimple.format(cdSimple);
        String[] split_date = date.split("-");
        int iYear = Integer.parseInt(split_date[0]);
        int iMonth = Integer.parseInt(split_date[1]) - 1;
        int iDay = 1;

        GregorianCalendar calendar = new GregorianCalendar(iYear, iMonth, iDay);
        int daysOfMonth = calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        int status = 0;

        for (int i = 1; i <= daysOfMonth; i++) {
            String day = "";
            if (i < 10) {
                day = "0" + Integer.toString(i);
            } else {
                day = Integer.toString(i);
            }
            List<WorkDay> wd=db_manager.getWorkDay(split_date[0] + "-" + split_date[1] + "-" + day);
            status = db_manager.getDayStatus(split_date[0] + "-" + split_date[1] + "-" + day);
            Log.d("DB ", "day of month:" + split_date[0] + "-" + split_date[1] + "-" + day+", status"+status);
            if(wd.size()>0) {
                Log.d("DB ", "godz przyjscia:" + wd.get(0).getArriveTime() + ", godz. wyjscia:" + wd.get(0).getLeavingTime());
            }
        }
    }

    public void showDailyTime(View view) {

//        for (WorkDay wd : db_manager.getWorkDay("2016-05-26")) {
//            Log.d("DB ", wd.toString());
//            statisticDisplay.setText("Czas pracy" + wd.toString());
//        }

        printWorkTime("2016-05-26");


    }

    public void printWorkTime(String cDate) {
        String workDuration;
        statisticDisplay.setText("");
        if (cDate != null) {
            for (WorkDay wd : db_manager.getWorkDay(cDate)) {
                Log.d("DB ", "GetWorkDay");
                currentLeaveTime = wd.getLeavingTime();
                currentArrivalTime = wd.getArriveTime();
//                currentDayStatus = wd.;
//                currentDaySetting;

                statisticDisplay.setText("Praca od: " + currentArrivalTime + " do: " + currentLeaveTime + " ");
                workDuration = getWorkDuration(currentLeaveTime, currentArrivalTime);
                statisticDisplay.append(workDuration);
                if (wd.getFreeDay() != 0) {
                    statisticDisplay.append("\nPraca w dniu wolnym !");
                }
                if(checkWorkStatus() == true) {
                    statisticDisplay.setTextColor(Color.GREEN);
                }
                else{
                    statisticDisplay.setTextColor(Color.RED);
                }
            }
        } else {
            statisticDisplay.setText("-------------");
            Toast.makeText(getApplicationContext(), "Null Date !\n", Toast.LENGTH_LONG).show();
        }
    }


    public String minToHH_MM(Integer timeMinutes) {
        String outValue = "";
        Integer timeHH;
        Integer timeMM;

        timeHH = timeMinutes/60;
        timeMM = timeMinutes%60;
        outValue+=timeHH.toString() + "h ";
        outValue+=timeMM.toString() + "m ";

        return outValue;
    }



    public String getWorkDuration(String leaveTime, String arrivalTime) {
        String outValue = "Czas pracy: ";

        String tmpStr;
        Integer leaveTimeInMinutes;
        Integer arrivalTimeInMinutes;
        Integer workTimeMinutes;
        Integer workTimeH;
        Integer workTimeM;

        Log.d("DB", "Lt, At: " + leaveTime + "  " + arrivalTime);

        leaveTimeInMinutes = 60 * Integer.parseInt(leaveTime.substring(0, 2)) + Integer.parseInt(leaveTime.substring(3, 5));
        arrivalTimeInMinutes = 60 * Integer.parseInt(arrivalTime.substring(0, 2)) + Integer.parseInt(arrivalTime.substring(3, 5));
        workTimeMinutes = leaveTimeInMinutes - arrivalTimeInMinutes;
        outValue += minToHH_MM(workTimeMinutes);

        Log.d("DB", "LtMin, Atmin " + leaveTimeInMinutes + "  " + arrivalTimeInMinutes);
        Log.d("DB", "Int VAL: " + workTimeMinutes);
        Log.d("DB", "Out: " + outValue);

        return outValue;
    }

    public String getWorkStatus() {
        String outValue = "Status: -8h";
        db_manager.getDayStatus(currentDate);
        return outValue;
    }

    Boolean checkWorkStatus() {
        Boolean status = false;
        Integer dayStatus;

//        dayStatus = db_manager.getDayStatus(currentDate);
//
//        if (dayStatus > 0) {
//            status = true;
//        }
//        Log.d("DB", "Day status: " + dayStatus);



        return status;

    }

}