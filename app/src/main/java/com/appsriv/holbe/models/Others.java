package com.appsriv.holbe.models;

/**
 * Created by appsriv-02 on 29/4/16.
 */
public class Others
{
    private String timings_id;
    private String time;

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

    private String others_mapping_id;
    private String others_name;
    private String duration;
    private String compliance;
    int ProgressBarRes;
    String Colour;
    private int int_compliance;

    //profile screen
    private int others_count;
    private int others_completed;
    private int others_late;
    private int others_missed;

    public int getOthers_count() {
        return others_count;
    }

    public void setOthers_count(int others_count) {
        this.others_count = others_count;
    }

    public int getOthers_completed() {
        return others_completed;
    }

    public void setOthers_completed(int others_completed) {
        this.others_completed = others_completed;
    }

    public int getOthers_late() {
        return others_late;
    }

    public void setOthers_late(int others_late) {
        this.others_late = others_late;
    }

    public int getOthers_missed() {
        return others_missed;
    }

    public void setOthers_missed(int others_missed) {
        this.others_missed = others_missed;
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
    public String getOthers_mapping_id() {
        return others_mapping_id;
    }

    public void setOthers_mapping_id(String others_mapping_id) {
        this.others_mapping_id = others_mapping_id;
    }

    public String getOthers_name() {
        return others_name;
    }

    public void setOthers_name(String others_name) {
        this.others_name = others_name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCompliance() {
        return compliance;
    }

    public void setCompliance(String compliance) {
        this.compliance = compliance;
    }
}
