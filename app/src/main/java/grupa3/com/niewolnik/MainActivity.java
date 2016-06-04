package grupa3.com.niewolnik;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Date startTime = new Date();
    private Date endTime = new Date();
    private BluetoothAdapter bluetoothAdapter;
    //private DB db;

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

        //   Plan_obszar_wspolny. zapiszPlanRob("NiewolnikPlan",getApplicationContext());
        Plan_obszar_wspolny.dajPreferencje(getApplicationContext());

        //========testdb========
        addWorkDay();
        showDB();
        //======================
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Piotr
        Plan_obszar_wspolny.piszPreferencje(getApplicationContext(),
                Plan_obszar_wspolny.daj_nr_planu(),
                Plan_obszar_wspolny.daj_nr_roku(),
                Plan_obszar_wspolny.daj_nr_mies());
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

    public void pairBluetoothTriggeringDevice(View view) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!bluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
        }
    }
}
