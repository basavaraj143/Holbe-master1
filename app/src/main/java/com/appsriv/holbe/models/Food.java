package com.appsriv.holbe.models;

/**
 * Created by appsriv-02 on 29/4/16.
 */
public class Food
{
    /*"type":"food",
         "timings_id":"1813",
         "time":"12:00AM",
         "food_mapping_id":"11",
         "food_name":"Ladyfinger",
         "when":"Lunch",
         "compliance":"219"*/
    private String type;
    private String timings_id;
    private String time;
    private String food_mapping_id;
    private String food_name;
    private String when;
    private String compliance;
    int ProgressBarRes;
    String Colour;
    private int int_compliance;
    private int food_count;
    private int food_completed;
    private int food_late;
    private int food_missed;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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


    public int getFood_count() {
        return food_count;
    }

    public void setFood_count(int food_count) {
        this.food_count = food_count;
    }

    public int getFood_completed() {
        return food_completed;
    }

    public void setFood_completed(int food_completed) {
        this.food_completed = food_completed;
    }

    public int getFood_late() {
        return food_late;
    }

    public void setFood_late(int food_late) {
        this.food_late = food_late;
    }

    public int getFood_missed() {
        return food_missed;
    }

    public void setFood_missed(int food_missed) {
        this.food_missed = food_missed;
    }

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

    public String getFood_mapping_id() {
        return food_mapping_id;
    }

    public void setFood_mapping_id(String food_mapping_id) {
        this.food_mapping_id = food_mapping_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getCompliance() {
        return compliance;
    }

    public void setCompliance(String compliance) {
        this.compliance = compliance;
    }
}
