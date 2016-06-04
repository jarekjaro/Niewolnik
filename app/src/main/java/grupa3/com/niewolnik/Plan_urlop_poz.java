package grupa3.com.niewolnik;
   import android.app.Activity;
   import android.app.DatePickerDialog;
   import android.app.Dialog;
   import android.content.Intent;
   import android.support.v7.app.AppCompatActivity;
   import android.os.Bundle;
   import android.view.View;
   import android.view.Window;
   import android.widget.DatePicker;
   import android.widget.TextView;
   import android.widget.Toast;

   import java.text.SimpleDateFormat;
   import java.util.Calendar;
   import java.util.GregorianCalendar;

public class Plan_urlop_poz extends AppCompatActivity  {

    private TextView od_daty;
    private TextView do_daty;
    private Calendar kalendarz;
    private int rok_p, mies_p, dzien_p;
    private int rok_k, mies_k, dzien_k;
    private int rok_pocz, rok_kon;
    private long data_pocz, data_kon;
    private String operacja, robo;
    private int pozycja, ile_dat;

    private SimpleDateFormat formatDaty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_plan_urlop_poz);

    //    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //    dateFormat.setLenient(false);
    //    Calendar kalendarz = new GregorianCalendar();
    //    System.out.println(dateFormat.format(kalendarz.getTime()));

     //   public String weekdays[] = new      DateFormatSymbols(Locale.ENGLISH).getWeekdays();
     //   Calendar c = Calendar.getInstance();
     //   Date date = new Date();
     //   c.setTime(date);
     //   int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
     //   System.out.println(dayOfWeek);
     //   System.out.println(weekdays[dayOfWeek]);


        //  formatDaty = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        // Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        kalendarz = Calendar.getInstance();
        int zm1 = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int zm2 = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        od_daty = (TextView) findViewById(R.id.url_od_daty);
        do_daty = (TextView) findViewById(R.id.url_do_daty);

        operacja = getIntent().getStringExtra("operacja");
        pozycja  = getIntent().getIntExtra("pozycja",0);
        ile_dat  = getIntent().getIntExtra("ile_dat",0);

        if (operacja.equals("dodanie")) {
            rok_p = kalendarz.get(Calendar.YEAR);
            mies_p = kalendarz.get(Calendar.MONTH);
            dzien_p = kalendarz.get(Calendar.DAY_OF_MONTH);
            rok_k = kalendarz.get(Calendar.YEAR);
            mies_k = kalendarz.get(Calendar.MONTH);
            dzien_k = kalendarz.get(Calendar.DAY_OF_MONTH);
            pokazDateOd(rok_p, mies_p, dzien_p);
            pokazDateDo(rok_k, mies_k, dzien_k);
            data_pocz = (rok_p * 100) + (mies_p * 10) + dzien_p;
            data_kon  = (rok_k * 100) + (mies_k * 10) + dzien_k;
        }
        else {
            od_daty.setText(getIntent().getStringExtra("pocz"));

            rok_p = Integer.valueOf(od_daty.getText().toString().substring(0,4));
            mies_p = Integer.valueOf(od_daty.getText().toString().substring(5,7));
            dzien_p = Integer.valueOf(od_daty.getText().toString().substring(8,10));

            data_pocz = (rok_p * 100) + (mies_p * 10) + dzien_p;

            robo = getIntent().getStringExtra("kon");

            if ( ile_dat > 1) {
                do_daty.setText(getIntent().getStringExtra("kon"));
                rok_k = Integer.valueOf(do_daty.getText().toString().substring(0,4));
                mies_k = Integer.valueOf(do_daty.getText().toString().substring(5,7));
                dzien_k = Integer.valueOf(do_daty.getText().toString().substring(8,10));
                data_kon  = (rok_k * 100) + (mies_k * 10) + dzien_k;
            }
            else {
                // do_daty.setText("---- -- -- ");
                do_daty.setText(" ");
                rok_k = rok_p;
                mies_k = mies_p;
                dzien_k = dzien_p;
                data_kon  = data_pocz;
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        // Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();

    }
    public void setDateDo(View view) {
        showDialog(998);
        // Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected Dialog onCreateDialog ( int id) {
        if(id == 999) {
            return new DatePickerDialog(this, myListener, rok_p, mies_p-1, dzien_p);
        }
        if(id == 998) {
            return new DatePickerDialog(this, myListenerDo, rok_k, mies_k-1, dzien_k);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int rok, int mies, int dzien) {
            data_pocz = (rok * 100) + (mies * 10) + dzien;
            rok_pocz = rok;
            pokazDateOd(rok,mies+1, dzien);
        }
    };

    // https://developer.android.com/guide/topics/ui/controls/pickers.html

    private DatePickerDialog.OnDateSetListener myListenerDo = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int rok, int mies, int dzien) {
           // data_kon = (rok * 100) + (mies * 10) + dzien;
            rok_kon = rok;
            pokazDateDo(rok,mies+1, dzien);
        }
    };

    private void pokazDateOd(int rok, int mies, int dzien) {
        String r_mies = (mies < 10) ?  "0" + Integer.toString(mies) : Integer.toString(mies);
        String r_dzien = (dzien < 10) ?  "0" + Integer.toString(dzien) : Integer.toString(dzien);

        // od_daty.setText(new StringBuilder().append(rok).append("-").append(mies).append("-").append(dzien));
        od_daty.setText(new StringBuilder().append(rok).append("-").append(r_mies).append("-").append(r_dzien));
      }

    private void pokazDateDo(int rok, int mies, int dzien) {
        String r_mies = (mies < 10) ?  "0" + Integer.toString(mies) : Integer.toString(mies);
        String r_dzien = (dzien < 10) ?  "0" + Integer.toString(dzien) : Integer.toString(dzien);
        data_kon = (rok * 100) + (mies * 10) + dzien;
        // do_daty.setText(new StringBuilder().append(rok).append("-").append(mies).append("-").append(dzien));
        do_daty.setText(new StringBuilder().append(rok).append("-").append(r_mies).append("-").append(r_dzien));
    }

    public void zapisz_urlop(View view) {
        if (data_pocz > data_kon) {
            Toast.makeText(getApplicationContext(), "Poczatek późniejszy niz koniec ! ", Toast.LENGTH_LONG);
        }
        else {

            Intent intent = new Intent(this, grupa3.com.niewolnik.Plan_urlopow.class);
            intent.putExtra("operacja","zmiana");
            intent.putExtra("pozycja",pozycja);
            intent.putExtra("pocz", od_daty.getText().toString());

            if (do_daty.getText().toString().trim().isEmpty()) {
                ile_dat = 1;
            }
            else {
                robo = do_daty.getText().toString().trim();
                if (robo.equals(od_daty.getText().toString().trim()))
                    ile_dat = 1;
                else {
                    ile_dat = 2;
                    intent.putExtra("kon", do_daty.getText().toString());
                }
            }
            intent.putExtra("ile_dat", ile_dat);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    public void usun(View view) {
        Intent intent = new Intent (this, grupa3.com.niewolnik.Plan_urlopow.class);
        intent.putExtra("operacja","usuwanie");
        intent.putExtra("pocz","***");
        intent.putExtra("pozycja",pozycja);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void porzuc (View view) {
        Intent intent = new Intent(this, grupa3.com.niewolnik.Plan_urlopow.class);
        //intent.putExtra("operacja", "porzuc");
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
