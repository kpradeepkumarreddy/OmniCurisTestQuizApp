package com.omnicuris.quizapp;

import android.app.Application;

import com.omnicuris.quizapp.utils.DebugLogTree;
import com.omnicuris.quizapp.utils.ReleaseLogTree;

import timber.log.Timber;

public class QuizApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // init timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugLogTree());
        } else {
            Timber.plant(new ReleaseLogTree());
        }
    }
}

