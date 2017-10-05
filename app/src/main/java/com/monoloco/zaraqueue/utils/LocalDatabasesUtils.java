package com.monoloco.zaraqueue.utils;

import android.content.Context;

import com.monoloco.zaraqueue.base.BaseUtils;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by root on 5/10/17.
 */

public class LocalDatabasesUtils extends BaseUtils {

    @Inject Realm realm;

    private Context context;

    public LocalDatabasesUtils(Context context) {
        this.context = context;
    }

    public void updateQueue(){

    }
}
