package year23.helha.project_medictime.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.AccessControlContext;

public class MedocHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "MedocBase.db";

    public MedocHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MedocColDb.MedocTable.NAME + "("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MedocColDb.MedocTable.cols.TITLE + ", "
                + MedocColDb.MedocTable.cols.DUREE + ", "
                + MedocColDb.MedocTable.cols.MATIN + ", "
                + MedocColDb.MedocTable.cols.MIDI + ", "
                + MedocColDb.MedocTable.cols.SOIR + ")"
        );
        db.execSQL("CREATE TABLE " + MedocColDb.PriseTable.NAME + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MedocColDb.PriseTable.cols.MEDOC_ID + " INTEGER, " // Modification ici
                + MedocColDb.PriseTable.cols.DEBUT + ", "
                + MedocColDb.PriseTable.cols.FIN + ", "
                + MedocColDb.PriseTable.cols.MATIN + ", "
                + MedocColDb.PriseTable.cols.MIDI + ", "
                + MedocColDb.PriseTable.cols.SOIR + ", "
                + "FOREIGN KEY (" + MedocColDb.PriseTable.cols.MEDOC_ID + ") REFERENCES "
                + MedocColDb.MedocTable.NAME + "(ID))"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}