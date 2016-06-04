package grupa3.com.niewolnik;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    private Date startTime = new Date();
    private Date endTime = new Date();
    private BluetoothAdapter bluetoothAdapter;
    //private DB db;

    private int currentProgress = 0;
    private int dailyWorkedTime = 0;
    private int currentDayWorkingHours = 8;

    private int calculateMinutesDifference(Date startTime, Date endTime) {
        return (int) ((endTime.getTime() - startTime.getTime()) / 1000 / 60);
    }

    private String differenceInString(int differenceInMinutes) {
        int min = differenceInMinutes % 60;
        int hours = (differenceInMinutes - min) / 60;
        return hours + "h " + min + "m";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

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

    public void pairBluetoothTriggeringDevice(View view) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            // If there are paired devices
            if (pairedDevices.size() > 0) {
                // Loop through paired devices
                for (BluetoothDevice device : pairedDevices) {
                    // Add the name and address to an array adapter to show in a ListView
                    ArrayList<String> listaUrzadzen = new ArrayList<>();
                    listaUrzadzen.add(device.getName() + "\n" + device.getAddress());
                }
            }
        }
    }
}
