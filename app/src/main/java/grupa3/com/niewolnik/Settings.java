package grupa3.com.niewolnik;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_1);
        displayListView();
    }

    public void gotoMainActivity (View view) {
        Intent intent = new Intent(this, grupa3.com.niewolnik.MainActivity.class);
        startActivity(intent);
    }

    private void displayListView() {

        //Array list of options
        List<String> godzinyList = new ArrayList<String>();
        godzinyList.add("Plan tygodniowy - godziny od pon. do piątku");
        godzinyList.add("Plan miesieczny - godziny w każdym dniu roboczym");
        godzinyList.add("Plan urlopowy - lista dni urlopowych");
        godzinyList.add("Lista dni wolnych ( święta )");

        //create an ArrayAdaptar from the String Array

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.lista_opcji, godzinyList);
        ListView listView = (ListView) findViewById(R.id.listView1);

        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        //enables filtering for the contents of the given ListView
        listView.setTextFilterEnabled(true);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         /*
                String zm = "";
                Toast toast = Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_LONG);
                Toast toast_1 = Toast.makeText(getApplicationContext(),
                        (zm),Toast.LENGTH_LONG );

                switch (position ) {
                    case 0:
                        zm = "pierwszy";
                        gotoOpje(0);
                        break;
                    case 1:
                        zm = "drugi";
                        break;
                    case 2:
                        zm = "trzeci";
                        break;
                    default:
                        zm = "ostatni";
                }

                toast_1.setGravity(Gravity.TOP, 25, 300);
                toast_1.show();
            */
                gotoOpcje(position);
            }
        });
    }

    public void gotoOpcje (int nrOpcji) {
        switch (nrOpcji) {
            case 0:
                Intent intent_1 = new Intent(this, grupa3.com.niewolnik.Plan_na_tydzien.class);
                startActivity(intent_1);
                break;
            case 1:
                Intent intent_2 = new Intent(this, grupa3.com.niewolnik.Plan_na_miesiac.class);
                startActivity(intent_2);
                break;
            case 2:
                Intent intent_3 = new Intent(this, grupa3.com.niewolnik.Plan_urlopow.class);
                startActivity(intent_3);
                break;
            default:
                break;
        }
    }
}
