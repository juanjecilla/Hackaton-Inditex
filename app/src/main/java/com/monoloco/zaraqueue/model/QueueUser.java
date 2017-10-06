package com.monoloco.zaraqueue.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by root on 5/10/17.
 */

public class QueueUser implements Parcelable {

    private String uid;
    private String name;
    private int age;
    private int gender;
    private ArrayList<Clothes> clothes;
    private int company;
    private long estimatedTime;
    private int valid;
    private long timestart;
    private int ready;

    public QueueUser() {
    }

    public QueueUser(String uid, String name, int age, int gender, ArrayList<Clothes> clothes, int company, long estimatedTime, int valid, long timestart, int ready) {
        this.uid = uid;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.clothes = clothes;
        this.company = company;
        this.estimatedTime = estimatedTime;
        this.valid = valid;
        this.timestart = timestart;
        this.ready = ready;
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

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
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

    public int getReady() {
        return ready;
    }

    public void setReady(int ready) {
        this.ready = ready;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeInt(this.gender);
        dest.writeTypedList(this.clothes);
        dest.writeInt(this.company);
        dest.writeLong(this.estimatedTime);
        dest.writeInt(this.valid);
        dest.writeLong(this.timestart);
        dest.writeInt(this.ready);
    }

    protected QueueUser(Parcel in) {
        this.uid = in.readString();
        this.name = in.readString();
        this.age = in.readInt();
        this.gender = in.readInt();
        this.clothes = in.createTypedArrayList(Clothes.CREATOR);
        this.company = in.readInt();
        this.estimatedTime = in.readLong();
        this.valid = in.readInt();
        this.timestart = in.readLong();
        this.ready = in.readInt();
    }

    public static final Creator<QueueUser> CREATOR = new Creator<QueueUser>() {
        @Override
        public QueueUser createFromParcel(Parcel source) {
            return new QueueUser(source);
        }

        @Override
        public QueueUser[] newArray(int size) {
            return new QueueUser[size];
        }
    };
}
