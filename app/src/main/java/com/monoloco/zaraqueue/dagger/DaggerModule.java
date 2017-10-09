package com.monoloco.zaraqueue.dagger;

import android.content.Context;

import com.monoloco.zaraqueue.ApplicationClass;
import com.monoloco.zaraqueue.activities.ClientActivity;
import com.monoloco.zaraqueue.activities.ManageActivity;
import com.monoloco.zaraqueue.firebase.FirebaseManager;
import com.monoloco.zaraqueue.fragments.NewClientFragment;
import com.monoloco.zaraqueue.fragments.NewConfirmFragment;
import com.monoloco.zaraqueue.model.Queue;
import com.monoloco.zaraqueue.model.QueueUser;
import com.monoloco.zaraqueue.preferences.PreferencesManager;
import com.monoloco.zaraqueue.utils.LocalDatabasesUtils;
import com.monoloco.zaraqueue.utils.Utils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by root on 5/10/17.
 */

@Module(
        injects =
                {
                        // Activities
                        ClientActivity.class,
                        ManageActivity.class,

                        // Utils
                        Utils.class,
                        Utils.class,
                        LocalDatabasesUtils.class,

                        // Firebase
                        FirebaseManager.class,

                        // Fragment
                        NewClientFragment.class,
                        NewConfirmFragment.class,

                },
        library = true,
        complete = false
)
public class DaggerModule {

    private ApplicationClass applicationClass;

    public DaggerModule(ApplicationClass applicationClass) {
        this.applicationClass = applicationClass;
    }

    /**
     * Allow the application context to be injected.
     */
    @Provides
    @Singleton
    Context provideApplicationContext() {
        return applicationClass;
    }

    @Provides
    @Singleton
    PreferencesManager providePreferencesManager(Context context) {
        return new PreferencesManager(context);
    }

    @Provides
    @Singleton
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    LocalDatabasesUtils provideLocalDatabaseUtils(Context context){
        return new LocalDatabasesUtils(context);
    }

    @Provides
    @Singleton
    Queue provideQueue(){
        return new Queue();
    }

    @Provides
    @Singleton
    FirebaseManager provideFirebaseManager(Context context){
        return new FirebaseManager(context);
    }

    @Provides
    @Singleton
    QueueUser provideQueueUser(){
        return new QueueUser();
    }

}