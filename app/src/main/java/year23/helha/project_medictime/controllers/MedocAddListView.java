package year23.helha.project_medictime.controllers;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import year23.helha.project_medictime.R;
import year23.helha.project_medictime.db.MedocColDb;
import year23.helha.project_medictime.db.MedocCursorWrapper;
import year23.helha.project_medictime.db.MedocHelper;
import year23.helha.project_medictime.models.Medoc;
import year23.helha.project_medictime.models.PrisesMedoc;
import year23.helha.project_medictime.models.PrisesMedocDb;

public class MedocAddListView extends AppCompatActivity {

    private PrisesMedoc prisesMedoc;

    @SuppressLint("Range")
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        prisesMedoc = new PrisesMedoc();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_listview);
        Button mAddMedoc = this.findViewById(R.id.goToMedocDb);
        TextView mDateDebut = this.findViewById(R.id.Date_Debut);
        TextView mDateFin = this.findViewById(R.id.Date_Fin);
        CheckBox mCBMorningList = this.findViewById(R.id.CheckBox_Matin_To_List);
        CheckBox mCBMidiList = this.findViewById(R.id.CheckBox_Midi_To_List);
        CheckBox mCBEveningList = this.findViewById(R.id.CheckBox_Soir_To_List);
        Spinner spinnerMedoc = this.findViewById(R.id.Spinner_Medocs);
        Button mGoToList = this.findViewById(R.id.Confirm_Add_Medoc_To_List);

        SQLiteDatabase mDataBase = new MedocHelper(this).getWritableDatabase();
        String query = "SELECT * FROM " + MedocColDb.MedocTable.NAME;
        Cursor cursor = mDataBase.rawQuery(query, null);
        MedocCursorWrapper mMedocCursorWrapper = new MedocCursorWrapper(cursor);
        List<Medoc> medoc = new ArrayList<>();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                medoc.add(mMedocCursorWrapper.getMedoc());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        ArrayAdapter<Medoc> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, medoc);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMedoc.setAdapter(adapter);
        cursor.getCount();
        spinnerMedoc.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {
                Medoc selectedMedoc = medoc.get(position);
                mCBMorningList.setChecked(selectedMedoc.isMatin());
                mCBMidiList.setChecked(selectedMedoc.isMidi());
                mCBEveningList.setChecked(selectedMedoc.isSoir());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mAddMedoc.setOnClickListener(event -> {
            Intent intent = new Intent(MedocAddListView.this, MedocAddDb.class);
            startActivity(intent);
        });
        mGoToList.setOnClickListener(event -> {
            Intent intent = new Intent(MedocAddListView.this, ListViewDaysMedoc.class);
            startActivity(intent);

            if (prisesMedoc != null) {
                PrisesMedocDb prisesMedocDb = PrisesMedocDb.get(this);
                if (prisesMedoc.getMedocId() != null) {
                    prisesMedocDb.addMedoc(prisesMedoc);
                } else {
                    Log.e("MedocAddListView", "MedocId is null");
                }
            } else {
                Log.e("MedocAddListView", "PrisesMedoc is null");
            }
        });

        ShowDateDebut(mDateDebut);
        ShowFinPickerDialog(mDateFin);
        ShowMedocOnListView(mCBMorningList, mCBMidiList, mCBEveningList);
    }

    private void ShowMedocOnListView(CheckBox mCBMorningList, CheckBox mCBMidiList, CheckBox mCBEveningList) {
        mCBMorningList.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (prisesMedoc != null) {
                prisesMedoc.setMatin(isChecked);
            }
        });
        mCBMidiList.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (prisesMedoc != null) {
                prisesMedoc.setMidi(isChecked);
            }
        });
        mCBEveningList.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (prisesMedoc != null) {
                prisesMedoc.setSoir(isChecked);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void ShowDateDebut(TextView mDateDebut) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        mDateDebut.setText(day + "/" + (month + 1) + "/" + year);
        @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(MedocAddListView.this,
                (datePicker, year1, month1, day1) -> {
                    mDateDebut.setText(day1 + "/" + (month1 + 1) + "/" + year1);
                },
                year, month, day
        );
    }

    @SuppressLint("SetTextI18n")
    private void ShowFinPickerDialog(TextView dateTextView) {
        dateTextView.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            dateTextView.setText(day + "/" + (month + 1) + "/" + year);
            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(MedocAddListView.this,
                    (datePicker, year1, month1, day1) -> {
                        dateTextView.setText(day1 + "/" + (month1 + 1) + "/" + year1);
                    },
                    year, month, day
            );
            datePickerDialog.show();
            Log.i("Date", "Date: " + dateTextView.getText().toString());
        });
    }
}