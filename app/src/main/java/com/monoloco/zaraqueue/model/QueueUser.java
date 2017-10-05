package com.monoloco.zaraqueue.model;

import java.util.ArrayList;

/**
 * Created by root on 5/10/17.
 */

public class QueueUser {

    private String uid;
    private String name;
    private int age;
    private int gender;
    private ArrayList<Clothes> clothes;
    private boolean company;
    private long estimatedTime;
    private int valid;
    private long timestart;

    public QueueUser() {
    }

    public QueueUser(String uid, String name, int age, int gender, ArrayList<Clothes> clothes, boolean company, long estimatedTime, int valid, long timestart) {
        this.uid = uid;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.clothes = clothes;
        this.company = company;
        this.estimatedTime = estimatedTime;
        this.valid = valid;
        this.timestart = timestart;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public ArrayList<Clothes> getClothes() {
        return clothes;
    }

    public void setClothes(ArrayList<Clothes> clothes) {
        this.clothes = clothes;
    }

    public boolean isCompany() {
        return company;
    }

    public void setCompany(boolean company) {
        this.company = company;
    }

    public long getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(long estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public long getTimestart() {
        return timestart;
    }

    public void setTimestart(long timestart) {
        this.timestart = timestart;
    }
}
