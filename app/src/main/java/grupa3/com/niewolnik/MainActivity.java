package grupa3.com.niewolnik;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        mainProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        db_manager = new DB(this);

    }


    public void startWorkingTime(View view) {
        Date todayDate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<WorkDay> workDayList = db_manager.getWorkDay(sdf.format(todayDate));

        if (workDayList.size() != 0) {
            if ((db_manager.getWorkDay(sdf.format(todayDate))).size() != 0) {

                Log.d("DB", " --------day status----------");
                workDayList.addAll(db_manager.getWorkDay(sdf.format(todayDate)));
                for (int i = 0; i < workDayList.size(); i++) {
                    Log.d("DB", workDayList.get(i).toString());
                }
            } else {
                db_manager.addWorkday(new WorkDay());
                Log.d("DB", " --------day added ----------");
                Log.d("DB", db_manager.getWorkDay(sdf.format(todayDate)).toString());
            }
        }
    }

    public void stopWorkingTime(View view) {

    }

    public void goToCalendarView(View view) {
        Intent intent = new Intent(this, grupa3.com.niewolnik.Calendar.class);
        startActivity(intent);
    }

    public void goToDelegationView(View view) {

    }

    public void goToSettingsView(View view) {
        Intent intent = new Intent(this, grupa3.com.niewolnik.Settings.class);
        startActivity(intent);
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
