package grupa3.com.niewolnik;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    private Date startTime = new Date();
    private Date endTime = new Date();
    private BluetoothAdapter bluetoothAdapter;
    private ProgressBar mainProgressBar;
    private DB db_manager;

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


        //---------db tests------------------------------------------------------------
        db_manager = new DB(this);

//        db_manager.addWorkday(new WorkDay("2016-05-26", "07:30", "17:20", 0));
//        db_manager.addWorkday(new WorkDay("2016-05-30", "08:30", "12:00", 0));
//        db_manager.addWorkday(new WorkDay("2016-05-30", "14:30", "17:30", 0));

        //db_manager.delWorkday("2016-05-26");

        Log.d("DB", " --------pojedynczy----------");
        for(WorkDay wd:db_manager.getWorkDay("2016-05-26")) {
            Log.d("DB ", wd.toString());
        }

        Log.d("DB", " --------wyswietlanie----------");
        for(WorkDay wd:db_manager.getAll()) {
            Log.d("DB ", wd.toString());
        }
        Log.d("DB", " --------settings----------");
//        db_manager.updateSetting("mon", 480);
//        db_manager.addSetting("mon", 480);
//        db_manager.addSetting("tue", 480);
//        db_manager.addSetting("wed", 480);
//        db_manager.addSetting("thu", 480);
//        db_manager.addSetting("fri", 480);
//        db_manager.addSetting("sat", 480);
//        db_manager.addSetting("sun", 480);

//        db_manager.delSetting("fri", 480);
//        db_manager.delSetting("sat", 480);
//        db_manager.delSetting("sun",480);
        HashMap<String,Integer> allSettings=db_manager.getAllSettings();

        for (String s:new ArrayList<String>(allSettings.keySet()))
        {
            Log.d("DB",s+" "+ Integer.toString(allSettings.get(s)));
        }

//        Log.d("DB", " --------day status----------");
//        db_manager.getDayStatus("2016-05-26");
//        Log.d("DB", " --------day status----------");
//        db_manager.getDayStatus("2016-05-30");



        //--------------------------------------------------------------------
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
