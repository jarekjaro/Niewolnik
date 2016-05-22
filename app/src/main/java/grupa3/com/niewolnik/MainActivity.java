package grupa3.com.niewolnik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Date startTime = new Date();
    private Date endTime = new Date();
    //private DB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //========testdb========
        addWorkDay();
        showDB();
        //======================
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



    public void addWorkDay() {
        DB db;

        //WorkDay workDay = new WorkDay("2016-05-20","08:20","16:40",0);
        //db.addWorkday(workDay);

        //workDay = new WorkDay("2016-05-21","08:30","17:10",0);
        //db.addWorkday(workDay);
    }

    public void showDB() {
        DB db;
        //for (WorkDay w : db.dajWszystkie()) {
        //    Log.d("DB", w.getLP() + " " + w.getDate()+ " " + w.getArriveTime() +
        //            " " + w.getLeavingTime());
        //}
    }
}
