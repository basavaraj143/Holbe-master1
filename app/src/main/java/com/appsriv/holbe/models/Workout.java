package com.appsriv.holbe.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Workout

{
    /*


    "type":"workout",
         "hasweight":"0",
         "circuit_id":"62",
         "timings_id":"2504",
         "time":null,
         "workout_mapping_id":"90",
         "workout_name":"treadmill",
         "reps":"1",
         "sets":"1",
         "weight":"1",
         "rest":"",
         "gap":"week",
         "frequency":"3",
         "compliance":"211"

*/

   /* public Workout(ArrayList<String> reps1, ArrayList<String> sets1, ArrayList<String> workout_name1, ArrayList<String> type1, ArrayList<String> timings_id1, ArrayList<String> time1, ArrayList<String> hasWeight1, ArrayList<String> compliance1) {
        this.reps1 = reps1;
        this.sets1 = sets1;
        this.workout_name1 = workout_name1;
        this.type1 = type1;
        this.timings_id1 = timings_id1;
        this.time1 = time1;
        this.hasWeight1 = hasWeight1;
        this.compliance1 = compliance1;
    }*/

    private String type;
    private String hasweight;
    private int int_compliance;
    private String workout_mapping_id;
    private  String workout_name;
    private String reps;
    private String sets;
    private String weight;
    private String compliance;
    private String colour;
    private int progressBarRes;


    private ArrayList<String> reps1;
    private ArrayList<String> sets1;
    private ArrayList<String> workout_name1;
    private ArrayList<String> type1;
    private ArrayList<String> timings_id1;
    private ArrayList<String> time1;
    private ArrayList<String> hasWeight1;
    private ArrayList<String> compliance1;
    private boolean already_exist;

    public boolean isAlready_exist() {
        return already_exist;
    }

    public void setAlready_exist(boolean already_exist) {
        this.already_exist = already_exist;
    }

    private HashMap<String,ArrayList<Workout>> listHashMap;

    public HashMap<String, ArrayList<Workout>> getListHashMap() {
        return listHashMap;
    }

    public void setListHashMap(HashMap<String, ArrayList<Workout>> listHashMap) {
        this.listHashMap = listHashMap;
    }

    public Workout()
    {
        reps1 = new ArrayList<>();
        sets1 = new ArrayList<>();
        workout_name1 = new ArrayList<>();
        type1 = new ArrayList<>();
        timings_id1 = new ArrayList<>();
        time1 = new ArrayList<>();
        hasWeight1 = new ArrayList<>();
        compliance1 = new ArrayList<>();
    }

    public ArrayList<String> getReps1() {
        return reps1;
    }

    public void setReps1(ArrayList<String> reps1) {
        this.reps1 = reps1;
    }
    public void addReps1(String reps)
    {
        this.reps1.add(reps);
    }

    public ArrayList<String> getSets1() {
        return sets1;
    }

    public void setSets1(ArrayList<String> sets1) {
        this.sets1 = sets1;
    }

    public ArrayList<String> getWorkout_name1() {
        return workout_name1;
    }

    public void setWorkout_name1(ArrayList<String> workout_name1) {
        this.workout_name1 = workout_name1;
    }

    public ArrayList<String> getType1() {
        return type1;
    }

    public void setType1(ArrayList<String> type1) {
        this.type1 = type1;
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

    public ArrayList<String> getHasWeight1() {
        return hasWeight1;
    }

    public void setHasWeight1(ArrayList<String> hasWeight1) {
        this.hasWeight1 = hasWeight1;
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

    public String getHasweight() {
        return hasweight;
    }

    public void setHasweight(String hasweight) {
        this.hasweight = hasweight;
    }

    //profile screen
    private int workout_count;
    private int workout_completed;
    private int workout_late;
    private int workout_missed;
    private String timings_id;
    private String time;
    private String gap;
    private String freequency;
    private String rest;
    private String circuit_id;

    public String getCircuit_id() {
        return circuit_id;
    }

    public void setCircuit_id(String circuit_id) {
        this.circuit_id = circuit_id;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
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



    public int getWorkout_count() {
        return workout_count;
    }

    public void setWorkout_count(int workout_count) {
        this.workout_count = workout_count;
    }

    public int getWorkout_completed() {
        return workout_completed;
    }

    public void setWorkout_completed(int workout_completed) {
        this.workout_completed = workout_completed;
    }

    public int getWorkout_late() {
        return workout_late;
    }

    public void setWorkout_late(int workout_late) {
        this.workout_late = workout_late;
    }

    public int getWorkout_missed() {
        return workout_missed;
    }

    public void setWorkout_missed(int workout_missed) {
        this.workout_missed = workout_missed;
    }

    public int getProgressBarRes() {
        return progressBarRes;
    }

    public void setProgressBarRes(int progressBarRes) {
        this.progressBarRes = progressBarRes;
    }

    public int getInt_compliance() {
        return int_compliance;
    }

    public void setInt_compliance(int int_compliance) {
        this.int_compliance = int_compliance;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getWorkout_mapping_id() {
        return workout_mapping_id;
    }

    public void setWorkout_mapping_id(String workout_mapping_id) {
        this.workout_mapping_id = workout_mapping_id;
    }

    public String getWorkout_name() {
        return workout_name;
    }

    public void setWorkout_name(String workout_name) {
        this.workout_name = workout_name;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCompliance() {
        return compliance;
    }

    public void setCompliance(String compliance) {
        this.compliance = compliance;
    }
}