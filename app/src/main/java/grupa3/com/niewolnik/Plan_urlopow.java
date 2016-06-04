package grupa3.com.niewolnik;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Plan_urlopow extends AppCompatActivity {

    private List<String> listaUrlop;
    private ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_urlopow);
        listaUrlopow();
    }

    private void listaUrlopow() {
        //Lista urlopów

        //List<String> listaUrlop= new ArrayList<String>();
        listaUrlop= new ArrayList<String>();

        listaUrlop.add("2016-07-01" + "   *   " + "2016-07-15");
        listaUrlop.add("2016-07-30");
        listaUrlop.add("2016-11-02");
        listaUrlop.add("2016-12-20" + "   *   " + "2016-12-31");

        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.lista_urlopow,listaUrlop);
        dataAdapter = new ArrayAdapter<String>(this,R.layout.lista_urlopow,listaUrlop);

        final ListView listView = (ListView) findViewById(R.id.listViewUrlop);

        listView.setAdapter(dataAdapter);

        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String urlop =  (String) listView.getAdapter().getItem(position); //(TextView) view).getText();
                gotoListaUrlop(urlop, position);
            }
        });
    }

    public void gotoListaUrlop(String urlop, int position) {
        int poz = urlop.indexOf("*");
        Intent intent = new Intent(this, grupa3.com.niewolnik.Plan_urlop_poz.class);
        intent.putExtra("operacja", "edycja");
        intent.putExtra("pozycja",position);

        if ( poz > 0) {
            String urlPocz = urlop.substring(0, poz - 1).trim();
            String urlKon = urlop.substring(poz + 1).trim();
            intent.putExtra("pocz", urlPocz);
            intent.putExtra("kon", urlKon);
            intent.putExtra("ile_dat",2);
        }
        else {
            String urlPocz = urlop.trim();
            intent.putExtra("pocz", urlPocz);
            intent.putExtra("ile_dat",1);
            //String urlKon = "***";
            //intent.putExtra("kon", urlKon);
        }
        startActivityForResult(intent,2);
    }

    public void gotoSettingsU (View view) {
        Intent intent = new Intent(this, grupa3.com.niewolnik.Settings.class);
        startActivity(intent);
    }

    public void gotoNowyUrlop(View view) {
        Intent intent = new Intent(this, grupa3.com.niewolnik.Plan_urlop_poz.class);
        // startActivity(intent);
        intent.putExtra("operacja","dodanie");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode,  resultCode, intent);

        if (resultCode == Activity.RESULT_OK) {
            String operacja = intent.getStringExtra("operacja");
            int nrPoz = intent.getIntExtra("pozycja",0);
            int ile_dat = intent.getIntExtra("ile_dat",0);
            String lan = intent.getStringExtra("pocz");

            lan =  intent.getStringExtra("pocz");
            if (ile_dat > 1)
                lan += "   *   " +  intent.getStringExtra("kon");

            switch (operacja) {
                case "dodanie":
                    listaUrlop.add (lan );
                    dataAdapter.notifyDataSetChanged();
                    break;
                case "usuwanie":
                    listaUrlop.remove(nrPoz);
                    dataAdapter.notifyDataSetChanged();
                    break;
                case "zmiana":
                    listaUrlop.set(nrPoz,lan);
                    dataAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }

    }

 }
