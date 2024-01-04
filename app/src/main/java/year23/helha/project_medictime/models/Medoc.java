package year23.helha.project_medictime.models;

import androidx.annotation.NonNull;

public class Medoc {

    protected Integer mId;
    public String mName;
    int mDuree;
    boolean mMatin;
    boolean mMidi;
    boolean mSoir;
    public Medoc() {
        this.mId = 0;
        this.mName = "";
        this.mDuree = 0;
        this.mMatin = false;
        this.mMidi = false;
        this.mSoir = false;
    }
    public Medoc(Integer id) {
        mId = id;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmName() {
        return mName;
    }

    public long getmDuree() {
        return mDuree;
    }

    public void setmDuree(String mDuree) {
        this.mDuree = Integer.parseInt(mDuree);
    }

    public boolean isMatin() {
        return mMatin;
    }

    public void setMatin(boolean matin) {
        this.mMatin = matin;
    }

    public boolean isMidi() {
        return mMidi;
    }

    public void setMidi(boolean midi) {
        this.mMidi = midi;
    }

    public boolean isSoir() {
        return mSoir;
    }
    public void setSoir(boolean soir) {
        this.mSoir = soir;
    }

    @NonNull
    @Override
    public String toString() {
        return mName;
    }
}