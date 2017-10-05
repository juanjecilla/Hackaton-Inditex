package com.monoloco.zaraqueue.model;

import java.util.ArrayList;

/**
 * Created by root on 5/10/17.
 */

public class Queue {
    private String uid;
    private ArrayList<String> list;

    public Queue(String uid, ArrayList<String> list) {
        this.uid = uid;
        this.list = list;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
}
