package grupa3.com.niewolnik;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by piotr on 2016-06-03.
 */
public class smietnik {

    private EditText pon_1, pon_2, pon_3, pon_4, pon_5, pon_6;
    private EditText wto_1, wto_2, wto_3, wto_4, wto_5, wto_6;
    private EditText sro_1, sro_2, sro_3, sro_4, sro_5, sro_6;
    private EditText czw_1, czw_2, czw_3, czw_4, czw_5, czw_6;
    private EditText pio_1, pio_2, pio_3, pio_4, pio_5, pio_6;
    private EditText sob_1, sob_2, sob_3, sob_4, sob_5, sob_6;
    private EditText nie_1, nie_2, nie_3, nie_4, nie_5, nie_6;

    private TextView nr_1,  nr_2,  nr_3,  nr_4,  nr_5,  nr_6,  nr_7;
    private TextView nr_8,  nr_9,  nr_10, nr_11, nr_12, nr_13, nr_14;
    private TextView nr_15, nr_16, nr_17, nr_18, nr_19, nr_20, nr_21;
    private TextView nr_22, nr_23, nr_24, nr_25, nr_26, nr_27, nr_28;
    private TextView nr_29, nr_30, nr_31, nr_32, nr_33, nr_34, nr_35;
    private TextView nr_36, nr_37, nr_38, nr_39, nr_40, nr_41, nr_42;

    private int[] t1 = {R.id.pon_1,R.id.wto_1, R.id.sro_1, R.id.czw_1, R.id.pio_1, R.id.sob_1, R.id.nie_1};
    private int[] t12 = {R.id.pon_2,R.id.wto_2, R.id.sro_2, R.id.czw_2, R.id.pio_2, R.id.sob_2, R.id.nie_2};
    private int[] t13 = {R.id.pon_3,R.id.wto_3, R.id.sro_3, R.id.czw_3, R.id.pio_3, R.id.sob_3, R.id.nie_3};
    private int[] t14 = {R.id.pon_4,R.id.wto_4, R.id.sro_4, R.id.czw_4, R.id.pio_4, R.id.sob_4, R.id.nie_4};
    private int[] t15 = {R.id.pon_5,R.id.wto_5, R.id.sro_5, R.id.czw_5, R.id.pio_5, R.id.sob_5, R.id.nie_5};
    private int[] t16 = {R.id.pon_6,R.id.wto_6, R.id.sro_6, R.id.czw_6, R.id.pio_6, R.id.sob_6, R.id.nie_6};


    private String[] t2 = {"0","0","0","8", "7", "6","0"};

    private int[] t3 = {R.id.nr_1, R.id.nr_2,  R.id.nr_3,  R.id.nr_4,  R.id.nr_5,  R.id.nr_6,  R.id.nr_7};
    private int[] t32 = {R.id.nr_8, R.id.nr_9,  R.id.nr_10, R.id.nr_11, R.id.nr_12, R.id.nr_13, R.id.nr_14};
    private int[] t33 = {R.id.nr_15,R.id.nr_16, R.id.nr_17, R.id.nr_18, R.id.nr_19, R.id.nr_20, R.id.nr_21};
    private int[] t34 = {R.id.nr_22,R.id.nr_23, R.id.nr_24, R.id.nr_25, R.id.nr_26, R.id.nr_27, R.id.nr_28};
    private int[] t35 = {R.id.nr_29,R.id.nr_30, R.id.nr_31, R.id.nr_32, R.id.nr_33, R.id.nr_34, R.id.nr_35};
    private int[] t36 = {R.id.nr_36,R.id.nr_37, R.id.nr_38, R.id.nr_39, R.id.nr_40, R.id.nr_41, R.id.nr_42};


    private String[] t4 = {" "," "," ","1", "2", "3", "4"};
/*
    private void tydzien_zapis__() {
        EditText pon, wto, sro, czw, pio, sob, nie;
        TextView nr_1, nr_2, nr_3, nr_4, nr_5, nr_6, nr_7;

        pon = (EditText) findViewById(t1[0]);
        wto = (EditText) findViewById(t1[1]);
        sro = (EditText) findViewById(t1[2]);
        czw = (EditText) findViewById(t1[3]);
        pio = (EditText) findViewById(t1[4]);
        sob = (EditText) findViewById(t1[5]);;
        nie = (EditText) findViewById(t1[5]);;

        //  if ( rGodz.length() > 0) {
        //      pon_1.setText(rGodz.substring(0, 1));
        //      wto_1.setText(rGodz.substring(1, 2));
        //  }
        if (! t2[0].equals("0"))  pon.setText(t2[0]);
        if (! t2[1].equals("0"))  wto.setText(t2[1]);
        if (! t2[2].equals("0"))  sro.setText(t2[2]);
        if (! t2[3].equals("0"))  czw.setText(t2[3]);
        if (! t2[4].equals("0"))  pio.setText(t2[4]);
        if (! t2[5].equals("0"))  sob.setText(t2[5]);
        if (! t2[6].equals("0"))  nie.setText(t2[5]);

        nr_1 = (TextView) findViewById(t3[0]);
        nr_1.setText(t4[0]);
        nr_2 = (TextView) findViewById(t3[1]);
        nr_2.setText(t4[1]);
        nr_3 = (TextView) findViewById(t3[2]);
        nr_3.setText(t4[2]);
        nr_4 = (TextView) findViewById(t3[3]);
        nr_4.setText(t4[3]);
        nr_5 = (TextView) findViewById(t3[4]);
        nr_5.setText(t4[4]);
        nr_6 = (TextView) findViewById(t3[5]);
        nr_6.setText(t4[5]);
        nr_7 = (TextView) findViewById(t3[6]);
        nr_7.setText(t4[6]);

        //s = pon_1.getText().toString().trim();
        //s += wto_1.getText().toString().trim();
    }

    public void tydzien_odczyt__() {
        EditText  pon, wto, sro, czw, pio, sob, nie;

        pon = (EditText) findViewById(t1[0]);
        wto = (EditText) findViewById(t1[1]);
        sro = (EditText) findViewById(t1[2]);
        czw = (EditText) findViewById(t1[3]);
        pio = (EditText) findViewById(t1[4]);
        sob = (EditText) findViewById(t1[5]);;
        nie = (EditText) findViewById(t1[5]);;

        for ( int wsk = 0; wsk < 7; wsk++)
            tydzOdczyt[wsk] = "0";

        if (! pon.getText().toString().trim().isEmpty())  tydzOdczyt[0] = pon.getText().toString().trim();
        if (! wto.getText().toString().trim().isEmpty())  tydzOdczyt[0] = wto.getText().toString().trim();
        if (! sro.getText().toString().trim().isEmpty())  tydzOdczyt[0] = sro.getText().toString().trim();
        if (! czw.getText().toString().trim().isEmpty())  tydzOdczyt[0] = czw.getText().toString().trim();
        if (! pio.getText().toString().trim().isEmpty())  tydzOdczyt[0] = pio.getText().toString().trim();
        if (! sob.getText().toString().trim().isEmpty())  tydzOdczyt[0] = sob.getText().toString().trim();
        if (! nie.getText().toString().trim().isEmpty())  tydzOdczyt[0] = nie.getText().toString().trim();
    }
    */
}
