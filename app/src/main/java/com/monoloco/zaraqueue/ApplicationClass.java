package com.monoloco.zaraqueue;

import android.app.Application;
import android.content.Context;

import com.monoloco.zaraqueue.dagger.DaggerModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by root on 5/10/17.
 */

public class ApplicationClass extends Application {
    // The Dagger ObjectGraph
    private ObjectGraph objectGraph;

    private static ApplicationClass instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Configure the graph for dagger
        objectGraph = ObjectGraph.create(getModules().toArray());

/*        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Raleway-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );*/
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

    protected List<DaggerModule> getModules() {
        return Arrays.asList(
                new DaggerModule(this)
        );
    }

    public static void injectMember(Object object) {
        instance.objectGraph.inject(object);
    }
}
