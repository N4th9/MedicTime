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
import android.widget.CompoundButton;
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

    private MedocCursorWrapper mMedocCursorWrapper;
    protected PrisesMedoc prisesMedoc;

    @SuppressLint("Range")
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        prisesMedoc = new PrisesMedoc();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_listview);
        Button mAddMedoc = this.findViewById(R.id.goToMedocDb);
        TextView mDateDebut = this.findViewById(R.id.Date_Debut);
        TextView mDateFin = this.findViewById(R.id.Date_Fin);
        CheckBox mCBMatinList = this.findViewById(R.id.CheckBox_Matin_To_List);
        CheckBox mCBMidiList = this.findViewById(R.id.CheckBox_Midi_To_List);
        CheckBox mCBSoirList = this.findViewById(R.id.CheckBox_Soir_To_List);
        Spinner spinnerMedocs = findViewById(R.id.Spinner_Medocs);
        Button mGoToList = findViewById(R.id.Confirm_Add_Medoc_To_List);

        SQLiteDatabase mDataBase = new MedocHelper(this).getWritableDatabase();
        String query = "SELECT * FROM " + MedocColDb.MedocTable.NAME;
        Cursor cursor = mDataBase.rawQuery(query, null);
        mMedocCursorWrapper = new MedocCursorWrapper(cursor);
        List<Medoc> medocs = new ArrayList<>();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                medocs.add(mMedocCursorWrapper.getMedoc());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        ArrayAdapter<Medoc> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, medocs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMedocs.setAdapter(adapter);
        cursor.getCount();
        spinnerMedocs.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {
                Medoc selectedMedoc = medocs.get(position);
                mCBMatinList.setChecked(selectedMedoc.isMatin());
                mCBMidiList.setChecked(selectedMedoc.isMidi());
                mCBSoirList.setChecked(selectedMedoc.isSoir());
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
        ShowMedocsOnListView(mCBMatinList, mCBMidiList, mCBSoirList);
    }

    private void ShowMedocsOnListView(CheckBox mCBMatinList, CheckBox mCBMidiList, CheckBox mCBSoirList) {
        mCBMatinList.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (prisesMedoc != null) {
                prisesMedoc.setMatin(isChecked);
            }
        });
        mCBMidiList.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (prisesMedoc != null) {
                prisesMedoc.setMidi(isChecked);
            }
        });
        mCBSoirList.setOnCheckedChangeListener((compoundButton, isChecked) -> {
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