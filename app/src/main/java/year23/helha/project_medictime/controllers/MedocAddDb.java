package year23.helha.project_medictime.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import year23.helha.project_medictime.R;
import year23.helha.project_medictime.models.Medoc;
import year23.helha.project_medictime.models.MedocDb;

public class MedocAddDb extends AppCompatActivity {

    private Medoc medoc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medoc_db);
        NewMedocToDb();

        Button mValidateMedocToDb = findViewById(R.id.Confirm_New_Medoc_To_Db);

        mValidateMedocToDb.setOnClickListener(v -> {
            MedocDb.get(this).addMedoc(medoc);
            Intent intent = new Intent(MedocAddDb.this, MedocAddListView.class);
            startActivity(intent);
        });

        EditText mName = this.findViewById(R.id.TextToDb);
        EditText mDuration = this.findViewById(R.id.DureeToDb);
        CheckBox CheckBoxMorning = this.findViewById(R.id.CheckBox_Matin_Db);
        CheckBox CheckBoxMidi = this.findViewById(R.id.CheckBox_Midi_Db);
        CheckBox CheckBoxEvening = this.findViewById(R.id.CheckBox_Soir_Db);

        medoc = new Medoc();

        MedocInfo(mName, mDuration, CheckBoxMorning, CheckBoxMidi, CheckBoxEvening);
    }

    private void NewMedocToDb() {
        medoc = new Medoc();
        medoc.setmName("");
    }

    private void MedocInfo(EditText mName, EditText mDuration, CheckBox CheckBoxMorning, CheckBox CheckBoxMidi, CheckBox CheckBoxEvening) {
        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                medoc.setmName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                medoc.setmDuree(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        CheckBoxMorning.setOnClickListener(v -> medoc.setMatin(CheckBoxMorning.isChecked()));
        CheckBoxMidi.setOnClickListener(v -> medoc.setMidi(CheckBoxMidi.isChecked()));
        CheckBoxEvening.setOnClickListener(v -> medoc.setSoir(CheckBoxEvening.isChecked()));
    }
}
