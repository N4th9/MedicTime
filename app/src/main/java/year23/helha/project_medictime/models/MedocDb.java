package year23.helha.project_medictime.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import year23.helha.project_medictime.db.MedocColDb;
import year23.helha.project_medictime.db.MedocCursorWrapper;
import year23.helha.project_medictime.db.MedocHelper;

public class MedocDb {
    private static MedocDb sMedocDb;
    private Context mContext;
    private final SQLiteDatabase mDataBase;

    public MedocDb(Context context){
        mContext = context;
        mDataBase = new MedocHelper(mContext).getWritableDatabase();
    }

    public static MedocDb get(Context context) {
        if(sMedocDb == null){
            sMedocDb = new MedocDb(context);
        }
        return sMedocDb;
    }

    public void addMedoc(Medoc medoc){
        mDataBase.insert(MedocColDb.MedocTable.NAME, null, getMedocValues(medoc));
    }

    public List<Medoc> getMedocs(){
        ArrayList<Medoc> Medocs = new ArrayList<>();
        MedocCursorWrapper cursor = queryMedocs();
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Medocs.add(cursor.getMedoc());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return Medocs;
    }

    private MedocCursorWrapper queryMedocs() {
        android.database.Cursor cursor = mDataBase.query(
                MedocColDb.MedocTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return new MedocCursorWrapper(cursor);
    }

    private ContentValues getMedocValues(Medoc medoc){
        ContentValues values = new ContentValues();
        values.put(MedocColDb.MedocTable.cols.TITLE, medoc.getmName());
        values.put(MedocColDb.MedocTable.cols.DUREE, medoc.getmDuree());
        values.put(MedocColDb.MedocTable.cols.MATIN, medoc.isMatin());
        values.put(MedocColDb.MedocTable.cols.MIDI, medoc.isMidi());
        values.put(MedocColDb.MedocTable.cols.SOIR, medoc.isSoir());
        return values;
    }

    public String getMedocName(Integer id) {
        MedocCursorWrapper cursor = queryMedocs();
        try {
            if (cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getMedoc().getmName();
        } finally {
            cursor.close();
        }
    }
}