package com.monoloco.zaraqueue.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 5/10/17.
 */

public class Clothes implements Parcelable {

    private String id;
    private int type;

    public Clothes() {
    }

    public Clothes(String id, int type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.type);
    }

    protected Clothes(Parcel in) {
        this.id = in.readString();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<Clothes> CREATOR = new Parcelable.Creator<Clothes>() {
        @Override
        public Clothes createFromParcel(Parcel source) {
            return new Clothes(source);
        }

        @Override
        public Clothes[] newArray(int size) {
            return new Clothes[size];
        }
    };
}
