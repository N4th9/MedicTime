package year23.helha.project_medictime.controllers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.List;

import year23.helha.project_medictime.R;
import year23.helha.project_medictime.models.MedocDb;
import year23.helha.project_medictime.models.PrisesMedoc;
import year23.helha.project_medictime.models.PrisesMedocDb;

public class ListViewDaysMedoc extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_medoc);
        Button GoToSpinner = findViewById(R.id.GoToSpinner);

        GoToSpinner.setOnClickListener(v -> {
            Intent intent = new Intent(ListViewDaysMedoc.this, MedocAddListView.class);
            startActivity(intent);
        });

        LinearLayout mContainer = findViewById(R.id.ContainerMedoc);
        mContainer.removeAllViews();
        PrisesMedocDb lab = PrisesMedocDb.get(this);
        List<String> Days = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Days = lab.getTousLesJoursPossibles();
        }
        for (String jour : Days) {
            List<PrisesMedoc> prises = lab.getPrisesByJour(jour);
            View jourFrag = getLayoutInflater().inflate(R.layout.fragment_medocs, null);
            ((TextView) jourFrag.findViewById(R.id.DayMedoc)).setText(jour);
            LinearLayout MatinContainer = jourFrag.findViewById(R.id.Matin_Container);
            LinearLayout MidiContainer = jourFrag.findViewById(R.id.Midi_Container);
            LinearLayout SoirContainer = jourFrag.findViewById(R.id.Soir_Container);
            MatinContainer.removeAllViews();
            MidiContainer.removeAllViews();
            SoirContainer.removeAllViews();

            for (final PrisesMedoc prise : prises) {
                if (prise.isMatin()) {
                    MatinContainer.addView(getPriseView(prise));
                }
                if (prise.isMidi()) {
                    MidiContainer.addView(getPriseView(prise));
                }
                if (prise.isSoir()) {
                    SoirContainer.addView(getPriseView(prise));
                }
            }
            mContainer.addView(jourFrag);
        }
    }

    private View getPriseView(PrisesMedoc prise) {
        TextView textView = new TextView(getApplicationContext());
        MedocDb lab = MedocDb.get(this);
        String medocName= lab.getMedocName(prise.getMedocId());
        textView.setText(medocName);
        return textView;
    }
}
