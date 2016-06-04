package grupa3.com.niewolnik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;


public class Calendar extends AppCompatActivity {

    String currentDate;
    String currentLeaveTime;
    String currentArrivalTime;
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
        db_manager.addWorkday(new WorkDay("2016-06-02", "02:11", "20:03", 1));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                month += 1;
                currentDate = year + "-";
                if (month < 9) {
                    currentDate += "0" + month;
                }
                else {
                    currentDate += month;
                }
                currentDate += "-";
                if (day < 9) {
                    currentDate += "0" + day;
                }
                else {
                    currentDate += day;
                }
                printWorkTime(currentDate);
                Toast.makeText(getApplicationContext(), "currentDate:\n" + currentDate, Toast.LENGTH_LONG).show();
            }
        });

    }



    public void showStatistics(View view) {
        //db_manager.getDayStatus("2016-05-26");

        Log.d("DB", " --------test current date----------");
        Log.d("DB", "CD: " + currentDate);

        printWorkTime(currentDate);
    }

    public void showDailyTime(View view) {

//        for (WorkDay wd : db_manager.getWorkDay("2016-05-26")) {
//            Log.d("DB ", wd.toString());
//            statisticDisplay.setText("Czas pracy" + wd.toString());
//        }

        printWorkTime("2016-05-26");
    }

    public void printWorkTime(String cDate) {

        statisticDisplay.setText("");
        if (cDate != null) {
            for (WorkDay wd : db_manager.getWorkDay(cDate)) {

                currentLeaveTime = wd.getLeavingTime();
                currentArrivalTime = wd.getArriveTime();

                statisticDisplay.setText("Praca od: " + currentArrivalTime + " do: " + currentLeaveTime);

                //statisticDisplay.setText("Czas pracy: " + wd.toString());


                if (wd.getFreeDay() != 0){
                    statisticDisplay.append("\nPraca w dniu wolnym !");
                }


            }
        }
        else {
            statisticDisplay.setText("-------------");
            Toast.makeText(getApplicationContext(), "Null Date !\n", Toast.LENGTH_LONG).show();
        }
    }


    public String getWorkDuration(String leaveTime,String arrivalTime){
        String outValue = "";
        String tmpStr;
        Integer leaveTimeInMinutes;
        Integer arrivalTimeInMinutes;
        Integer workTimeMinutes;

        leaveTimeInMinutes = 60 * Integer.parseInt(leaveTime.substring(0, 1)) + Integer.parseInt(leaveTime.substring(3, 4));
        arrivalTimeInMinutes = 60 * Integer.parseInt(arrivalTime.substring(0, 1)) + Integer.parseInt(arrivalTime.substring(3, 4));
        workTimeMinutes = leaveTimeInMinutes - arrivalTimeInMinutes;

        Log.d("DB", "Work Time: " + outValue + workTimeMinutes);

        return outValue;
    }


}
