package com.monoloco.zaraqueue.utils;

import android.content.Context;

import com.monoloco.zaraqueue.base.BaseUtils;
import com.monoloco.zaraqueue.model.Queue;
import com.monoloco.zaraqueue.model.QueueItem;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by root on 5/10/17.
 */

public class LocalDatabasesUtils extends BaseUtils {


    private Context context;

    public LocalDatabasesUtils(Context context) {
        this.context = context;
    }

    public void updateQueue(){

    }

    public void addToQueue(QueueItem queueItem) {

    }

    public void removeFromQueue(QueueItem queueItem) {

    }

    private void chechQueueState(){

    }

/*    public Queue getQueue() {
        Queue queue = realm.where(Queue.class).findFirst();
        return queue;
    }*/
}
