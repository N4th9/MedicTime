package year23.helha.project_medictime.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import year23.helha.project_medictime.models.Medoc;
import year23.helha.project_medictime.models.PrisesMedoc;
import year23.helha.project_medictime.models.PrisesMedocDb;

public class MedocCursorWrapper extends CursorWrapper {
    public MedocCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Medoc getMedoc() {
        String Nom = getString(getColumnIndex(MedocColDb.MedocTable.cols.TITLE));
        int DureeMedoc = getInt(getColumnIndex(MedocColDb.MedocTable.cols.DUREE));
        int setMatinDb = getInt(getColumnIndex(MedocColDb.MedocTable.cols.MATIN));
        int setMidiDb = getInt(getColumnIndex(MedocColDb.MedocTable.cols.MIDI));
        int setSoirDb = getInt(getColumnIndex(MedocColDb.MedocTable.cols.SOIR));

        Medoc medoc = new Medoc();
        medoc.setmName(Nom);
        medoc.setmDuree(String.valueOf(DureeMedoc));
        medoc.setMatin(setMatinDb != 0);
        medoc.setMidi(setMidiDb != 0);
        medoc.setSoir(setSoirDb != 0);
        return medoc;
    }

    public PrisesMedoc getPriseMedoc(){
        Integer id_medoc = getInt(getColumnIndex(MedocColDb.PriseTable.cols.MEDOC_ID));
        String DateDebut = getString(getColumnIndex(MedocColDb.PriseTable.cols.DEBUT));
        String DateFin = getString(getColumnIndex(MedocColDb.PriseTable.cols.FIN));
        int isMatin = getInt(getColumnIndex(MedocColDb.PriseTable.cols.MATIN));
        int isMidi = getInt(getColumnIndex(MedocColDb.PriseTable.cols.MIDI));
        int isSoir = getInt(getColumnIndex(MedocColDb.PriseTable.cols.SOIR));

        PrisesMedoc prise = new PrisesMedoc();
        prise.setMedocId(id_medoc);
        prise.setDebut(DateDebut);
        prise.setFin(DateFin);
        prise.setMatin(isMatin!=0);
        prise.setMidi(isMidi!=0);
        prise.setSoir(isSoir!=0);
        return prise;
    }
}