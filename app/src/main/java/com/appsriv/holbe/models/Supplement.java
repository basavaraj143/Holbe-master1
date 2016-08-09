package com.appsriv.holbe.models;

import java.util.ArrayList;

/**
 * Created by appsriv-02 on 29/4/16.
 */
public class Supplement
{

    private String type;
    private String dosage_main_name;
    private String form_main_name;
    private String criteria_main_name;
    private String timings_id;
    private String time;
    private String gap;
    private String freequency;
    private String supplement_mapping_id;
    private String  supplement_name;
    private String  amount;
    private String  repitition;
    private String  when_time;
    private String  compliance;


    private ArrayList<String> type1;
    private ArrayList<String> dosage_main_name1;
    private ArrayList<String> form_main_name1;
    private ArrayList<String> criteria_main_name1;
    private ArrayList<String> timings_id1;
    private ArrayList<String> time1;
    private ArrayList<String> gap1;
    private ArrayList<String> freequency1;
    private ArrayList<String> supplement_mapping_id1;
    private ArrayList<String>  supplement_name1;
    private ArrayList<String>  amoun1t;
    private ArrayList<String>  repitition1;
    private ArrayList<String>  when_time1;
    private ArrayList<String>  compliance1;


    int ProgressBarRes;
    String Colour;
    private int supplement_count;
    private int supplement_completed;
    private int supplement_late;
    private int supplement_missed;

   /* public Supplement()
    {

        type1 = new ArrayList<>();
        dosage_main_name1 = new ArrayList<>();
        form_main_name1 = new ArrayList<>();
        criteria_main_name1 = new ArrayList<>();
        timings_id1 = new ArrayList<>();
        gap1 = new ArrayList<>();
        freequency1 = new ArrayList<>();
        supplement_mapping_id1 = new ArrayList<>();
        supplement_name1 = new ArrayList<>();
        amoun1t = new ArrayList<>();
        repitition1 = new ArrayList<>();
        when_time1 = new ArrayList<>();
        compliance1 = new ArrayList<>();
        compliance1 = new ArrayList<>();

    }*/

    public ArrayList<String> getType1() {
        return type1;
    }

    public void setType1(ArrayList<String> type1) {
        this.type1 = type1;
    }

    public ArrayList<String> getDosage_main_name1() {
        return dosage_main_name1;
    }

    public void setDosage_main_name1(ArrayList<String> dosage_main_name1) {
        this.dosage_main_name1 = dosage_main_name1;
    }

    public ArrayList<String> getForm_main_name1() {
        return form_main_name1;
    }

    public void setForm_main_name1(ArrayList<String> form_main_name1) {
        this.form_main_name1 = form_main_name1;
    }

    public ArrayList<String> getCriteria_main_name1() {
        return criteria_main_name1;
    }

    public void setCriteria_main_name1(ArrayList<String> criteria_main_name1) {
        this.criteria_main_name1 = criteria_main_name1;
    }

    public ArrayList<String> getTimings_id1() {
        return timings_id1;
    }

    public void setTimings_id1(ArrayList<String> timings_id1) {
        this.timings_id1 = timings_id1;
    }

    public ArrayList<String> getTime1() {
        return time1;
    }

    public void setTime1(ArrayList<String> time1) {
        this.time1 = time1;
    }

    public ArrayList<String> getGap1() {
        return gap1;
    }

    public void setGap1(ArrayList<String> gap1) {
        this.gap1 = gap1;
    }

    public ArrayList<String> getFreequency1() {
        return freequency1;
    }

    public void setFreequency1(ArrayList<String> freequency1) {
        this.freequency1 = freequency1;
    }

    public ArrayList<String> getSupplement_mapping_id1() {
        return supplement_mapping_id1;
    }

    public void setSupplement_mapping_id1(ArrayList<String> supplement_mapping_id1) {
        this.supplement_mapping_id1 = supplement_mapping_id1;
    }

    public ArrayList<String> getSupplement_name1() {
        return supplement_name1;
    }

    public void setSupplement_name1(ArrayList<String> supplement_name1) {
        this.supplement_name1 = supplement_name1;
    }

    public ArrayList<String> getAmoun1t() {
        return amoun1t;
    }

    public void setAmoun1t(ArrayList<String> amoun1t) {
        this.amoun1t = amoun1t;
    }

    public ArrayList<String> getRepitition1() {
        return repitition1;
    }

    public void setRepitition1(ArrayList<String> repitition1) {
        this.repitition1 = repitition1;
    }

    public ArrayList<String> getWhen_time1() {
        return when_time1;
    }

    public void setWhen_time1(ArrayList<String> when_time1) {
        this.when_time1 = when_time1;
    }

    public ArrayList<String> getCompliance1() {
        return compliance1;
    }

    public void setCompliance1(ArrayList<String> compliance1) {
        this.compliance1 = compliance1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDosage_main_name() {
        return dosage_main_name;
    }

    public void setDosage_main_name(String dosage_main_name) {
        this.dosage_main_name = dosage_main_name;
    }

    public String getForm_main_name() {
        return form_main_name;
    }

    public void setForm_main_name(String form_main_name) {
        this.form_main_name = form_main_name;
    }

    public String getCriteria_main_name() {
        return criteria_main_name;
    }

    public void setCriteria_main_name(String criteria_main_name) {
        this.criteria_main_name = criteria_main_name;
    }

    public String getFreequency() {
        return freequency;
    }

    public void setFreequency(String freequency) {
        this.freequency = freequency;
    }

    public String getGap() {
        return gap;
    }

    public void setGap(String gap) {
        this.gap = gap;
    }

    public String getTimings_id() {
        return timings_id;
    }

    public void setTimings_id(String timings_id) {
        this.timings_id = timings_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public int getSupplement_count() {
        return supplement_count;
    }

    public void setSupplement_count(int supplement_count) {
        this.supplement_count = supplement_count;
    }

    public int getSupplement_completed() {
        return supplement_completed;
    }

    public void setSupplement_completed(int supplement_completed) {
        this.supplement_completed = supplement_completed;
    }

    public int getSupplement_late() {
        return supplement_late;
    }

    public void setSupplement_late(int supplement_late) {
        this.supplement_late = supplement_late;
    }

    public int getSupplement_missed() {
        return supplement_missed;
    }

    public void setSupplement_missed(int supplement_missed) {
        this.supplement_missed = supplement_missed;
    }

    private int int_compliance;

    public int getInt_compliance() {
        return int_compliance;
    }

    public void setInt_compliance(int int_compliance) {
        this.int_compliance = int_compliance;
    }

    public String getColour() {
        return Colour;
    }

    public void setColour(String colour) {
        Colour = colour;
    }
    public int getProgressBarRes() {
        return ProgressBarRes;
    }

    public void setProgressBarRes(int progressBarRes) {
        ProgressBarRes = progressBarRes;
    }

    public String getSupplement_mapping_id() {
        return supplement_mapping_id;
    }

    public void setSupplement_mapping_id(String supplement_mapping_id) {
        this.supplement_mapping_id = supplement_mapping_id;
    }

    public String getSupplement_name() {
        return supplement_name;
    }

    public void setSupplement_name(String supplement_name) {
        this.supplement_name = supplement_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRepitition() {
        return repitition;
    }

    public void setRepitition(String repitition) {
        this.repitition = repitition;
    }

    public String getWhen_time() {
        return when_time;
    }

    public void setWhen_time(String when_time) {
        this.when_time = when_time;
    }

    public String getCompliance() {
        return compliance;
    }

    public void setCompliance(String compliance) {
        this.compliance = compliance;
    }
}
