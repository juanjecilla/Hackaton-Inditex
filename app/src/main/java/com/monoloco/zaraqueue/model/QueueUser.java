package com.monoloco.zaraqueue.model;

import java.util.ArrayList;

/**
 * Created by root on 5/10/17.
 */

public class QueueUser {

    private String uid;
    private String name;
    private long birthdate;
    private int gender;
    private ArrayList<Clothes> clothes;
    private boolean company;
    private long estimatedTime;

    public QueueUser() {
    }

    public QueueUser(String uid, String name, long birthdate, int gender, ArrayList<Clothes> clothes, boolean company, long estimatedTime) {
        this.uid = uid;
        this.name = name;
        this.birthdate = birthdate;
        this.gender = gender;
        this.clothes = clothes;
        this.company = company;
        this.estimatedTime = estimatedTime;
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

    public long getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(long birthdate) {
        this.birthdate = birthdate;
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
}
