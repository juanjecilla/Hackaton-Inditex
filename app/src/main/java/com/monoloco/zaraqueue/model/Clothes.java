package com.monoloco.zaraqueue.model;

/**
 * Created by root on 5/10/17.
 */

public class Clothes {

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
}
