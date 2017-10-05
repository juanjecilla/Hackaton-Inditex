package com.monoloco.zaraqueue.dagger;

import android.content.Context;

import com.monoloco.zaraqueue.ApplicationClass;
import com.monoloco.zaraqueue.activities.MainActivity;
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
                        MainActivity.class,

                        // Utils
                        Utils.class,
                        Utils.class,
                        LocalDatabasesUtils.class

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

}