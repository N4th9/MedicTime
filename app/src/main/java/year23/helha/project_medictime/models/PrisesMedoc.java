package year23.helha.project_medictime.models;

import android.os.Build;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PrisesMedoc {
    private static int lastMedocId = 0;

    public UUID getId() {
        return mId;
    }

    public void setId(UUID mId) {
        this.mId = mId;
    }

    public Integer getMedocId() {
        return mMedocId;
    }

    public void setMedocId(Integer mMedocId) {
        this.mMedocId = mMedocId;
    }

    public String getDebut() {
        return mDebut;
    }

    public void setDebut(String mDebut) {
        this.mDebut = mDebut;
    }

    public String getFin() {
        return mFin;
    }

    public void setFin(String mFin) {
        this.mFin = mFin;
    }

    public Boolean isMatin() {
        return mMatin;
    }

    public void setMatin(Boolean mMatin) {
        this.mMatin = mMatin;
    }

    public Boolean isMidi() {
        return mMidi;
    }

    public void setMidi(Boolean mMidi) {
        this.mMidi = mMidi;
    }

    public Boolean isSoir() {
        return mSoir;
    }

    public void setSoir(Boolean mSoir) {
        this.mSoir = mSoir;
    }

    protected java.util.UUID mId;
    protected Integer mMedocId;
    protected String mDebut;
    protected String mFin;
    protected Boolean mMatin;
    protected Boolean mMidi;
    protected Boolean mSoir;

    public PrisesMedoc() {
        mMedocId = ++lastMedocId;
        this.mDebut="20/10/23";
        this.mFin="30/10/23";
        this.mMatin = false;
        this.mMidi = false;
        this.mSoir = false;
    }

    public PrisesMedoc(UUID id) {
        mId = id;
    }

    public List<String> getTousLesJours() {
        List<String> jours = new ArrayList<>();

        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
        }

        LocalDate dateDebut = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateDebut = LocalDate.parse(mDebut, formatter);
        }
        LocalDate dateFin = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateFin = LocalDate.parse(mFin, formatter);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            while (!dateDebut.isAfter(dateFin)) {
                jours.add(dateDebut.format(formatter));
                dateDebut = dateDebut.plusDays(1);
            }
        }

        return jours;
    }
}
