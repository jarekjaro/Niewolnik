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
    private byte zmByte;
    private String rGodz = "";
    private int r_plan;

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
                rGodz = new String(Plan_obszar_wspolny.godzPlanu, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (rGodz.length() > 0) {
                pon.setText(rGodz.substring(0, 1));
                wto.setText(rGodz.substring(1, 2));
                sro.setText(rGodz.substring(2, 3));
                czw.setText(rGodz.substring(3, 4));
                pio.setText(rGodz.substring(4, 5));
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
