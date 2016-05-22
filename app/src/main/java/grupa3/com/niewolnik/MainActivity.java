package grupa3.com.niewolnik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Date startTime = new Date();
    private Date endTime = new Date();
    private int currentProgress =0;
    private int dailyWorkedTime =0;
    private int currentDayWorkingHours = 8;

    private int calculateMinutesDifference(Date startTime, Date endTime) {
        return (int) ((endTime.getTime() - startTime.getTime())/1000/60);
    }

    private String differenceInString(int differenceInMinutes) {
        int min = differenceInMinutes % 60;
        int hours = (differenceInMinutes -min)/60;
        return hours + "h " + min + "m";
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void startWorkingTime(View view) {

    }

    public void stopWorkingTime(View view) {

    }

    public void goToCalendarView(View view) {
        Intent intent = new Intent(this, grupa3.com.niewolnik.Calendar.class);
        startActivity(intent);
    }

    public void goToDelegationView(View view) {

    }
}
