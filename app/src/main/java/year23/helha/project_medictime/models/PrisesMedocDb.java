package year23.helha.project_medictime.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import year23.helha.project_medictime.db.MedocColDb;
import year23.helha.project_medictime.db.MedocCursorWrapper;
import year23.helha.project_medictime.db.MedocHelper;

public class PrisesMedocDb {
    private static PrisesMedocDb sPriseMedocDb;
    private Context mContext;
    private final SQLiteDatabase mDataBase;

    public PrisesMedocDb(Context context){
        mContext = context;
        mDataBase = new MedocHelper(mContext).getWritableDatabase();
    }

    public static PrisesMedocDb get(Context context) {
        if(sPriseMedocDb == null){
            sPriseMedocDb = new PrisesMedocDb(context);
        }
        return sPriseMedocDb;
    }

    public void addMedoc(PrisesMedoc prise){
        mDataBase.insert(MedocColDb.PriseTable.NAME, null, getPrisesValues(prise));
    }

    public List<PrisesMedoc> getPrises(){
        ArrayList<PrisesMedoc> Prises = new ArrayList<>();
        MedocCursorWrapper cursor = queryPrises(null, null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Prises.add(cursor.getPriseMedoc());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return Prises;
    }

    private MedocCursorWrapper queryPrises(Object o, Object o1) {
        android.database.Cursor cursor = mDataBase.query(
                MedocColDb.PriseTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return new MedocCursorWrapper(cursor);
    }

    private ContentValues getPrisesValues(PrisesMedoc prise){
        ContentValues values = new ContentValues();
        values.put(MedocColDb.PriseTable.cols.MEDOC_ID, prise.getMedocId());
        values.put(MedocColDb.PriseTable.cols.DEBUT, prise.getDebut());
        values.put(MedocColDb.PriseTable.cols.FIN, prise.getFin());
        values.put(MedocColDb.PriseTable.cols.MATIN, prise.isMatin());
        values.put(MedocColDb.PriseTable.cols.MIDI, prise.isMidi());
        values.put(MedocColDb.PriseTable.cols.SOIR, prise.isSoir());
        return values;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<String> getTousLesJoursPossibles() {
        List<String> tousLesJours = new ArrayList<>();
        Set<String> joursSet = new HashSet<>();
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        }

        List<PrisesMedoc> allPrises = getPrises();

        for (PrisesMedoc prise : allPrises) {
            try {
                LocalDate dateDebut = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    dateDebut = LocalDate.parse(prise.getDebut(), formatter);
                }
                LocalDate dateFin = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    dateFin = LocalDate.parse(prise.getFin(), formatter);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    while (dateDebut.isBefore(dateFin)) {
                        String jour = dateDebut.format(formatter);

                        if (!joursSet.contains(jour)) {
                            tousLesJours.add(jour);
                            joursSet.add(jour);
                        }
                        dateDebut = dateDebut.plusDays(1);
                    }
                }
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        }
        return tousLesJours;

    }

    public List<PrisesMedoc> getPrisesByJour(String jour) {
        List<PrisesMedoc> prisesDuJour = new ArrayList<>();
        List<UUID> prisesDejaAjoutees = new ArrayList<>();

        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        }

        try {
            LocalDate dateToFilter = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dateToFilter = LocalDate.parse(jour, formatter);
            }

            List<PrisesMedoc> allPrises = getPrises();

            for (PrisesMedoc prise : allPrises) {
                LocalDate debut = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    debut = LocalDate.parse(prise.getDebut(), formatter);
                }
                LocalDate fin = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    fin = LocalDate.parse(prise.getFin(), formatter);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (!prisesDejaAjoutees.contains(prise.getId()) && !dateToFilter.isBefore(debut) && !dateToFilter.isAfter(fin)) {
                        prisesDuJour.add(prise);
                        prisesDejaAjoutees.add(prise.getId());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return prisesDuJour;
    }
}
