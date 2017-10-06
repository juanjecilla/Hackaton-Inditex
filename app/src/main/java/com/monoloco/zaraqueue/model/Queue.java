package com.monoloco.zaraqueue.model;

import java.util.ArrayList;

/**
 * Created by root on 5/10/17.
 */

public class Queue {
    private String uid;
    private ArrayList<QueueUser> queueList;

    public Queue() {
    }

    public Queue(String uid, ArrayList<QueueUser> queueList) {
        this.uid = uid;
        this.queueList = queueList;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<QueueUser> getQueueList() {
        return queueList;
    }

    public void setQueueList(ArrayList<QueueUser> queueList) {
        this.queueList = queueList;
    }
}
