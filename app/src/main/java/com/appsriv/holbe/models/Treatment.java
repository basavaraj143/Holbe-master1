package com.appsriv.holbe.models;

/**
 * Created by appsriv-02 on 4/5/16.
 */

public class Treatment
{
    private int treatment_count;
    private int  treatment_completed;
    private int  treatment_late;
    private int  treatment_missed;


    public int getTreatment_count() {
        return treatment_count;
    }

    public void setTreatment_count(int treatment_count) {
        this.treatment_count = treatment_count;
    }

    public int getTreatment_completed() {
        return treatment_completed;
    }

    public void setTreatment_completed(int treatment_completed) {
        this.treatment_completed = treatment_completed;
    }

    public int getTreatment_late() {
        return treatment_late;
    }

    public void setTreatment_late(int treatment_late) {
        this.treatment_late = treatment_late;
    }

    public int getTreatment_missed() {
        return treatment_missed;
    }

    public void setTreatment_missed(int treatment_missed) {
        this.treatment_missed = treatment_missed;
    }
}
