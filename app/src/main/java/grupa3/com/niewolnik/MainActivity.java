package grupa3.com.niewolnik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Date startTime = new Date();
    private Date endTime = new Date();


    public final static String EXTRA_MESSAGE = "com.abc.niewolnik.MONTH";

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
        Intent intent = new Intent(this, Calendar.class);
        startActivity(intent);
    }

    public void goToDelegationView(View view) {

    }
}
