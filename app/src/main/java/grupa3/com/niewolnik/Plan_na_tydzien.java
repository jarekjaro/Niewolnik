package grupa3.com.niewolnik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;
import java.io.IOException;
import java.util.*;

public class Plan_na_tydzien extends AppCompatActivity {

    // Plan na tydzien to  plan nr 1
    // Plan na miesiąc to plan nr 2
    private EditText pon, wto, sro, czw, pio;
    private TextView tytul;
    private String zmByte;
    private String rGodz = "";
    private int r_plan, godz = 0;
    private DB db_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_plan_na_tydzien);
        ustawTytulTydz ();

        pon = (EditText) findViewById(R.id.tydz_pon);
        wto = (EditText) findViewById(R.id.tydz_wto);
        sro = (EditText) findViewById(R.id.tydz_sro);
        czw = (EditText) findViewById(R.id.tydz_czwa);
        pio = (EditText) findViewById(R.id.tydz_pio);

        if (r_plan == 1) {
            try {
                zmByte = "";

                godz = db_manager.getDaySetting("mon");
                rGodz = String.valueOf(godz / 60);
                zmByte += rGodz;
                pon.setText(rGodz);

                godz = db_manager.getDaySetting("tue");
                rGodz = String.valueOf(godz / 60);
                zmByte += rGodz;
                wto.setText(rGodz);

                godz = db_manager.getDaySetting("wed");
                rGodz = String.valueOf(godz / 60);
                zmByte += rGodz;
                sro.setText(rGodz);

                godz = db_manager.getDaySetting("thu");
                rGodz = String.valueOf(godz / 60);
                zmByte += rGodz;
                czw.setText(rGodz);

                godz = db_manager.getDaySetting("fri");
                rGodz = String.valueOf(godz / 60);
                zmByte += rGodz;
                pio.setText(rGodz);

                Plan_obszar_wspolny.godzPlanu = rGodz.getBytes();

            } catch(NumberFormatException nfe) {
                System.out.println("Blad odczytu planu tygodniowego " + nfe);
            }
        }
    }


    public void gotoPlanNaMiesiac (View view) {
        zapPlanTyg();
        Intent intent = new Intent(this, grupa3.com.niewolnik.Plan_na_miesiac.class);
        startActivity(intent);
    }

    public void gotoSettings (View view) {
        zapPlanTyg();
        Intent intent = new Intent(this, grupa3.com.niewolnik.Settings.class);
        startActivity(intent);
    }

    public void ustawAktywnyPlanTydz (View view) {
      //  Calendar kalendarz = java.util.Calendar.getInstance();
      //  int zm1 = Calendar.getInstance().get(Calendar.YEAR);
      //  int zm2 = Calendar.getInstance().get(Calendar.MONTH);

      //  Plan_obszar_wspolny.aktywuj_plan(1,zm1, zm2);
        Plan_obszar_wspolny.aktywuj_plan(1, 0, 0);
        zapPlanTyg();
        ustawTytulTydz();
    }

    public void ustawTytulTydz () {
        tytul = (TextView) findViewById(R.id.textView61);
        String nazwa = Plan_obszar_wspolny.dajNazPlanu(1);
        r_plan = Plan_obszar_wspolny.daj_nr_planu ();
        tytul.setText(nazwa);
    }

    public void zapPlanTyg () {
        String s;

        try {
            godz = Integer.parseInt(pon.getText().toString().trim()) / 60;
            db_manager.updateSetting("mon", godz);

            godz = Integer.parseInt(pon.getText().toString().trim()) / 60;
            db_manager.updateSetting("tue", godz);
            godz = Integer.parseInt(pon.getText().toString().trim()) / 60;
            db_manager.updateSetting("wed", godz);
            godz = Integer.parseInt(pon.getText().toString().trim()) / 60;
            db_manager.updateSetting("thu", godz);
            godz = Integer.parseInt(pon.getText().toString().trim()) / 60;
            db_manager.updateSetting("fri", godz);

            Plan_obszar_wspolny.godzPlanu = rGodz.getBytes();

        } catch(NumberFormatException nfe) {
            System.out.println("Blad zapisu planu tygodniowego " + nfe);
        }
    }

    public void zapPlanTyg_pop () {
        String s;

        try {
            s = pon.getText().toString().trim();
            s += wto.getText().toString().trim();
            s += sro.getText().toString().trim();
            s += czw.getText().toString().trim();
            s += pio.getText().toString().trim();
            Plan_obszar_wspolny.godzPlanu = s.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*

Log.d("DB", " --------day setting----------");
Log.d("DB", Integer.toString(db_manager.getDaySetting("mon")));

db_manager.updateSetting("mon", 480);
db_manager.getDaySetting("mon")

 */