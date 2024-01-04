package year23.helha.project_medictime.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import year23.helha.project_medictime.R;
import year23.helha.project_medictime.models.Medoc;
import year23.helha.project_medictime.models.MedocDb;

public class MedocAddDb extends AppCompatActivity {

    protected Medoc medoc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medoc_db);
        NewMedocToDb();

        Button mValiderMedocToDb = findViewById(R.id.Confirm_New_Medoc_To_Db);

        mValiderMedocToDb.setOnClickListener(v -> {
            MedocDb.get(this).addMedoc(medoc);
            Intent intent = new Intent(MedocAddDb.this, MedocAddListView.class);
            startActivity(intent);
        });

        EditText mName = findViewById(R.id.TextToDb);
        EditText mDuree = this.findViewById(R.id.DureeToDb);
        CheckBox CheckBoxMatin = this.findViewById(R.id.CheckBox_Matin_Db);
        CheckBox CheckBoxMidi = this.findViewById(R.id.CheckBox_Midi_Db);
        CheckBox CheckBoxSoir = this.findViewById(R.id.CheckBox_Soir_Db);

        medoc = new Medoc();

        InfosMedoc(mName, mDuree, CheckBoxMatin, CheckBoxMidi, CheckBoxSoir);
    }

    private void NewMedocToDb() {
        medoc = new Medoc();
        medoc.setmName("");
    }

    private void InfosMedoc(EditText mName, EditText mDuree, CheckBox CheckBoxMatin, CheckBox CheckBoxMidi, CheckBox CheckBoxSoir) {
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
        mDuree.addTextChangedListener(new TextWatcher() {
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
        CheckBoxMatin.setOnClickListener(v -> medoc.setMatin(CheckBoxMatin.isChecked()));
        CheckBoxMidi.setOnClickListener(v -> medoc.setMidi(CheckBoxMidi.isChecked()));
        CheckBoxSoir.setOnClickListener(v -> medoc.setSoir(CheckBoxSoir.isChecked()));
    }
}
