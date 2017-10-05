package com.monoloco.zaraqueue.interfaces;

/**
 * Created by root on 5/10/17.
 */

public interface OnQueueUpdatedListener {
    void onQueueUpdated(long timecount, int size);
    void onQueueStateChanged(int state);
}
