package com.monoloco.zaraqueue.dagger;

import android.content.Context;

import com.monoloco.zaraqueue.ApplicationClass;
import com.monoloco.zaraqueue.activities.MainActivity;
import com.monoloco.zaraqueue.preferences.PreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by root on 5/10/17.
 */

@Module(
        injects =
                {
                        // Activities
                        MainActivity.class

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
}