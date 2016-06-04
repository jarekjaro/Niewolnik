package grupa3.com.niewolnik;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;

public class Plan_na_miesiac extends AppCompatActivity {

    // Plan na tydzien to  plan nr 1
    // Plan na miesiąc to plan nr 2

    private int pierDzienMies ;
    private int ileDniWMies;
    private String rGodz = "";
    private int r_plan;
    private TextView tytul;

    private int [][] tabIdPol =
            {
                    {R.id.pon_1, R.id.wto_1, R.id.sro_1, R.id.czw_1, R.id.pio_1, R.id.sob_1, R.id.nie_1},
                    {R.id.pon_2, R.id.wto_2, R.id.sro_2, R.id.czw_2, R.id.pio_2, R.id.sob_2, R.id.nie_2},
                    {R.id.pon_3, R.id.wto_3, R.id.sro_3, R.id.czw_3, R.id.pio_3, R.id.sob_3, R.id.nie_3},
                    {R.id.pon_4, R.id.wto_4, R.id.sro_4, R.id.czw_4, R.id.pio_4, R.id.sob_4, R.id.nie_4},
                    {R.id.pon_5, R.id.wto_5, R.id.sro_5, R.id.czw_5, R.id.pio_5, R.id.sob_5, R.id.nie_5},
                    {R.id.pon_6, R.id.wto_6, R.id.sro_6, R.id.czw_6, R.id.pio_6, R.id.sob_6, R.id.nie_6}
            };
    private int [][] tabNrPol =
            {
                {R.id.nr_1, R.id.nr_2,  R.id.nr_3,  R.id.nr_4,  R.id.nr_5,  R.id.nr_6,  R.id.nr_7},
                {R.id.nr_8, R.id.nr_9,  R.id.nr_10, R.id.nr_11, R.id.nr_12, R.id.nr_13, R.id.nr_14},
                {R.id.nr_15,R.id.nr_16, R.id.nr_17, R.id.nr_18, R.id.nr_19, R.id.nr_20, R.id.nr_21},
                {R.id.nr_22,R.id.nr_23, R.id.nr_24, R.id.nr_25, R.id.nr_26, R.id.nr_27, R.id.nr_28},
                {R.id.nr_29,R.id.nr_30, R.id.nr_31, R.id.nr_32, R.id.nr_33, R.id.nr_34, R.id.nr_35},
                {R.id.nr_36,R.id.nr_37, R.id.nr_38, R.id.nr_39, R.id.nr_40, R.id.nr_41, R.id.nr_42}
            };
    private String [] tydzOdczyt = {"","","","","","",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_na_miesiac);

        dajDniMies ();
        ustawTytulMies();
        nr_dni_na_ekranie();

        if (r_plan == 2) {
              try {
                  rGodz = new String(Plan_obszar_wspolny.godzPlanu, "UTF-8");
              } catch (Exception e) {
                  e.printStackTrace();
              }
            zapis_na_ekranie();
        }
    }

    public void gotoSettings (View view) {
        Intent intent = new Intent (this, grupa3.com.niewolnik.Settings.class);
        startActivity(intent);
    }

    public void gotoPlanTygodniowy (View view) {
        Intent intent = new Intent (this, grupa3.com.niewolnik.Plan_na_tydzien.class);
        startActivity(intent);
    }

    public void ustawAktywnyPlanMies (View view) {
        Calendar kalendarz = java.util.Calendar.getInstance();
        int zm1 = kalendarz.get(Calendar.YEAR);
        int zm2 = kalendarz.get(Calendar.MONTH);

        Plan_obszar_wspolny.aktywuj_plan(1,zm1, zm2);

        zapPlanMies();
        ustawTytulMies();
    }
    public void ustawTytulMies () {
        tytul = (TextView) findViewById(R.id.textView13);
        String nazwa = Plan_obszar_wspolny.dajNazPlanu(2);
        tytul.setText(nazwa);
    }

    public void zapPlanMies() {
        odczyt_z_ekranu();
        try {
            Plan_obszar_wspolny.godzPlanu = rGodz.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private  void dajDniMies () {
        Calendar kal = Calendar.getInstance();
        kal.set(Calendar.DAY_OF_MONTH,1);
        pierDzienMies  = kal.get(Calendar.DAY_OF_WEEK) - 1;
        ileDniWMies = kal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public void zapis_na_ekranie() {
        EditText  pole;
        int wsk;
        TextView  nr_pola;
        String nr_dnia;

        for (int i=0; i < 6; i++) {
            for(int j=0; j < 7; j++) {
                pole = (EditText) findViewById(tabIdPol[i][j]);
                wsk = i*7 + j;
                if (! rGodz.substring(wsk,wsk+1).equals("0")) pole.setText(rGodz.substring(wsk,wsk+1));

             //   if (! tydzOdczyt[i*7+j].equals("0")) pole.setText(tydzOdczyt[i*7+j]);
             //   if ( pierDzienMies < i*7+j && ileDniWMies > i*7+j) {
             //      nr_pola = (TextView) findViewById(tabNrPol[i][j]);
             //      nr_dnia = String.valueOf(i*7+j);
             //      nr_pola.setText(nr_dnia);
             //   }
            }
        }
    }

    public void nr_dni_na_ekranie() {
        EditText  pole;
        TextView  nr_pola;
        String nr_dnia;
        int nrKol = 0;

        for (int i=0; i < 6; i++) {
            for(int j=0; j < 7; j++) {
                if ( pierDzienMies < i*7+j && ileDniWMies > nrKol) {       //i*7+j) {
                    nr_pola = (TextView) findViewById(tabNrPol[i][j]);
                    nr_dnia = String.valueOf(i*7+j);
                    nr_pola.setText(nr_dnia);
                    nrKol++;
                }
            }
        }
    }

    public void odczyt_z_ekranu() {
        EditText  pole;

       // for ( int wsk = 0; wsk < 7; wsk++)
       //     tydzOdczyt[wsk] = "0";

        rGodz = "";

        for (int i=0; i < 6; i++) {
            for(int j=0; j < 7; j++) {
                pole = (EditText) findViewById(tabIdPol[i][j]);
                if (! pole.getText().toString().trim().isEmpty())  rGodz += pole.getText().toString().trim();
                //if (! pole.getText().toString().trim().isEmpty())  tydzOdczyt[i*7+j] = pole.getText().toString().trim();
            }
        }
    }


}
