package grupa3.com.niewolnik;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * Created by piotr on 2016-05-28.
 */
public  class  Plan_obszar_wspolny {
    private static int nr_planu = 0;
    private static int nr_mies = 0;
    private static int nr_roku = 0;
    public static String[] nazwa_planu = {"Plan tygodniowy","Plan miesięczny"};
    public static byte [] godzPlanu = {};

    public static String  dajNazPlanu (int nrPlanu) {
        String nazwa = nazwa_planu[nrPlanu - 1];

        if (nr_planu == nrPlanu)
            nazwa += " - " + "aktywny";

        return nazwa;
    }

    public static void  aktywuj_plan (int plan, int rok, int mies) {
         nr_planu = plan;
         nr_mies = mies;
         nr_roku = rok;
    }

    public static int daj_nr_planu () {
        return nr_planu;
    }
    public static int daj_nr_roku() {
        return nr_roku;
    }

    public static int daj_nr_mies() {
        return nr_mies;
    }
    public static void piszPreferencje(Context ctx, int nrPlanu, int nrRoku, int nrMies) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Niewolnik_Nr_Planu",nrPlanu);
        editor.putInt("Niewolnik_Nr_Roku", nrRoku);
        editor.putInt("Niewolnik,Nr_Mies", nrMies);
        editor.apply();

        zapiszPlan(godzPlanu,"NiewolnikPlan", ctx);
    }

    public static void dajPreferencje (Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        int prefPlan = prefs.getInt("Niewolnik_Nr_Planu", -1);
        int prefRok = prefs.getInt("Niewolnik_Nr_Roku", -1);
        int prefMies = prefs.getInt("Niewolnik_Nr_Mies", -1);

        if (prefPlan != -1) {
            aktywuj_plan(prefPlan, prefRok, prefMies);
            odczytajPlan("NiewolnikPlan",ctx);
        }
    }

    public static void zapiszPlan (byte[] dane, String nazwa, Context ctx) {
        try {
            FileOutputStream fileOut = ctx.openFileOutput (nazwa, ctx.MODE_PRIVATE);
            fileOut.write(dane);
            fileOut.close();
        } catch (IOException e){
          e.printStackTrace();
        }
    }

    public static void odczytajPlan (String nazwa, Context ctx) {
        byte[] dane = {};
        String cos = "";
        try {
            FileInputStream fileIn = ctx.openFileInput(nazwa);
            godzPlanu = new byte[fileIn.available()];
            while(fileIn.read(godzPlanu) != -1 ) {};
            fileIn.close();
            cos += new String(godzPlanu);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException  e) {
            e.printStackTrace();
        }

    }

    public static void zapiszPlanRob ( String nazwa, Context ctx) {
        String s = "12345";
        byte[] dane = {};
        try {
            dane = s.getBytes("UTF-8");
            FileOutputStream fileOut = ctx.openFileOutput (nazwa, ctx.MODE_PRIVATE);
            fileOut.write(dane);
            fileOut.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
