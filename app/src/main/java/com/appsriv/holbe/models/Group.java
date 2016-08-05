package com.appsriv.holbe.models;

import java.util.ArrayList;

public class Group {

    private String Name;
    private String percentage;
    private String groupName;
    int icon;
    private String sectionName;

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    private ArrayList<Workout> Items;
    private ArrayList<Supplement> sup_Items;
    private ArrayList<LifeStyle> life_Items;
    private ArrayList<Food> food_Items;
    private ArrayList<Others> other_Items;
    private ArrayList<Treatment> treatments;

    private int workout_compliance;
    private int supplement_compliance;
    private int lifestyle_compliance;
    private int food_compliance;
    private int others_compliance;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public ArrayList<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(ArrayList<Treatment> treatments) {
        this.treatments = treatments;
    }

    public int getWorkout_compliance() {
        return workout_compliance;
    }

    public void setWorkout_compliance(int workout_compliance) {
        this.workout_compliance = workout_compliance;
    }

    public int getSupplement_compliance() {
        return supplement_compliance;
    }

    public void setSupplement_compliance(int supplement_compliance) {
        this.supplement_compliance = supplement_compliance;
    }

    public int getLifestyle_compliance() {
        return lifestyle_compliance;
    }

    public void setLifestyle_compliance(int lifestyle_compliance) {
        this.lifestyle_compliance = lifestyle_compliance;
    }

    public int getFood_compliance() {
        return food_compliance;
    }

    public void setFood_compliance(int food_compliance) {
        this.food_compliance = food_compliance;
    }

    public int getOthers_compliance() {
        return others_compliance;
    }

    public void setOthers_compliance(int others_compliance) {
        this.others_compliance = others_compliance;
    }

    public ArrayList<Supplement> getSup_Items() {
        return sup_Items;
    }

    public void setSup_Items(ArrayList<Supplement> sup_Items) {
        this.sup_Items = sup_Items;
    }

    public ArrayList<LifeStyle> getLife_Items() {
        return life_Items;
    }

    public void setLife_Items(ArrayList<LifeStyle> life_Items) {
        this.life_Items = life_Items;
    }

    public ArrayList<Food> getFood_Items() {
        return food_Items;
    }

    public void setFood_Items(ArrayList<Food> food_Items) {
        this.food_Items = food_Items;
    }

    public ArrayList<Others> getOther_Items() {
        return other_Items;
    }

    public void setOther_Items(ArrayList<Others> other_Items) {
        this.other_Items = other_Items;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public ArrayList<Workout> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Workout> Items) {
        this.Items = Items;
    }

}
